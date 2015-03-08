import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main{
	public static World world;
	
	public static void main(String[] args){
		Config.setupConfig();
	
		JFrame window = new JFrame("CS124 Final Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		world = new World(Config.getWorldWidth(), Config.getWorldHeight());
		
		StateHandler stateHandler = StateHandler.getInstance();
		ObjectHandler objectHandler = ObjectHandler.getInstance();
		stateHandler.setup();
		objectHandler.setup();
		
		Paint canvas = new Paint();
		canvas.setPreferredSize(new Dimension(Config.getWindowWidth(), Config.getWindowHeight()));
		window.add(canvas);
		
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
				int x = mouseHandler.getPressedX()-canvas.getOffsetX()-Config.getWindowWidth();
				int y = mouseHandler.getPressedY()-canvas.getOffsetY()-Config.getWindowHeight();
				//insert command
			}
			if(keyboardHandler.isAnyDown()){
				if(keyboardHandler.isLeftDown()){
					canvas.addOffsetX(-5*deltaTime/20000000.);
				}
				if(keyboardHandler.isRightDown()){
					canvas.addOffsetX(5*deltaTime/20000000.);
				}
				if(keyboardHandler.isUpDown()){
					canvas.addOffsetY(-5*deltaTime/20000000.);
				}
				if(keyboardHandler.isDownDown()){
					canvas.addOffsetY(5*deltaTime/20000000.);
				}
			}
			
			world.process(deltaTime);
			
			canvas.repaint();
		}
	}
}