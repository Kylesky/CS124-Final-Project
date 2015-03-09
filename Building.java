public abstract class Building
{
	int l,w; 
	String name;
	BuildingBehavior behavior; 
	World world; 
	
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
	
}