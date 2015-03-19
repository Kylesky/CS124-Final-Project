import java.util.*;
import java.io.*;
import java.awt.*; 

public class OverlayTypeReader
{
	static BufferedReader br; 
	public static ArrayList<Overlay> getOverlays() throws Exception
	{
		br = new BufferedReader(new FileReader("Overlays.txt"));
		br.readLine();
		ArrayList<Overlay> ret = new ArrayList<Overlay>(); 
		while(true)
		{
			String input = br.readLine();
			if(input==null) break; 
			StringTokenizer tk = new StringTokenizer(input,"	"); 
			String name = tk.nextToken().trim(); 
			String basis = tk.nextToken().trim();
			StringTokenizer tk2 = new StringTokenizer(tk.nextToken().trim(), "|");
			boolean building = false;
			boolean house = false;
			boolean road = false;
			boolean agent = false;
			while(tk2.hasMoreTokens()){
				switch(tk2.nextToken()){
					case "BUILDING": building = true; break;
					case "HOUSE": house = true; break;
					case "ROAD": road = true; break;
					case "AGENT": agent = true; break;
				}
			}
			int rangeNum = Integer.parseInt(tk.nextToken().trim()); 
			ArrayList<Integer> ranges = new ArrayList<Integer>();
			ArrayList<Color> colors = new ArrayList<Color>();
			while(rangeNum-->0){
				ranges.add(Integer.parseInt(tk.nextToken().trim()));
				tk2 = new StringTokenizer(tk.nextToken().trim(), "|");
				int r = Integer.parseInt(tk2.nextToken().trim());
				int g = Integer.parseInt(tk2.nextToken().trim());
				int b = Integer.parseInt(tk2.nextToken().trim());
				colors.add(new Color(r, g, b));
			}
			
			ret.add(new Overlay(name, basis, building, house, road, agent, ranges, colors));
		}
		return ret;
	}
}