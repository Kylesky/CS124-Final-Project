import java.util.*;
import java.awt.*;
public class NeedBehavior extends BuildingBehavior
{
	int defAmount, serviceTime;
	//Universal parameter indices
	final int UNITS = 0; 
	final int SALES = 1;
	int serviced, servingSize;  
	public NeedBehavior(String name, int cost, Color color, int w,
	int h, int wealth, int defAmount, int serviceTime, int serviced, int servingSize)
	{
		super(name, cost, color, w, h, wealth);
		this.defAmount = defAmount; 
		this.serviceTime = serviceTime; 
		this.serviced = serviced;
		this.servingSize = servingSize;
	}
	
	public void restock(Building build)
	{
		build.fields[UNITS] = build.fields[SALES]*=2; 
	}
	
	public void setup(Building build)
	{
		build.fields[UNITS] = defAmount; 
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
				//CHANGE SCALING IF THERE IS TIME!
				build.agents.poll();
				House house = e.getHouse(); 
				int wealthDiff = 1 + (2*Math.abs(house.getWealth() - wealth)); 
				//If wealth = 5, universal structure
				if(wealth==5) wealthDiff = 1; 
				
				int amount = servingSize / wealthDiff; 
				if(amount>build.fields[UNITS]) amount = build.fields[UNITS];
				
				build.fields[SALES]+=amount;
				build.fields[UNITS]-=amount; 
				//Add needs based on serviced to household of Agent e
				
				NeedManager curr = house.getNeedManager();
				try{curr.addVal(serviced,amount);}
				catch(InvalidNeedException ex){System.out.println(
				"ERROR modifying needs");}
				build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
			}else break;
		}
	}
}