import java.util.*;
public class House extends Building
{
	NeedManager needs; 
	int adults, kids, health, wealth, satisfaction; 
	public House()
	{
		adults = 1;
		kids = 0;
		health = 100; 
		wealth = 0; 
		satisfaction = 0; 
		needs = new NeedManager(this); 
	}
	
	public BuildingBehavior getbhvr()
	{
		throw new UnsupportedOperationException(); 
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
	
}