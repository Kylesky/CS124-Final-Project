import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	int capacity, util; 
	public UtilityBehavior(String name, Color color, int w, int h, int wealth, int capacity, int util)
	{
		super(name, color, w, h, wealth);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void execute(long deltaTime, Building build)
	{
		
	}
}