import java.util.*;
import java.awt.*;
public class House extends Entity
{
	NeedManager needs; 
	ArrayList<Agent> agents; 
	int adults, kids, health, wealth, satisfaction, w, h; 
	public House(int r, int c, World world)
	{
		super(r,c,world);
		adults = 1;
		kids = 0;
		health = 100; 
		wealth = 0; 
		satisfaction = 0; 
		needs = new NeedManager(this); 
		agents = new ArrayList<Agent>(); 
		w=2;
		h=2;
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
		int cellsize = Config.getWorldCellSize(); 
		int x = getC()*cellsize + offsetX;
		int y = getR()*cellsize + offsetY; 
		g.fillRect(x,y,w,h); 
	}
	
	public void process(long deltaTime)
	{
		//Agents coming out and stuff
	}
	
}