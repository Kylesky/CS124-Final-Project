import java.util.*;
import java.awt.*;
public class ServiceBehavior extends BuildingBehavior
{
	//Universal parameter indices
	int radius; 
	String service; 
	public ServiceBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int radius, String service)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.radius = radius;
		this.service = service;
	}
	
	public void process(long deltaTime, Building build)
	{
		
	}
	
	public String getNeedServiced()
	{
		return service; 
	}
	
	public void onDemolish(Building build)
	{
		service(build,false);
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
					House hold = (House)temp; 
					if(give) hold.setNeed(service, 1); // add service
					else hold.setNeed(service,0); //remove service from temp, building destroyed
					covered.add(temp);
				}
			}
		}
	}
}