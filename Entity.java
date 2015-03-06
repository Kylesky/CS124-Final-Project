import java.awt.*;

abstract class Entity{
	private int r, c;
	private boolean active;
	public Entity(int r, int c){
		this.r = r;
		this.c = c;
		active = true;
	}
	
	public void setR(double r){this.r = r;}
	public void setC(double c){this.c = c;}
	public void setActive(boolean b){active = b;}
	public double getR(){return r;}
	public double getC(){return c;}
	public boolean isActive(){return active;}
	
	public abstract void draw(Graphics2D g, int offsetX, int offsetY);
	public abstract void process(long deltaTime);
}