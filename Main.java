import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main{
	public static World world;
	public static int UIState;
	public static ArrayList<UICommand> UICommands;
	public static ArrayList<BuildCommand> buildCommands;
	public static ArrayList<OverlaySwitchCommand> overlaySwitchCommands;
	public static BuildCommand buildCommandSelected;
	
	public static final int UI_BUILD = 0;
	public static final int UI_DEMOLISH = 1;
	public static final int UI_OVERLAYS = 2;
	public static final int UI_STATISTICS = 3;
	public static final int UI_PREVIOUS_PAGE = 4;
	public static final int UI_NEXT_PAGE = 5;
	
	public static void main(String[] args)throws Exception{
		Config.setupConfig();
	
		JFrame window = new JFrame("CS124 Final Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		StateHandler stateHandler = StateHandler.getInstance();
		ObjectHandler objectHandler = ObjectHandler.getInstance();
		OverlayHandler overlayHandler = OverlayHandler.getInstance();
		stateHandler.setup();
		objectHandler.setup();
		overlayHandler.setup();
		
		world = new World(Config.getWorldWidth(), Config.getWorldHeight());
		
		Paint canvas = new Paint(world);
		canvas.setPreferredSize(new Dimension(Config.getWindowWidth(), Config.getWindowHeight()));
		window.add(canvas);
		
		UIState = UI_BUILD;
		UICommands = new ArrayList<UICommand>();
		for(int i=0; i<6; i++){
			UICommands.add(new UICommand(i, canvas));
		}
		buildCommands = new ArrayList<BuildCommand>();
		objectHandler.generateBuildCommands(buildCommands);
		overlaySwitchCommands = new ArrayList<OverlaySwitchCommand>();
		overlayHandler.generateOverlaySwitchCommands(overlaySwitchCommands);
		
		MouseHandler mouseHandler = MouseHandler.getInstance();
		mouseHandler.setParent(window);
		canvas.addMouseListener(mouseHandler);
		
		KeyboardHandler keyboardHandler = KeyboardHandler.getInstance();
		keyboardHandler.setParent(window);
		canvas.addKeyListener(keyboardHandler);
		
		window.pack();
		window.setResizable(false);
		window.show();
		
		long timeSoFar = 0;
		long oldTime = System.nanoTime();
		while(true){
			long curTime = System.nanoTime();
			long deltaTime = curTime-oldTime;
			timeSoFar += deltaTime;
			oldTime = curTime;
			
			if(mouseHandler.isMouseDown()){
				int x = mouseHandler.getPressedX();
				int y = mouseHandler.getPressedY();
				if(y > Config.getWindowHeight()-30){
					if(y < Config.getWindowHeight()-25 || y > Config.getWindowHeight()+3) continue;
					if(195 <= x && x <= 253){
						UICommands.get(UI_BUILD).execute(null);
					}else if(265 <= x && x <= 368){
						UICommands.get(UI_DEMOLISH).execute(null);
					}else if(380 <= x && x <= 480){
						UICommands.get(UI_OVERLAYS).execute(null);
					}else if(495 <= x && x <= 595){
						UICommands.get(UI_STATISTICS).execute(null);
					}
				}else if(x > Config.getWindowWidth()-190){
					int sidebarPage = canvas.getSidebarPage();
					int index = sidebarPage*Config.getCommandsPerPage();
					if(UIState == UI_BUILD){
						do{
							if(Config.getWindowWidth()-185 <= x && x <= Config.getWindowWidth()-5 &&
								5+(index%Config.getCommandsPerPage())*30 <= y && y <= 25+(index%Config.getCommandsPerPage())*30){
								buildCommandSelected = buildCommands.get(index);
							}
						
							index++;
						}while(index%Config.getCommandsPerPage() != 0 && index < Main.buildCommands.size());
					}else if(UIState == UI_OVERLAYS){
						do{
							if(Config.getWindowWidth()-185 <= x && x <= Config.getWindowWidth()-5 &&
								5+(index%Config.getCommandsPerPage())*30 <= y && y <= 25+(index%Config.getCommandsPerPage())*30){
								overlaySwitchCommands.get(index).execute(null);
							}
						
							index++;
						}while(index%Config.getCommandsPerPage() != 0 && index < Main.overlaySwitchCommands.size());
					}
					
					if(Config.getWindowHeight()-52 <= y && y <= Config.getWindowHeight()-32){
						if(Config.getWindowWidth()-185 <= x && x <= Config.getWindowWidth()-125){
							UICommands.get(UI_PREVIOUS_PAGE).execute(null);
						}else if(Config.getWindowWidth()-40 <= x && x <= Config.getWindowWidth()-5){
							UICommands.get(UI_NEXT_PAGE).execute(null);
						}
					}
				}else{
					x = (x-canvas.getOffsetX())/world.getCellSize();
					y = (y-canvas.getOffsetY())/world.getCellSize();
				}
			}
			if(keyboardHandler.isAnyDown()){
				if(keyboardHandler.isLeftDown()){
					canvas.addOffsetX(5*deltaTime/10000000.);
				}
				if(keyboardHandler.isRightDown()){
					canvas.addOffsetX(-5*deltaTime/10000000.);
				}
				if(keyboardHandler.isUpDown()){
					canvas.addOffsetY(5*deltaTime/10000000.);
				}
				if(keyboardHandler.isDownDown()){
					canvas.addOffsetY(-5*deltaTime/10000000.);
				}
			}
			
			world.process(deltaTime);
			
			canvas.repaint();
		}
	}
}