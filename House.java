import java.util.*;
import java.awt.*;
public class House extends Entity
{
	NeedManager needs; 
	ArrayList<Agent> agents; 
	int adults, kids, health, wealth, satisfaction; 
	HouseBehavior behavior;
	
	public House(){}
	public House(int r, int c, HouseBehavior behavior, World world)
	{
		super(r,c,world,Entity.HOUSE);
		adults = 1;
		kids = 0;
		health = 100; 
		wealth = 0;
		satisfaction = 0; 
		needs = new NeedManager(this); 
		agents = new ArrayList<Agent>();
		this.behavior = behavior;
	}
	
	public int getSat(){satisfaction = needs.getSat(); return satisfaction;}
	public int getPop(){return adults+kids;}
	public void addWealth(int wealth){this.wealth += wealth;}
	public void setWealth(int wealth){this.wealth = wealth;}
	public int getWealth(){return wealth;}
	public void addHealth(int health){this.health += health;}
	public void setHealth(int health){this.health = health;}
	public int getHealth(){return health;}
	public void setBehavior(HouseBehavior behavior){this.behavior = behavior;}
	public HouseBehavior getBehavior(){return behavior;}
	
	public void clearAgents(){
		if(agents == null) agents = new ArrayList<Agent>();
		agents.clear();
	}
	public void copyFields(House h){
	}
	
	public int getWealthLevel()
	{
		if(wealth<0) return 0;
		else if(wealth<=30000) return 1; 
		else if(wealth>30000 && wealth<=70000) return 2;
		else return 3; 
	}
	
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		behavior.draw(g,getR(),getC(),offsetX,offsetY); 
	}
	
	public void process(long deltaTime)
	{
		//Agents coming out and stuff
	}
	
	public int getWidth(){return getBehavior().getWidth();}
	public int getHeight(){return getBehavior().getHeight();}
	public String getBehaviorName(){return getBehavior().getName();}
}