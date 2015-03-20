import java.util.*;
import java.awt.*;
public class EntertainmentBehavior extends BuildingBehavior
{
	int serviceTime;
	public static final int WAIT = 0;
	public static final int HOUR_END = 1;
	public static final int MIN_END = 2; 
	//Universal parameter indices
	public EntertainmentBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int serviceTime)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.serviceTime = serviceTime; 
	}
	
	public void process(long deltaTime, Building build)
	{
		World world = build.getWorld();
		//If waiting shows / movies are not showing yet
		//Phase computations continue even when closed
		if(build.getFields()[WAIT]==0)
		{
			if(build.isTimeFlagged() && build.getTimeFlagHour()==build.getFields()[HOUR_END] && build.getTimeFlagMinute()==build.getFields()[MIN_END])
			{
				//Compute end of next phase
				setNextPhase(build); 
				build.setField(WAIT,1); 
			}
		}
		else if(build.isTimeFlagged() && build.getTimeFlagHour()==build.getFields()[HOUR_END] && build.getTimeFlagMinute()==build.getFields()[MIN_END])
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
			//While there are agents, let them out and compute new need satisfaction
			while(!build.agents.isEmpty())
			{
					Agent e = build.agents.poll();
					long time = build.times.poll(); 
					time %= 1440000000000L;
					House house = e.getHouse(); 
					int lev = house.getWealthLevel(); 
					
					//If agent cannot afford school or clinic
					if(house.getWealth()>price)
					{
						double amount;
						int satisfaction; 
							int curTime = getCurTime(build); 
							amount = Math.min(((double)((curTime*1000000000L) - time)/serviceTime),1);
							satisfaction = (int)(amount*(20*house.getWealthLevel()));
							//Satisfaction penalty for high-class person entering low class building
							if(leftBit < lev) satisfaction/=((lev-leftBit)*2);
							house.addNeed("ENTERTAINMENT",satisfaction);
							System.out.println("ENTERTAINMENT SATISFACTION ADDED: " + satisfaction);
							house.addWealth(-price);
					}
					build.getWorld().spawnAgent(e,build.getR(),build.getC()); 
			}
			setNextPhase(build);
			build.setField(WAIT,0); 
		}
	}
	
	public int getCurTime(Building build)
	{
		World world = build.getWorld();
		int mins = world.getMinute();
		int hours = world.getHour(); 
		int curTime = mins + hours*60; 
		return curTime; 
	}
	
	public void setNextPhase(Building build)
	{
		int curTime = getCurTime(build);
		curTime += serviceTime; 
		curTime%=1440;
		//Compute the time that current phase will end (all start with waiting phase)
		int hourEnd = curTime/60;
		int minEnd = curTime%60; 
		build.setField(HOUR_END, hourEnd);
		build.setField(MIN_END, minEnd);
		System.out.println(hourEnd + ":" + minEnd);
	}
	
	public void onBuild(Building build){
		super.onBuild(build);
		setNextPhase(build); 
	}


	public String getNeedServiced()
	{
		return "ENTERTAINMENT"; 
	}
	
}