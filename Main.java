import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main{
	public static int WINDOW_WIDTH, WINDOW_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT;
	public static int WORLD_CELL_SIZE = 40;
	public static World world;
	
	public static void main(String[] args){
		launcher();
	
		JFrame window = new JFrame("CS124 Final Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		world = new World(WORLD_WIDTH, WORLD_HEIGHT);
		
		//StateHandler.setup();
		
		Paint canvas = new Paint();
		canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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
		
		/*
		entityList = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		*/
		
		long timeSoFar = 0;
		long oldTime = System.nanoTime();
		while(true){
			long curTime = System.nanoTime();
			long deltaTime = curTime-oldTime;
			timeSoFar += deltaTime;
			oldTime = curTime;
			
			System.out.println(timeSoFar/1000000000L);
			
			if(mouseHandler.isMouseDown()){
				int x = mouseHandler.getPressedX()-canvas.getOffsetX()-WINDOW_WIDTH;
				int y = mouseHandler.getPressedY()-canvas.getOffsetY()-WINDOW_HEIGHT;
				//insert command
			}
			
			/*
			for(Entity e: entityList){
				e.process(deltaTime);
			}
			for(Entity e: toRemove){
				entityList.remove(e);
			}
			toRemove.clear();
			for(Entity e: toAdd){
				entityList.add(e);
			}
			toAdd.clear();
			*/
			
			//canvas.repaint();
		}
	}
	
	public static void launcher(){
		JPanel lPanel = new JPanel();
		lPanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("<html>BLAH BLAH BLAH</html>");
		lPanel.add(label, BorderLayout.NORTH);
		
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(4, 2));
		
		JLabel width = new JLabel("Window Width:");
		JTextField t_width = new JTextField("800");
		JLabel length = new JLabel("Window Length:");
		JTextField t_length = new JTextField("600");
		JLabel wwidth = new JLabel("World Width (100-500):");
		JTextField t_wwidth = new JTextField("200");
		JLabel wlength = new JLabel("World Length (100-500):");
		JTextField t_wlength = new JTextField("200");
		bPanel.add(width);
		bPanel.add(t_width);
		bPanel.add(length);
		bPanel.add(t_length);
		bPanel.add(wwidth);
		bPanel.add(t_wwidth);
		bPanel.add(wlength);
		bPanel.add(t_wlength);
		lPanel.add(bPanel, BorderLayout.CENTER);
		
		Object[] buttons = {"Launch Game"};
		while(true){
			JOptionPane.showOptionDialog(null, lPanel, "CS124 Lab 4", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
			
			boolean ok = true;
			try{
				int temp = Integer.parseInt(t_width.getText());
				temp = Integer.parseInt(t_length.getText());
				temp = Integer.parseInt(t_wwidth.getText());
				if(temp < 100 || temp > 500) ok = false;
				temp = Integer.parseInt(t_wlength.getText());
				if(temp < 100 || temp > 500) ok = false;
			}catch(NumberFormatException e){
				ok = false;
			}
			if(ok){
				break;
			}else{
				JOptionPane.showMessageDialog(lPanel, "Invalid Inputs!");
			}
		}
		
		WINDOW_WIDTH = Integer.parseInt(t_width.getText());
		WINDOW_HEIGHT = Integer.parseInt(t_length.getText());
		WORLD_WIDTH = Integer.parseInt(t_wwidth.getText());
		WORLD_HEIGHT = Integer.parseInt(t_wlength.getText());
	}
	
	/*
	public static void remove(Entity e){
		toRemove.add(e);
	}
	*/
}