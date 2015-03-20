import java.util.*;
import java.awt.*;
public class ServiceBehavior extends BuildingBehavior
{
	//Universal parameter indices
	int radius, serviceTime; 
	String service; 
	public static final int WAIT = 0;
	public static final int HOUR_END = 1;
	public static final int MIN_END = 2; 
	public ServiceBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int w, int h, int wealth, int capSize, int radius, String service, int serviceTime)
	{
		super(name, code, openTime, closeTime, power, water, cost, price, color, w, h, wealth,capSize);
		this.radius = radius;
		this.service = service;
		this.serviceTime = serviceTime;
	}
	
	public void process(long deltaTime, Building build)
	{
		service(build);
		World world = build.getWorld();
		
		//If waiting (meaning there are no classes yet)
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
						
						if(service.equals("HEALTH"))
						{
							//Clinics do satisfy Health needs with radius, but heal patients that go inside
							amount = house.getWealthLevel()*10; 
							satisfaction = (int)amount; 
							//wealth difference penalty
							if(leftBit<lev)satisfaction/=((lev-leftBit)*2);
															
							house.addHealth(satisfaction);
						}
						else
						{
							int curTime = getCurTime(build); 
							amount = Math.min(((double)((curTime*1000000000L) - time)/serviceTime),1);
							satisfaction = (int)(amount*(20*house.getWealthLevel()));
							//Satisfaction penalty for high-class person entering low class building
							if(leftBit < lev) satisfaction/=((lev-leftBit)*2);
							house.addNeed(service,satisfaction);
							System.out.println(service + " SATISFACTION ADDED: " + satisfaction);
						}
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
		return curTime%1440; 
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
	public void setup(Building build)
	{
		super.setup(build);
	}
	public String getNeedServiced()
	{
		return service; 
	}
	public void onBuild(Building build){
		super.onBuild(build);
		setNextPhase(build); 
	}
	
	public void onDemolish(Building build){
		super.onDemolish(build);
	}
	
	public void service(Building build)
	{
		int centerc = build.getC()+width/2;
		int centerr = build.getR()+height/2; 
		World world = build.getWorld();
		HashSet<Entity> covered = new HashSet<Entity>(); 
		for(int i=centerc-radius; i<=centerc+radius; i++)
		{
			for(int j=centerr-radius; j<=centerr+radius;j++)
			{
				Entity temp = world.getCell(j,i);
				if(temp!=null && temp instanceof House && !covered.contains(temp))
				{
					House hold = (House)temp; 
					hold.setNeed(service, 1); 
					covered.add(temp);
				}
			}
		}
	}
	
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY, Building build)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
		g.setColor(Color.WHITE);
		g.setFont(Paint.mapFont);
		g.drawString((build.toDemolish()?"!":"")+code, x+5, y+Config.getWorldCellSize()/2);
	}
}