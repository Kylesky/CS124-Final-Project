import java.awt.*; 
import java.util.*;
//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name,code; 
	Color color; 
	int width, height, wealth, cost, price, power, water, capSize; 
	public BuildingBehavior(String name, String code, int power, int water, int cost, int price, Color color, int width, int height, int wealth, int capSize)
	{
		this.name = name;
		this.code = code;
		this.color = color;
		this.width = width;
		this.height = height;
		this.wealth = wealth;
		this.cost = cost; 
		this.price =price;
		this.power = power;
		this.water = water; 
		this.capSize = capSize;
	}
	public String getName(){return name;}
	
	public void restock(Building build){};
	public abstract void process(long deltaTime, Building build);
	public void setup(Building build){}; 
	public int getCost(){return cost;}
	public Color getColor(){return color;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getCapSize(){return capSize;}
	public abstract String getNeedServiced(); 
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
		g.setColor(Color.WHITE);
		g.setFont(Paint.mapFont);
		g.drawString(code, x+5, y+Config.getWorldCellSize()/2);
	}
}