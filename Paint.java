import java.util.*;
import java.awt.*;
import java.awt.image.*;

class Paint extends Canvas{
	private BufferedImage buf;
	private Graphics2D bufg;
	private boolean firstDraw;
	private double offsetX, offsetY;
	
	public Paint(){
		firstDraw = true;
		offsetX = -Main.WINDOW_WIDTH;
		offsetY = -Main.WINDOW_HEIGHT;
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		if(firstDraw){
			setBackground(Color.white);
			buf = (BufferedImage)createImage(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
			bufg = (Graphics2D)buf.getGraphics();
			bufg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			firstDraw = false;
		}
		
		bufg.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		g.drawImage(buf, 0, 0, this);
	}
	
	public int getOffsetX(){
		return (int)offsetX;
	}
	
	public int getOffsetY(){
		return (int)offsetY;
	}
	
	public void addOffsetX(double x){
		offsetX += x;
		if(offsetX > 0) offsetX = 0;
		if(offsetX < -Main.WINDOW_WIDTH) offsetX = -Main.WINDOW_WIDTH;
	}
	
	public void addOffsetY(double y){
		offsetY += y;
		if(offsetY > 0) offsetY = 0;
		if(offsetY < -Main.WINDOW_HEIGHT) offsetY = -Main.WINDOW_HEIGHT;
	}
	
	public void setOffsetX(double x){
		offsetX = x;
		if(offsetX > 0) offsetX = 0;
		if(offsetX < -Main.WINDOW_WIDTH) offsetX = -Main.WINDOW_WIDTH;
	}
	
	public void setOffsetY(double y){
		offsetY = y;
		if(offsetY > 0) offsetY = 0;
		if(offsetY < -Main.WINDOW_HEIGHT) offsetY = -Main.WINDOW_HEIGHT;
	}
}