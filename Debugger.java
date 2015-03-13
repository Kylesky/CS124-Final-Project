import java.util.*;
import java.io.*;
import java.awt.*; 

public class Debugger
{
	static BufferedReader br; 
	public static void main(String[]args) throws Exception
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
			
			System.out.println("NAME: " + name); 
			System.out.println("TYPE: " + type); 
			System.out.println("COST: " + cost); 
			System.out.println("RGB: " + r + " " + g + " " + b); 
			System.out.println("WIDTH: " + width); 
			System.out.println("HEIGHT: " + height); 
			System.out.println("WEALTH: " + wealth); 
			if(type.equals("UTILITY"))
			{
				int cap = Integer.parseInt(tk.nextToken().trim());
				int util = Integer.parseInt(tk.nextToken().trim());
				//Util==1 is power, util==2 is water
				System.out.println("CAPACITY: " + cap);
				String env = "";
				if(util==1) env = "ELECTRICITY";
				else env = "WATER";
				System.out.println("UTILITY: " + env);
				
			}else if(type.equals("NEED"))
			{
				int defAmount = Integer.parseInt(tk.nextToken().trim());
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				int serviced = Integer.parseInt(tk.nextToken().trim());
				
				String env = "";
				if(serviced==1) env = "FOOD";
				else env = "NON-FOOD";
				
				System.out.println("DEFAULT AMOUNT: " + defAmount); 
				System.out.println("SERVICE TIME: " + serviceTime); 
				System.out.println("SERVICED: " + env); 
				
			}else if(type.equals("ENTERTAINMENT"))
			{
				int serviceTime = Integer.parseInt(tk.nextToken().trim());
				System.out.println("SERVICE TIME: " + serviceTime); 
				//ret.add(new EntertainmentBehavior(name, cost, color, width, height, wealth, serviceTime)); 
			}
			System.out.println();
		}
	}
}