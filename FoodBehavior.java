import java.util.*;
import java.awt.*;
public class FoodBehavior extends BuildingBehavior
{
	ArrayDeque<Agent> agents; 
	ArrayDeque<Long> times; 
	int defAmount, serviceTime;
	//Universal parameter indices
	final int UNITS = 0; 
	final int SALES = 1;
	public FoodBehavior(String name, Color color, int w, int h, int wealth, World world, int defAmount, int serviceTime)
	{
		super(name, color, w, h, wealth, world);
		agents = new ArrayDeque<Agent>(); 
		times = new ArrayDeque<Long>(); 
		this.defAmount = defAmount; 
		this.serviceTime = serviceTime; 
	}
	
	public void setup(Building build)
	{
		build.fields[UNITS] = defAmount; 
	}
	
	public void execute(long deltaTime, Building build)
	{
		
	}
}