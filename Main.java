import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main{
	public static World world;
	public static int UIState; //0 = build, 1 = demolish, 2 = overlays, 3 = statistics
	public static ArrayList<UISwitchCommand> UISwitchCommands;
	public static ArrayList<BuildCommand> buildCommands;
	public static ArrayList<OverlaySwitchCommand> overlaySwitchCommands;
	public static BuildCommand buildCommandSelected;
	
	public static final int UI_BUILD = 0;
	public static final int UI_DEMOLISH = 1;
	public static final int UI_OVERLAYS = 2;
	public static final int UI_STATISTICS = 3;
	
	public static void main(String[] args)throws Exception{
		Config.setupConfig();
	
		JFrame window = new JFrame("CS124 Final Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		StateHandler stateHandler = StateHandler.getInstance();
		ObjectHandler objectHandler = ObjectHandler.getInstance();
		stateHandler.setup();
		objectHandler.setup();
		
		world = new World(Config.getWorldWidth(), Config.getWorldHeight());
		
		Paint canvas = new Paint(world);
		canvas.setPreferredSize(new Dimension(Config.getWindowWidth(), Config.getWindowHeight()));
		window.add(canvas);
		
		UIState = UI_BUILD;
		UISwitchCommands = new ArrayList<UISwitchCommand>();
		for(int i=0; i<4; i++){
			UISwitchCommands.add(new UISwitchCommand(i, canvas));
		}
		buildCommands = new ArrayList<BuildCommand>();
		objectHandler.generateBuildCommands(buildCommands);
		overlaySwitchCommands = new ArrayList<OverlaySwitchCommand>();
		
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
						UISwitchCommands.get(UI_BUILD).execute(null);
					}else if(265 <= x && x <= 368){
						UISwitchCommands.get(UI_DEMOLISH).execute(null);
					}else if(380 <= x && x <= 480){
						UISwitchCommands.get(UI_OVERLAYS).execute(null);
					}else if(495 <= x && x <= 595){
						UISwitchCommands.get(UI_STATISTICS).execute(null);
					}
				}else if(x > Config.getWindowWidth()-190){
				}else{
					x = (x-canvas.getOffsetX())/world.getCellSize();
					y = (y-canvas.getOffsetY())/world.getCellSize();
				}
				//insert command
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