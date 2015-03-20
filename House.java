import java.util.*;
import java.awt.*;
public class House extends Entity
{
	private ArrayDeque<Agent> inAgents;
	private HashSet<Agent> outAgents;
	private int health, wealth; 
	private HouseBehavior behavior;
	private int needs[];
	
	public House(){}
	public House(int r, int c, HouseBehavior behavior, World world)
	{
		super(r,c,world,Entity.HOUSE);
		health = 100;
		wealth = behavior.getStartingMoney();
		needs = new int[NeedManager.NUMNEEDS];
		inAgents = new ArrayDeque<Agent>();
		outAgents = new HashSet<Agent>();
		this.behavior = behavior;
	}
	
	public double getSat(){return NeedManager.getInstance().getSat(this);}
	public int getInPop(){return inAgents.size();}
	public int getOutPop(){return outAgents.size();}
	public void putAgent(Agent agent){inAgents.offer(agent); outAgents.remove(agent);}
	public Agent getAgent(){Agent agent = inAgents.poll(); outAgents.add(agent); return agent;}
	public int getPop(){return inAgents.size() + outAgents.size();}
	public void addWealth(int wealth){this.wealth += wealth;}
	public void setWealth(int wealth){this.wealth = wealth;}
	public int getWealth(){return wealth;}
	public void addHealth(int health){this.health += health;}
	public void setHealth(int health){this.health = health;}
	public int getHealth(){return health;}
	public void setBehavior(HouseBehavior behavior){this.behavior = behavior;}
	public HouseBehavior getBehavior(){return behavior;}
	public int getNeed(int need){if(need < 0 || need > NeedManager.NUMNEEDS) return -1; else return needs[need];}
	public void clearAgents(){
		if(inAgents == null) inAgents = new ArrayDeque<Agent>();
		inAgents.clear();
		if(outAgents == null) outAgents = new HashSet<Agent>();
		outAgents.clear();
	}
	public void copyFields(House h){
		health = h.health;
		wealth = h.wealth;
	}
	public void setupNeeds(){
		needs = new int[NeedManager.NUMNEEDS];
	}
	
	public int getWealthLevel()
	{
		if(wealth<0) return 0;
		else if(wealth<=getPop()*Config.getLowWealthBracket()) return 1; 
		else if(wealth<=getPop()*Config.getMediumWealthBracket()) return 2;
		else return 3; 
	}
	
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		behavior.draw(g,getR(),getC(),offsetX,offsetY, getWealthLevel(), this); 
	}
	
	public void process(long deltaTime)
	{
		//Agents coming out and stuff
		behavior.process(deltaTime, this);
	}
	
	public void acceptAgent(Agent agent){
		putAgent(agent);
	}
	
	public void addAgent(int num){
		while(num<0){
			ObjectHandler.getInstance().destroyAgent(inAgents.poll());
			num++;
		}
		while(num>0){
			Agent agent = ObjectHandler.getInstance().createAgent("CITIZEN");
			agent.setHouse(this);
			inAgents.offer(agent);
			num--;
		}
	}
	
	public int getCost(){return getBehavior().getCost();}
	public int getWidth(){return getBehavior().getWidth();}
	public int getHeight(){return getBehavior().getHeight();}
	public String getBehaviorName(){return getBehavior().getName();}
	
	public void addNeed(String need, int val){ needs[NeedManager.conv(need)]+=val; }
	public void addNeed(int need, int val){ needs[need]+=val; }
	public void setNeed(String need, int val){ needs[NeedManager.conv(need)]=val; }
	public int getNeed(String need){ return needs[NeedManager.conv(need)]; }
	public int getScale(String need){
		switch(need){
			case "SATISFACTION": return (int)getSat();
			case "WEALTH": return getWealthLevel();
			case "HEALTH": return (int)(getHealth()/(getPop()*getWealthLevel()*10.0));
			default: return (int)(100*NeedManager.getInstance().getIndivSat(this, NeedManager.conv(need))+1e-7);
		}
	}
	
	public void resetServices(){
		for(int i=7; i<10; i++){
			needs[i] = 0;
		}
	}
	
	public void onDemolish()
	{
		behavior.onDemolish(this);
	}
	public void onBuild()
	{
		behavior.onBuild(this);
	}
}