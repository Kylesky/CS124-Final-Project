import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class KeyboardHandler implements KeyListener{
	private static KeyboardHandler unique;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	public static KeyboardHandler getInstance(){
		if(unique == null){
			unique = new KeyboardHandler();
		}
		return unique;
	}
	
	private static JFrame parent;
	private int keyMap = 0;
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT: keyMap |= (1<<LEFT); break;
			case KeyEvent.VK_RIGHT: keyMap |= (1<<RIGHT); break;
			case KeyEvent.VK_UP: keyMap |= (1<<UP); break;
			case KeyEvent.VK_DOWN: keyMap |= (1<<DOWN); break;
		}
	}
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT: keyMap &= ~(1<<LEFT); break;
			case KeyEvent.VK_RIGHT: keyMap &= ~(1<<RIGHT); break;
			case KeyEvent.VK_UP: keyMap &= ~(1<<UP); break;
			case KeyEvent.VK_DOWN: keyMap &= ~(1<<DOWN); break;
		}
	}
	public void keyTyped(KeyEvent e){}
	
	public void setParent(JFrame parent){
		this.parent = parent;
	}
	public JFrame getParent(){
		return parent;
	}
	
	public boolean isLeftDown(){
		return (keyMap & (1<<LEFT)) != 0;
	}
	
	public boolean isRightDown(){
		return (keyMap & (1<<RIGHT)) != 0;
	}
	
	public boolean isUpDown(){
		return (keyMap & (1<<UP)) != 0;
	}
	
	public boolean isDownDown(){
		return (keyMap & (1<<DOWN)) != 0;
	}
	
	public boolean isKeyDown(int key){
		return (keyMap & (1<<key)) != 0;
	}
}