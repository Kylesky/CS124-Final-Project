import java.util.*;
import java.awt.*;
public class Utility extends BuildingBehavior
{
	int radius; 
	public Utility(String name, Color color, int w, int h, int wealth, World world, int radius)
	{
		super(name, color, w, h, wealth, world);
		this.radius = radius;
	}
	
	public void execute()
	{
		
	}
}