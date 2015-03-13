import java.awt.*;

public class Agent{
	private double x, y;
	private boolean active;
	
	public Agent(){}
	public Agent(double x, double y){
		this.x = x;
		this.y = y;
		active = true;
	}
	
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setActive(boolean b){active = b;}
	public double getX(){return x;}
	public double getY(){return y;}
	public boolean isActive(){return active;}
	
	public void draw(Graphics2D g, int offsetX, int offsetY){
	}
	public void process(long deltaTime){
	}
}