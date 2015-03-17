import java.util.*;
import java.awt.*;
public class EntertainmentBehavior extends BuildingBehavior
{
	int serviceTime;
	//Universal parameter indices
	public EntertainmentBehavior(String name, String code, int power, int water, int cost, Color color, int w, int h, int wealth, int serviceTime)
	{
		super(name, code, power, water, cost, color, w, h, wealth);
		this.serviceTime = serviceTime; 
	}
	
	public void process(long deltaTime, Building build)
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
				//Add to needs here! 
				
				build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
	
	public String getNeedServiced()
	{
		return "ENTERTAINMENT"; 
	}
	
}