import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	int capacity, util; 
	public UtilityBehavior(String name, Color color, int w, int h, int wealth, World world, int capacity, int util)
	{
		super(name, color, w, h, wealth, world);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void setup(Building build)
	{
		
	}
	
	public void execute(long deltaTime, Building build)
	{
		
	}
}