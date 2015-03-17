import java.util.*;
import java.awt.*;
public class UtilityBehavior extends BuildingBehavior
{
	int capacity; 
	String util;
	public UtilityBehavior(String name, String code, int power, int water, int cost, Color color, int w, int h, int wealth, int capacity, String util)
	{
		super(name, code, power, water, cost, color, w, h, wealth);
		this.capacity = capacity;
		this.util = util; 
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
}