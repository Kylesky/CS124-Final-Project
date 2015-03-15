import java.awt.*;
import java.util.*;
public class Building extends Entity
{
	String name;
	BuildingBehavior behavior; 
	ArrayDeque<Agent> agents; 
	ArrayDeque<Long> times; 
	int[] fields; 
	
	public Building(){}
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
	
	public void clearAgents(){
		if(agents == null) agents = new ArrayDeque<Agent>();
		agents.clear();
	}
	public void clearTimes(){
		if(times == null) times = new ArrayDeque<Long>();
		times.clear();
	}
	public void copyFields(Building b){
		fields = Arrays.copyOf(b.fields, b.fields.length);
	}
	
	public void restock(){behavior.restock(this);}
	
	//Get behavior of certain building flyweight
	public BuildingBehavior getBehavior(){return behavior;}
	public void setBehavior(BuildingBehavior behavior){this.behavior = behavior;}
	
	//Get name of building
	public String getName(){return name;}
	public String getBehaviorName(){return getBehavior().getName();}
	
	//Houses override these functions so don't worry about behavior being null
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		behavior.draw(g,getR(),getC(),offsetX,offsetY); 
	}
	
	public void process(long deltaTime)
	{
		getBehavior().execute(deltaTime, this);
	}
	
	public int getWidth(){return getBehavior().getWidth();}
	public int getHeight(){return getBehavior().getHeight();}
}