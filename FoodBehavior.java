import java.util.*;
import java.awt.*;
public class FoodBehavior extends BuildingBehavior
{
	ArrayDeque<Agent> agents; 
	int defAmount;
	public FoodBehavior(String name, Color color, int w, int h, int wealth, World world, int defAmount)
	{
		super(name, color, w, h, wealth, world);
		agents = new ArrayDeque<Agent>(); 
		this.defAmount = defAmount; 
	}
	
	public void execute(long deltaTime, Building build)
	{
		
	}
}