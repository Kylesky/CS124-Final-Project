import java.util.*;
import java.awt.*;
public class EntertainmentBehavior extends BuildingBehavior
{
	int serviceTime;
	//Universal parameter indices
	public EntertainmentBehavior(String name, int cost, Color color, int w, int h, int wealth, int serviceTime)
	{
		super(name, cost, color, w, h, wealth);
		this.serviceTime = serviceTime; 
	}
	
	public void execute(long deltaTime, Building build)
	{
		long curTime = build.getWorld().getCurrentTime(); 
		while(!build.agents.isEmpty())
		{
			Agent e = build.agents.peek(); 
			long time = build.times.peek(); 
			long diff = curTime - time; 
			if(diff>=serviceTime)
			{
				build.agents.poll();
				build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
}