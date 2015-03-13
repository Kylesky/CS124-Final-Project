import java.awt.*; 
import java.util.*;
//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name; 
	Color color; 
	int width, height, wealth; 
	public BuildingBehavior(String name, Color color, int width, int height, int wealth)
	{
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
		this.wealth = wealth;
	}
	public void restock(Building build){};
	public abstract void execute(long deltaTime, Building build);
	public void setup(Building build){}; 
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY; 
		g.fillRect(x,y,width*cellsize,height*cellsize); 
	}
}