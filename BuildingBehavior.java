import java.awt.*; 
import java.util.*;
//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name,code; 
	Color color; 
	int width, height, wealth, cost, price, power, water, capSize, openTime, closeTime; 
	public BuildingBehavior(String name, String code, int openTime, int closeTime, int power, int water, int cost, int price, Color color, int width, int height, int wealth, int capSize)
	{
		this.name = name;
		this.code = code;
		this.openTime = openTime;
		this.closeTime = closeTime; 
		this.color = color;
		this.width = width;
		this.height = height;
		this.wealth = wealth;
		this.cost = cost; 
		this.price = price;
		this.power = power;
		this.water = water; 
		this.capSize = capSize;
	}
	public String getName(){return name;}
	
	public int getOpenTime(){return openTime;}
	public int getCloseTime(){return closeTime;}
	public void restock(Building build){};
	
	public void __process(long deltaTime, Building build){
		if(build.isTimeFlagged() && build.getTimeFlagHour() == openTime/100 && build.getTimeFlagMinute() == openTime%100){
			build.setState(Building.STATE_OPEN);
		}
		if(build.isTimeFlagged() && build.getTimeFlagHour() == closeTime/100 && build.getTimeFlagMinute() == closeTime%100){
			build.setState(Building.STATE_CLOSED);
		}
		process(deltaTime, build);
	}
	
	public abstract void process(long deltaTime, Building build);
	public void setup(Building build){}
	public void updatePowerWater(Building build)
	{
		World world = build.getWorld(); 
		world.addConsumedPower(power);
		world.addConsumedWater(water);
	}
	public int getCost(){return cost;}
	public Color getColor(){return color;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getPrice(){return price;}
	public int getWealth(){return wealth;}
	public void onDemolish(Building build)
	{
		World world = build.getWorld(); 
		world.addConsumedPower(-power);
		world.addConsumedWater(-water);
	}
	public abstract String getNeedServiced();
	
	public boolean canEnter(int wealthLevel){
		for(int i=wealthLevel-1; i>=0; i--){
			if((wealth & (1<<i)) != 0){
				return true;
			}
		}
		return false;
	}
	
	public int getCapSize(){return capSize;}
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