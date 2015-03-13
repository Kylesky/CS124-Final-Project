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
			StringTokenizer tk = new StringTokenizer(input,"|"); 
			
			String type = tk.nextToken().trim(); 
			String name = tk.nextToken().trim(); 
			int cost = Integer.parseInt(tk.nextToken().trim()); 
			int r = Integer.parseInt(tk.nextToken().trim()); 
			int g = Integer.parseInt(tk.nextToken().trim()); 
			int b = Integer.parseInt(tk.nextToken().trim()); 
			Color color = new Color(r,g,b); 
			int width = Integer.parseInt(tk.nextToken().trim());
			int height = Integer.parseInt(tk.nextToken().trim());
			int wealth = Integer.parseInt(tk.nextToken().trim());
			if(type.equals("UTILITY"))
			{
				int cap = Integer.parseInt(tk.nextToken().trim());
				int util = Integer.parseInt(tk.nextToken().trim());
				//Util==1 is power, util==2 is water
				ret.add(new UtilityBehavior(name, cost, color, width, height, wealth, cap, util));  
			}else if(type.equals("NEED"))
			{
				int defAmount = Integer.parseInt(tk.nextToken().trim());
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				int serviced = Integer.parseInt(tk.nextToken().trim());
				
				ret.add(new NeedBehavior(name, cost, color, width, height, wealth, defAmount, serviceTime, serviced)); 
			}else if(type.equals("ENTERTAINMENT"))
			{
				
			}
		}
		return ret;
	}
}