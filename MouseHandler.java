import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class MouseHandler implements MouseListener{
	private static MouseHandler unique;
	
	private MouseHandler(){}
	public static MouseHandler getInstance(){
		if(unique == null){
			unique = new MouseHandler();
		}
		return unique;
	}

	private boolean MOUSE_DOWN = false, MOUSE_ACTION_DONE = false;
	private int MOUSE_PRESS_X = -1;
	private int MOUSE_PRESS_Y = -1;
	private static JFrame parent;
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		if(!MOUSE_DOWN){
			MOUSE_PRESS_X = (int)MouseInfo.getPointerInfo().getLocation().getX() - parent.getX() - parent.getInsets().left;
			MOUSE_PRESS_Y = (int)MouseInfo.getPointerInfo().getLocation().getY() - parent.getY() - parent.getInsets().top;
		}
		MOUSE_DOWN = true;
	}
	public void mouseReleased(MouseEvent e){
		MOUSE_DOWN = false;
		MOUSE_ACTION_DONE = false;
	}
	
	public void setParent(JFrame parent){
		this.parent = parent;
	}
	public JFrame getParent(){
		return parent;
	}
	public int getMouseX(){
		return (int)MouseInfo.getPointerInfo().getLocation().getX() - parent.getX() - parent.getInsets().left;
	}
	public int getMouseY(){
		return (int)MouseInfo.getPointerInfo().getLocation().getY() - parent.getY() - parent.getInsets().top;
	}
	public int getPressedX(){
		return MOUSE_PRESS_X;
	}
	public int getPressedY(){
		return MOUSE_PRESS_Y;
	}
	public boolean isMouseDown(){
		return MOUSE_DOWN;
	}
	public boolean isMouseUp(){
		return !MOUSE_DOWN;
	}
	public boolean isMouseActionDone(){
		return MOUSE_ACTION_DONE;
	}
	public void setMouseActionDone(boolean b){
		MOUSE_ACTION_DONE = b;
	}
}