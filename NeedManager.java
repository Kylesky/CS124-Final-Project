import java.util.*;
public class NeedManager
{
	public static final int FOOD = 0;
	public static final int POWER = 1;
	public static final int WATER = 2; 
	public static final int ENTERTAINMENT = 3; 
	public static final int ENVIRONMENT = 4;
	public static final int POLICE = 5;
	public static final int FIREHOUSE = 6;
	public static final int EDUCATION = 7;
	public static final int NONFOOD = 8;
	private House house; 
	private int needs[];
	private int weights[]; 
	private int weightTot; 
	
	HashMap<String, Integer> conv; 
	public NeedManager(House house)
	{
		needs = new int[9];
		weights = new int[9];
		
		conv = new HashMap<String, Integer>(); 
		conv.put("FOOD",FOOD);
		conv.put("POWER",POWER);
		conv.put("WATER",WATER);
		conv.put("ENTERTAINMENT",ENTERTAINMENT);
		conv.put("ENVIRONMENT",ENVIRONMENT);
		conv.put("POLICE",POLICE);
		conv.put("FIREHOUSE",FIREHOUSE);
		conv.put("EDUCATION",EDUCATION);
		conv.put("NONFOOD",NONFOOD);
		
		this.house = house; 
		Arrays.fill(weights,1); 
		
		//Set weights so that needs are more important in satisfaction computation
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
	
	public void addVal(String need, int val) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1){needs[flag]+=val; if(needs[flag]>100) needs[flag] = 100;}
		else throw new InvalidNeedException(); 
	}
	
	public void setVal(String need, int val) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1){needs[flag]=val; if(needs[flag]>100) needs[flag] = 100;}
		else throw new InvalidNeedException(); 
	}
	
	public int getVal(String need) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1) return needs[flag]; 
		else throw new InvalidNeedException(); 
	}
}

class InvalidNeedException extends Exception
{
	public InvalidNeedException(){
		System.out.println("INVALID NEED SELECTED");
	}
}