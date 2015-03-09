import java.util.*;
public class NeedManager
{
	final int FOOD = 0;
	final int POWER = 1;
	final int WATER = 2; 
	final int ENTERTAINMENT = 3; 
	final int ENVIRONMENT = 4;
	final int POLICE = 5;
	final int FIREHOUSE = 6;
	final int SCHOOL = 7;
	final int NONFOOD = 8;
	House house; 
	private int needs[];
	private int weights[]; 
	private int weightTot; 
	public NeedManager(House house)
	{
		needs = new int[9];
		weights = new int[9];
		this.house = house; 
		Arrays.fill(weights,1); 
		weights[FOOD]=3; 
		weights[NONFOOD]=3; 
		for(int i=0; i<weights.length; i++)
			weightTot+=weights[i]; 
	}
	public int getSat()
	{
		double sat = 0; 
		for(int i=0; i<needs.length; i++)
		{
			if(i==FOOD || i==NONFOOD) sat+=((double)needs[i]/house.getPop())*weights[i]; 
			else sat+=needs[i]*weights[i]; 
		}
		return (int)(100*(sat/weightTot));
	}
}