import java.util.*;
import java.awt.*;
public class ServiceBehavior extends BuildingBehavior
{
	//Universal parameter indices
	int radius; 
	String service; 
	public ServiceBehavior(String name, String code, int cost, Color color, int w, int h, int wealth, int radius, String service)
	{
		super(name, code, cost, color, w, h, wealth);
		this.radius = radius;
		this.service = service;
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
	
	public void service(Building build, boolean give)
	{
		int centerc = (build.getR()+width)/2;
		int centerr = (build.getC()+height)/2; 
		World world = build.getWorld();
		HashSet<Entity> covered = new HashSet<Entity>(); 
		for(int i=centerc-radius; i<=centerc+radius; i++)
		{
			for(int j=centerr-radius; j<=centerr+radius;j++)
			{
				Entity temp = world.getCell(i,j);
				if(temp!=null && temp instanceof House && !covered.contains(temp))
				{
					if(give){} //service the Entity temp
					else //remove service from temp, building destroyed
					covered.add(temp);
				}
			}
		}
	}
}