import java.util.*;
import java.awt.*;
public class Utility extends BuildingBehavior
{
	int radius, util; 
	public Utility(String name, Color color, int w, int h, int wealth, World world, int radius, int util)
	{
		super(name, color, w, h, wealth, world);
		this.radius = radius;
		this.util = util; 
	}
	
	public void execute()
	{
		
	}
}