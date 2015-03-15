import java.awt.*;

public class Agent{
	private double x, y;
	private boolean active;
	private House house;
	private AgentBehavior behavior;
	
	public Agent(){}
	public Agent(double x, double y, AgentBehavior behavior, House house){
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
	
	public void draw(Graphics2D g, double offsetX, double offsetY){
		behavior.draw(g,x,y,offsetX,offsetY); 
	}
	
	public void process(long deltaTime){
	}
}