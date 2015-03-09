import java.util.*;
import java.awt.*;
public class House extends Entity
{
	NeedManager needs; 
	ArrayList<Agent> agents; 
	int adults, kids, health, wealth, satisfaction; 
	public House(int r, int c)
	{
		super(r,c);
		adults = 1;
		kids = 0;
		health = 100; 
		wealth = 0; 
		satisfaction = 0; 
		needs = new NeedManager(this); 
		agents = new ArrayList<Agent>(); 
	}
	
	public int getSat()
	{
		satisfaction = needs.getSat(); 
		return satisfaction; 
	}
	
	public int getPop()
	{
		return adults+kids; 
	}
	
	public int getWealth()
	{
		return wealth; 
	}
	
	public int getHealth()
	{
		return health; 
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
		
	}
	
	public void process(long deltaTime)
	{
		
	}
	
}