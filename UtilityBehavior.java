import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	int capacity, util; 
	public UtilityBehavior(String name, String code, int cost, Color color, int w, int h, int wealth, int capacity, int util)
	{
		super(name, code, cost, color, w, h, wealth);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
}