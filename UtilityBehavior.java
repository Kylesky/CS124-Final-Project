import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	private int capacity; 
	private String util;
	public UtilityBehavior(String name, String code, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int capacity, String util)
	{
		super(name, code, power, water, cost, price, color, w, h, wealth, capSize);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
	
	public void setup(Building build)
	{
		super.setup(build); 
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
}