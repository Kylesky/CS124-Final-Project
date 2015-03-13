import java.util.*;
import java.io.*;
import java.awt.*; 

public class BuildingTypeReader
{
	BufferedReader br; 
	World world; 
	public BuildingTypeReader(World world)
	{
		this.world=world; 
	}
	public ArrayList<BuildingBehavior> getBehaviors() throws Exception
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
			int r = Integer.parseInt(tk.nextToken().trim()); 
			int g = Integer.parseInt(tk.nextToken().trim()); 
			int b = Integer.parseInt(tk.nextToken().trim()); 
			Color color = new Color(r,g,b); 
			int width = Integer.parseInt(tk.nextToken().trim());
			int height = Integer.parseInt(tk.nextToken().trim());
			int wealth = Integer.parseInt(tk.nextToken().trim());
			if(type.equals("UTILITY"))
			{
				int radius = Integer.parseInt(tk.nextToken().trim());
				ret.add(new Utility(name, color, width, height, wealth, world, radius));  
			}else if(type.equals("FOOD"))
			{
				
			}else if(type.equals("NONFOOD"))
			{
				
			}else if(type.equals("ENTERTAINMENT"))
			{
				
			}
		}
		return ret;
	}
}