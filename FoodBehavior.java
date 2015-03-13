import java.util.*;
import java.awt.*;
public class FoodBehavior extends BuildingBehavior
{
	int defAmount, serviceTime;
	//Universal parameter indices
	final int UNITS = 0; 
	final int SALES = 1;
	public FoodBehavior(String name, Color color, int w, int h, int wealth, World world, int defAmount, int serviceTime)
	{
		super(name, color, w, h, wealth, world);
		this.defAmount = defAmount; 
		this.serviceTime = serviceTime; 
	}
	
	public void restock(Building build)
	{
		build.fields[UNITS] = build.fields[SALES]*=2; 
	}
	
	public void setup(Building build)
	{
		build.fields[UNITS] = defAmount; 
	}
	
	public void execute(long deltaTime, Building build)
	{
		long curTime = world.getCurrentTime(); 
		while(!build.agents.isEmpty())
		{
			Agent e = build.agents.peek(); 
			long time = build.times.peek(); 
			long diff = curTime - time; 
			if(diff>=serviceTime)
			{
				build.agents.poll();
				if(build.fields[UNITS]>=1)
				{
					build.fields[SALES]++;
					build.fields[UNITS]--; 
					//Add food to Agent e's household
				}
				world.spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
}