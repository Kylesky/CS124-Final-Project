import java.awt.*;
import java.util.*;
public class Building extends Entity
{
	String name;
	BuildingBehavior behavior; 
	ArrayDeque<Agent> agents; 
	ArrayDeque<Long> times; 
	int[] fields; 
	public Building(int r, int c, BuildingBehavior behavior, World world)
	{
		super(r,c,world,Entity.BUILDING);
		this.behavior = behavior;
		agents = new ArrayDeque<Agent>(); 
		times = new ArrayDeque<Long>(); 
		fields = new int[5]; 
		behavior.setup(this); 
	}
	
	public void addAgent(Agent e)
	{
		agents.offer(e);
		times.offer(getWorld().getCurrentTime()); 
	}
	
	public void restock()
	{
		behavior.restock(this);
	}
	
	//Get behavior of certain building flyweight
	public BuildingBehavior getbhvr()
	{
		return behavior; 
	}
	
	//Get name of building
	public String getName()
	{
		return name; 
	}
	
	//Houses override these functions so don't worry about behavior being null
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		behavior.draw(g,getR(),getC(),offsetX,offsetY); 
	}
	
	public void process(long deltaTime)
	{
		getbhvr().execute(deltaTime, this);
	}
}