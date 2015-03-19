import java.util.*;
import java.awt.*;
public class NeedBehavior extends BuildingBehavior
{
	int defAmount, serviceTime;
	//Universal parameter indices
	final int UNITS = 0; 
	final int SALES = 1;
	int servingSize;
	String serviced;

	public NeedBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int defAmount, int serviceTime, String serviced, int servingSize)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.defAmount = defAmount; 
		this.serviceTime = serviceTime; 
		this.serviced = serviced;
		this.servingSize = servingSize;
	}
	
	public void restock(Building build)
	{
		//assume that they make 100% profit, and spend everything on new stock
		build.fields[UNITS] = build.fields[SALES]*=2; 
	}
	
	public void setup(Building build)
	{
		build.fields[UNITS] = defAmount; 
	}
	
	public String getNeedServiced()
	{
		return serviced; 
	}
	
	public void process(long deltaTime, Building build)
	{
		long curTime = build.getWorld().getCurrentTime(); 
		while(!build.agents.isEmpty())
		{
			Agent e = build.agents.peek(); 
			long time = build.times.peek(); 

			long diff = curTime - time;
			if(diff/1000000000L>=serviceTime)
			{
				//CHANGE SCALING IF THERE IS TIME!
				build.agents.poll();
				build.times.poll();
				House house = e.getHouse(); 
				int lev = house.getWealthLevel(); 
				int leftBit = -1; 
				for(int i=3; i>=1; i--) // find left most 1-bit
				{
					if((1<<(i-1) & wealth)>0)
					{
						leftBit = i; 
						break;
					}
				}
				//test if can purchase entire serving
				int amount = servingSize; 
				
				//if service is lower end, less satisfied, but stocks are still reduced normally
				//if(leftBit < lev) amount/=((lev-leftBit)*2);
				
				//If less than one serving left, buy everything
				if(amount>build.fields[UNITS]) amount = build.fields[UNITS];
				
				int totalCost = amount*price; 
				//if not enough money, buy as much as you can
				if(totalCost > house.getWealth()) 
				{amount = house.getWealth()/price;  totalCost = amount*price;}
				build.fields[SALES]+=amount;
				build.fields[UNITS]-=amount; 
				house.setWealth(house.getWealth()-totalCost); 
				//Add needs based on serviced to household of Agent e
				int satisfaction = amount; 
				//If person of high class is buying from low-end store, he is less satisfied with his purchase
				//Gets less satisfaction from the same amount of goods
				if(leftBit < lev) satisfaction/=((lev-leftBit)*2);
				house.addNeed(serviced,satisfaction);
				
				build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
}