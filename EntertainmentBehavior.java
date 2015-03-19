import java.util.*;
import java.awt.*;
public class EntertainmentBehavior extends BuildingBehavior
{
	int serviceTime;
	//Universal parameter indices
	public EntertainmentBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int serviceTime)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.serviceTime = serviceTime; 
	}
	
	public void setup(Building build)
	{
		World world = build.getWorld();
		int mins = world.getMinute();
		int hours = world.getHour(); 
		int curTime = mins + hours*60; 
		build.setField(1,curTime); 
	}
	
	public void process(long deltaTime, Building build)
	{
		World world = build.getWorld();
		int mins = world.getMinute();
		int hours = world.getHour(); 
		int curTime = mins + hours*60; 
		
		if(build.getFields()[0]==0)
		{
			if(curTime - build.getFields()[1]>=serviceTime)
			{
				build.setField(1,curTime); 
				build.setField(0,1); 
			}
		}
		else
		{
			if(curTime - build.getFields()[1]>=serviceTime)
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
						//If agent has no money, he goes in the theater but is not satisfied because he does not 
						//get to watch
						House house = e.getHouse(); 
						int lev = house.getWealthLevel(); 
						
						if(house.getWealth()>price)
						{
							double amount = Math.min(((double)((curTime*1000000000L) - time)/serviceTime),1);
							int satisfaction = (int)(amount*(20*house.getWealthLevel()));
							
							//Satisfaction penalty for high-class person entering low class building
							if(leftBit < lev) satisfaction/=((lev-leftBit)*2);
							
							house.addNeed("ENTERTAINMENT",satisfaction);
							house.addWealth(-price);
						}
						build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
				}
				build.setField(1,curTime); 
				build.setField(0,0); 
			}
		}
	}


	public String getNeedServiced()
	{
		return "ENTERTAINMENT"; 
	}
	
}