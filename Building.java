import java.awt.*;
import java.util.*;
public class Building extends Entity
{
	private String name;
	private BuildingBehavior behavior; 
	ArrayDeque<Agent> agents; 
	ArrayDeque<Long> times; 
	int[] fields;
	private int state;
	public static final int STATE_OPEN = 0;
	public static final int STATE_CLOSED = 1;
	
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
	public int setField(int ind, int val){return fields[ind]=val;}
	public int[] getFields(){return fields;}
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
	
	public boolean isFull()
	{
		if(agents.size()==behavior.getCapSize()) return true;
		else return false; 
	}
	public void restock(){behavior.restock(this);}
	public int getStock(){return behavior.getStock(this);}
	public void onBuild(Building prototype)
	{
		setBehavior(prototype.getBehavior());
		clearAgents(); 
		clearTimes();
		copyFields(prototype);
		int time = getWorld().getHour()*100 + getWorld().getMinute();
		if(getBehavior().getOpenTime() <= time && time <= getBehavior().getCloseTime()){
			setState(STATE_OPEN);
		}else{
			setState(STATE_CLOSED);
		}
		behavior.onBuild(this);
	}
	
	//Get behavior of certain building flyweight
	public BuildingBehavior getBehavior(){return behavior;}
	public void setBehavior(BuildingBehavior behavior){this.behavior = behavior;}
	
	//Get name of building
	public String getName(){return name;}
	
	//Houses override these functions so don't worry about behavior being null
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		behavior.draw(g,getR(),getC(),offsetX,offsetY, this); 
	}
	
	public void process(long deltaTime)
	{
		getBehavior().__process(deltaTime, this);
	}
	
	public void acceptAgent(Agent agent)
	{
		addAgent(agent);
	}
	public void onDemolish(){behavior.onDemolish(this);}
	public void onBuild(){behavior.onBuild(this);}
	public int getCost(){return getBehavior().getCost();}
	public int getWidth(){return getBehavior().getWidth();}
	public int getHeight(){return getBehavior().getHeight();}
	public int getPrice(){return getBehavior().getPrice();}
	public int getState(){return state;}
	public void setState(int state){this.state = state;}
	public String getBehaviorName(){return getBehavior().getName();}
}