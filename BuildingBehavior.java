import java.awt.*; 
import java.util.*;
//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name; 
	Color color; 
	int width, height, wealth, cost; 
	public BuildingBehavior(String name, int cost, Color color, int width, int height, int wealth)
	{
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
		this.wealth = wealth;
		this.cost = cost; 
	}
	public String getName(){return name;}
	
	public void restock(Building build){};
	public abstract void process(long deltaTime, Building build);
	public void setup(Building build){}; 
	public Color getColor(){return color;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
	}
}