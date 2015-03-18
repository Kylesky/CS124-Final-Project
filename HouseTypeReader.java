import java.util.*;
import java.io.*;
import java.awt.*; 

public class HouseTypeReader
{
	static BufferedReader br; 
	public static ArrayList<HouseBehavior> getBehaviors() throws Exception
	{
		br = new BufferedReader(new FileReader("Houses.txt"));
		br.readLine();
		ArrayList<HouseBehavior> ret = new ArrayList<HouseBehavior>(); 
		while(true)
		{
			String input = br.readLine();
			if(input==null) break; 
			StringTokenizer tk = new StringTokenizer(input,"	"); 
			
			String name = tk.nextToken().trim(); 
			String code = tk.nextToken().trim(); 
			int power = Integer.parseInt(tk.nextToken().trim());
			int water = Integer.parseInt(tk.nextToken().trim());
			int cost = Integer.parseInt(tk.nextToken().trim()); 
			int r = Integer.parseInt(tk.nextToken().trim()); 
			int g = Integer.parseInt(tk.nextToken().trim()); 
			int b = Integer.parseInt(tk.nextToken().trim()); 
			Color color = new Color(r,g,b); 
			int cap = Integer.parseInt(tk.nextToken().trim()); 
			int width = Integer.parseInt(tk.nextToken().trim());
			int height = Integer.parseInt(tk.nextToken().trim());
			int startingAgents = Integer.parseInt(tk.nextToken().trim());
			int startingMoney = Integer.parseInt(tk.nextToken().trim());
			ret.add(new HouseBehavior(name, code, power, water, cost, color, cap, width, height, startingAgents, startingMoney));
		}
		return ret;
	}
}