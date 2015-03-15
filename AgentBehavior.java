import java.awt.*; 

public abstract class AgentBehavior
{
	String name;
	Color color;
	int radius;
	public AgentBehavior(String name, Color color, int radius)
	{
		this.name = name;
		this.color = color;
		this.radius = radius;
	}
	
	public String getName(){return name;}
	public abstract void process(long deltaTime, Agent agent);
	public Color getColor(){return color;}
	public int getRadius(){return radius;}
	public void draw(Graphics2D g, double x, double y, double offsetX, double offsetY)
	{
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+2*radius < 0 || y+2*radius < 0) return;
		g.fillOval((int)(x-radius+offsetX),(int)(y-radius+offsetY),2*radius,2*radius); 
	}
}