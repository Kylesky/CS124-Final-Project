import java.util.*;
import java.awt.*;
public class ServiceBehavior extends BuildingBehavior
{
	//Universal parameter indices
	int radius, service; 
	public ServiceBehavior(String name, int cost, Color color, int w, int h, int wealth, int radius, int service)
	{
		super(name, cost, color, w, h, wealth);
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
		for(int i=0; i<world.getHeight(); i++)
		{
			for(int j=0; j<world.getWidth();j++)
			{
				Entity temp = world.getCell(i,j);
				if(temp!=null && !covered.contains(temp))
				{
					int dx = Math.abs(centerc-temp.getC());
					int dy = Math.abs(centerr-temp.getR());
					if((dy+dx)<=radius)
					{
						if(give){} //service the Entity temp
						else //remove service from temp, building destroyed
						covered.add(temp);
					}
				}
			}
		}
	}
}