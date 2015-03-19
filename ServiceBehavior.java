import java.util.*;
import java.awt.*;
public class ServiceBehavior extends BuildingBehavior
{
	//Universal parameter indices
	int radius, serviceTime; 
	String service; 
	public static final int WAIT = 0;
	public static final int TIME = 1;
	public ServiceBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int radius, String service, int serviceTime)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.radius = radius;
		this.service = service;
		this.serviceTime = serviceTime;
	}
	
	public void process(long deltaTime, Building build)
	{
		World world = build.getWorld();
		int mins = world.getMinute();
		int hours = world.getHour(); 
		int curTime = mins + hours*60; 
		
		//If waiting (meaning there are no classes yet)
		if(build.getFields()[WAIT]==0)
		{
			if(curTime - build.getFields()[TIME]>=serviceTime)
			{
				build.setField(TIME,curTime); 
				build.setField(WAIT,1); 
			}
		}
		else
		{
			if(curTime - build.getFields()[TIME]>=serviceTime)
			{
				int leftBit = -1; 
				for(int i=3; i>=1; i--) // find left most 1-bit in wealth level of this building
				{
					if((1<<(i-1) & wealth)>0)
					{
						leftBit = i; 
						break;
					}
				}
				while(!build.agents.isEmpty())
				{
						Agent e = build.agents.poll();
						long time = build.times.poll(); 
						House house = e.getHouse(); 
						int lev = house.getWealthLevel(); 
						
						//If agent cannot afford school or clinic
						if(house.getWealth()>price)
						{
							double amount;
							int satisfaction; 
							
							if(service.equals("HEALTH"))
							{
								//Clinics do satisfy Health needs with radius, but heal patients that go inside
								amount = house.getHealth()*.2; 
								satisfaction = (int)amount; 
								//wealth difference penalty
								if(leftBit<lev)satisfaction/=((lev-leftBit)*2);
																
								house.setHealth(house.getHealth()+satisfaction);
							}
							else
							{
								amount = Math.min(((double)((curTime*1000000000L) - time)/serviceTime),1);
								satisfaction = (int)(amount*(20*house.getWealthLevel()));
								//Satisfaction penalty for high-class person entering low class building
								if(leftBit < lev) satisfaction/=((lev-leftBit)*2);
								house.addNeed(service,satisfaction);
							}
							
							
							

							house.addWealth(-price);
						}
						build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
				}
				build.setField(TIME,curTime); 
				build.setField(WAIT,0); 
			}
		}
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