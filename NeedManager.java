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
	
	public static final int NUMNEEDS = 10;
	public static final int[] weights = {1, 1, 3, 3, 1, 1, 1, 1, 1, 1};
	public static final int weightTot = 14;
	
	public static int conv(String s) throws InvalidNeedException{
		switch(s){
			case "POWER": return POWER;
			case "WATER": return WATER;
			case "FOOD": return FOOD;
			case "NONFOOD": return NONFOOD;
			case "ENTERTAINMENT": return ENTERTAINMENT;
			case "EDUCATION": return EDUCATION;
			case "HEALTH": return HEALTH;
			case "ENVIRONMENT": return ENVIRONMENT;
			case "POLICE": return POLICE;
			case "FIREHOUSE": return FIREHOUSE;
			default: return -1;
		}
	}
	
	private static NeedManager unique;
	
	private NeedManager(){}
	public static NeedManager getInstance(){
		if(unique == null){
			unique = new NeedManager();
		}
		return unique;
	}
	
	public int getSat(House house)
	{
		double sat = 0;
		double req = house.getPop()*house.getWealthLevel()*10.0;
		for(int i=0; i<NUMNEEDS; i++)
		{
			sat += Math.min(house.getNeed(i)/req, 1)*weights[i];
		}
		return 100*(sat/weightTot);
	}
	
	public double[] getGoalWeights(House house){
		double[] ret = new double[10];
		for(int i=0; i<10; i++){
			if(2 <= i && i <= 6){
				double lack = house.getPop()*house.getWealthLevel()*10.0 - house.getNeed(i);
				ret[i] = Math.max(lack, 0)*weights[i];
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