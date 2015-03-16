import java.util.*;
import java.awt.*;
public class House extends Entity
{
	private NeedManager needs; 
	private ArrayDeque<Agent> inAgents;
	private HashSet<Agent> outAgents;
	private int health, wealth, satisfaction; 
	private HouseBehavior behavior;
	
	public House(){}
	public House(int r, int c, HouseBehavior behavior, World world)
	{
		super(r,c,world,Entity.HOUSE);
		health = 100; 
		wealth = 0;
		satisfaction = 0; 
		needs = new NeedManager(this); 
		inAgents = new ArrayDeque<Agent>();
		outAgents = new HashSet<Agent>();
		this.behavior = behavior;
	}
	
	public int getSat(){satisfaction = needs.getSat(); return satisfaction;}
	public int getPop(){return inAgents.size() + outAgents.size();}
	public void addWealth(int wealth){this.wealth += wealth;}
	public void setWealth(int wealth){this.wealth = wealth;}
	public int getWealth(){return wealth;}
	public void addHealth(int health){this.health += health;}
	public void setHealth(int health){this.health = health;}
	public int getHealth(){return health;}
	public void setBehavior(HouseBehavior behavior){this.behavior = behavior;}
	public HouseBehavior getBehavior(){return behavior;}
	public NeedManager getNeedManager(){return needs;}
	public void clearAgents(){
		if(inAgents == null) inAgents = new ArrayDeque<Agent>();
		inAgents.clear();
		if(outAgents == null) outAgents = new HashSet<Agent>();
		outAgents.clear();
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
		behavior.process(deltaTime, this);
	}
	
	public int getCost(){return getBehavior().getCost();}
	public int getWidth(){return getBehavior().getWidth();}
	public int getHeight(){return getBehavior().getHeight();}
	public String getBehaviorName(){return getBehavior().getName();}
}