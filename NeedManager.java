import java.util.*;
public class NeedManager
{
	public static final int POWER = 0;
	public static final int WATER = 1;
	public static final int FOOD = 2;
	public static final int NONFOOD = 3;
	public static final int ENTERTAINMENT = 4;
	public static final int EDUCATION = 5;
	public static final int HEALTH = 6;
	public static final int ENVIRONMENT = 7;
	public static final int POLICE = 8;
	public static final int FIREHOUSE = 9;
	private House house;
	private int needs[], weights[];
	private int weightTot;
	int numNeeds;
	HashMap<String, Integer> conv;
	public NeedManager(House house)
	{
		numNeeds = 10;
		needs = new int[numNeeds];
		weights = new int[numNeeds];
		
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
		conv.put("HEALTH",HEALTH);
		
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
			double perc = needs[i]/(house.getPop()*house.getWealthLevel()*10.0);
			sat += Math.min(perc, 1)*weights[i];
		}
		return (int)(100*(sat/weightTot));
	}
	
	public void addVal(String need, int val) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1) needs[flag]+=val;
		else throw new InvalidNeedException(); 
	}
	
	//if index is passed directly
	public void addVal(int need, int val) throws InvalidNeedException
	{
		int flag = -1; 
		if(need>=0 && need<numNeeds) flag = need;
		
		if(flag!=-1) needs[flag]+=val;
		else throw new InvalidNeedException(); 
	}
	
	public void setVal(String need, int val) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1) needs[flag]=val;
		else throw new InvalidNeedException(); 
	}
	
	public int getVal(String need) throws InvalidNeedException
	{
		int flag = -1; 
		if(conv.containsKey(need)) flag = conv.get(need);
		
		if(flag!=-1) return needs[flag]; 
		else throw new InvalidNeedException(); 
	}
	
	public double[] getGoalWeights(){
		double[] ret = new double[10];
		for(int i=0; i<10; i++){
			if(2 <= i && i <= 6){
				double lack = house.getPop()*house.getWealthLevel()*10.0 - needs[i+2];
				ret[i] = Math.max(lack, 0)*weights[i+2];
			}else
				ret[i] = 0;
		}
		return ret;
	}
}

class InvalidNeedException extends Exception
{
	public InvalidNeedException(){
		System.out.println("INVALID NEED SELECTED");
	}
}