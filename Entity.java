import java.awt.*;

abstract class Entity{
	private int r, c;
	private boolean active;
	private World world;
	public Entity(int r, int c, World world){
		this.r = r;
		this.c = c;
		active = true;
		this.world = world;
	}
	
	public void setR(int r){this.r = r;}
	public void setC(int c){this.c = c;}
	public void setActive(boolean b){active = b;}
	public int getR(){return r;}
	public int getC(){return c;}
	public boolean isActive(){return active;}
	public World getWorld(){return world;}
	
	public abstract void draw(Graphics2D g, int offsetX, int offsetY);
	public abstract void process(long deltaTime);
}