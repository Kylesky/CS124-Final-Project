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
	
	public int getStock(Building build){return build.getFields()[UNITS];}
	public void restock(Building build)
	{
		//Some products are lost to spoilage, while a portion of the sales are reinvested into stocks
		build.fields[UNITS] -= .1*build.fields[UNITS]; 
		build.fields[UNITS]+= 1.7*build.fields[SALES];
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
		if(house.isTimeFlagged() && house.getTimeFlagHour() == 0 && house.getTimeFlagMinute() == 0){
			restock(build);
		}
		long curTime = build.getWorld().getCurrentTime(); 
		while(!build.agents.isEmpty())
		{
			Agent e = build.agents.peek(); 
			long time = build.times.peek(); 

			long diff = curTime - time;
			if(diff/1000000000L>=serviceTime)
			{
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
		g.drawString(build.getStock(), x+5, y+Config.getWorldCellSize()/2+15);
	}
}