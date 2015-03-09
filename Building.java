import java.awt.*;
public class Building extends Entity
{
	String name;
	BuildingBehavior behavior; 
	World world; 
	
	public Building(int r, int c, BuildingBehavior behavior)
	{
		super(r,c);
		this.behavior = behavior;
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
		getbhvr().execute();
	}
}