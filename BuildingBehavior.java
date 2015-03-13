import java.awt.*; 
import java.util.*;
//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name; 
	Color color; 
	World world;
	int w,h,wealth; 
	public BuildingBehavior(String name, Color color, int w, int h, int wealth, World world)
	{
		this.name = name;
		this.color = color;
		this.w = w;
		this.h = h;
		this.wealth = wealth;
		this.world = world; 
	}
	public void restock(Building build){};
	public abstract void execute(long deltaTime, Building build);
	public void setup(Building build){}; 
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY; 
		g.fillRect(x,y,w,h); 
	}
}