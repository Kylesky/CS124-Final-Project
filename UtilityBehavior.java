import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	private int capacity; 
	private String util;
	public UtilityBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int capacity, String util)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
	
	public void onBuild(Building build){
		super.onBuild(build);
		World world = build.getWorld(); 
		world.addTotalUtility(capacity,util);
	}
	
	public void onDemolish(Building build)
	{
		super.onDemolish(build);
		World world = build.getWorld();
		world.addTotalUtility(-capacity,util);
	}
	
	public String getNeedServiced()
	{
		return util; 
	}
	
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY, Building build)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
		g.setColor(Color.WHITE);
		g.setFont(Paint.mapFont);
		g.drawString((build.toDemolish()?"!":"")+code, x+5, y+Config.getWorldCellSize()/2);
	}
}