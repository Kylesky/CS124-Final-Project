import java.util.*;
import java.awt.*;
public class NeedBehavior extends BuildingBehavior
{
	int defAmount, serviceTime;
	//Universal parameter indices
	final int UNITS = 0; 
	final int SALES = 1;
	int serviced;  
	public NeedBehavior(String name, Color color, int w, int h, int wealth, World world, int defAmount, int serviceTime, int serviced)
	{
		super(name, color, w, h, wealth, world);
		this.defAmount = defAmount; 
		this.serviceTime = serviceTime; 
		this.serviced = serviced;
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
					//Add needs based on serviced to household of Agent e
				}
				world.spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
}