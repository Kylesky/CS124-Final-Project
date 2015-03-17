import java.util.*;
import java.awt.*;

public class Agent{
	private int timeFlag;
	private double x, y;
	private boolean active;
	private House house;
	private AgentBehavior behavior;
	
	private Stack<Double> pathx, pathy;
	private int goalr, goalc;
	
	public Agent(){}
	public Agent(double x, double y, AgentBehavior behavior, House house){
		if(house == null) timeFlag = -1; else timeFlag = house.getWorld().getCurrentTimeFlag();
		this.x = x;
		this.y = y;
		this.house = house;
		this.behavior = behavior;
	}
	
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setActive(boolean b){active = b;}
	public void setHouse(House house){this.house = house;}
	public double getX(){return x;}
	public double getY(){return y;}
	public boolean isActive(){return active;}
	public House getHouse(){return house;}
	public AgentBehavior getBehavior(){return behavior;}
	public void setBehavior(AgentBehavior behavior){this.behavior = behavior;}
	public void setTimeFlag(int flag){timeFlag = flag;}
	public boolean isTimeFlagged(){return house.getWorld().getTimeFlag(timeFlag);}
	public int getTimeFlagHour(){return timeFlag/60;}
	public int getTimeFlagMinute(){return timeFlag%60;}
	public Stack<Double> getPathX(){return pathx;}
	public Stack<Double> getPathY(){return pathy;}
	public void setPathX(Stack<Double> s){pathx = s;}
	public void setPathY(Stack<Double> s){pathy = s;}
	public int getDestinationR(){return goalr;}
	public int getDestinationC(){return goalc;}
	public void setDestinationR(int r){goalr = r;}
	public void setDestinationC(int c){goalc = c;}
	
	public void draw(Graphics2D g, double offsetX, double offsetY){
		behavior.draw(g,x,y,offsetX,offsetY); 
	}
	
	public void __process(long deltaTime){
		process(deltaTime);
		if(house.getWorld().getTimeFlag(timeFlag)){
			timeFlag++;
			timeFlag %= 1440;
		}
	}
	
	public void setup(int r, int c){
		behavior.setup(r, c, this);
	}
	
	public void process(long deltaTime){
		behavior.process(deltaTime, this);
	}
}