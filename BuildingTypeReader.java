import java.util.*;
import java.io.*;
import java.awt.*; 

public class BuildingTypeReader
{
	static BufferedReader br; 
	public static ArrayList<BuildingBehavior> getBehaviors() throws Exception
	{
		br = new BufferedReader(new FileReader("Buildings.txt"));
		br.readLine();
		ArrayList<BuildingBehavior> ret = new ArrayList<BuildingBehavior>(); 
		while(true)
		{
			String input = br.readLine();
			if(input==null) break; 
			StringTokenizer tk = new StringTokenizer(input,"	");
			String type = tk.nextToken().trim(); 
			
			String name = tk.nextToken().trim(); 
			String code = tk.nextToken().trim();
			int openTime = Integer.parseInt(tk.nextToken().trim()); 
			int closeTime = Integer.parseInt(tk.nextToken().trim()); 
			int power = Integer.parseInt(tk.nextToken().trim()); 
			int water = Integer.parseInt(tk.nextToken().trim()); 
			int cost = Integer.parseInt(tk.nextToken().trim()); //cost to build building
			int price = Integer.parseInt(tk.nextToken().trim()); //price to use the building
			int r = Integer.parseInt(tk.nextToken().trim()); 
			int g = Integer.parseInt(tk.nextToken().trim()); 
			int b = Integer.parseInt(tk.nextToken().trim()); 
			Color color = new Color(r,g,b); 
			int width = Integer.parseInt(tk.nextToken().trim());
			int height = Integer.parseInt(tk.nextToken().trim());
			int wealth = Integer.parseInt(tk.nextToken().trim());
			int capSize = Integer.parseInt(tk.nextToken().trim());
			if(type.equals("UTILITY"))
			{
				int cap = Integer.parseInt(tk.nextToken().trim());
				String util = tk.nextToken().trim();
				//Util==1 is power, util==2 is water
				ret.add(new UtilityBehavior(name, code, openTime, closeTime, power, water, cost, price, color, width, height, wealth, capSize, cap, util));  
			}else if(type.equals("NEED"))
			{
				int defAmount = Integer.parseInt(tk.nextToken().trim());
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				String serviced = tk.nextToken().trim(); 
				int servingSize = Integer.parseInt(tk.nextToken().trim());
				ret.add(new NeedBehavior(name, code, openTime, closeTime, power, water, cost, price, color, width, height, wealth, capSize, defAmount, serviceTime, serviced, servingSize)); 
			}else if(type.equals("ENTERTAINMENT"))
			{
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				ret.add(new EntertainmentBehavior(name, code, openTime, closeTime, power, water, cost, price, color, width, height, wealth, capSize, serviceTime)); 
			}else if(type.equals("SERVICE"))
			{
				int radius = Integer.parseInt(tk.nextToken().trim());
				String serviced = tk.nextToken().trim(); 
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				ret.add(new ServiceBehavior(name, code, openTime, closeTime, power, water, cost, price, color,width,height,wealth, capSize, radius,serviced,serviceTime)); 
			}
		}
		return ret;
	}
}