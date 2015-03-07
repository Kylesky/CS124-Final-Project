import java.awt.*;

abstract class Entity{
	private int r, c;
	private boolean active;
	public Entity(int r, int c){
		this.r = r;
		this.c = c;
		active = true;
	}
	
	public void setR(int r){this.r = r;}
	public void setC(int c){this.c = c;}
	public void setActive(boolean b){active = b;}
	public int getR(){return r;}
	public int getC(){return c;}
	public boolean isActive(){return active;}
	
	public abstract void draw(Graphics2D g, int offsetX, int offsetY);
	public abstract void process(long deltaTime);
}