import java.io.*;
import javax.swing.*;

class Config{
	private static int WINDOW_WIDTH, WINDOW_HEIGHT;
	private static int WORLD_WIDTH, WORLD_HEIGHT, WORLD_CELL_SIZE;
	private static int ENTITY_INIT_COUNT, AGENT_INIT_COUNT;
	
	private Config(){}
	
	public static void setupConfig(){
		try{
			BufferedReader sc = new BufferedReader(new FileReader("Config.ini"));
			while(true){
				String s = sc.readLine();
				if(s == null) break;
				if(s.length() == 0 || s.charAt(0) == '#' || s.charAt(0) == '[') continue;
				String[] split = s.split("=");
				switch(split[0]){
					case "WindowWidth": WINDOW_WIDTH = Integer.parseInt(split[1]); break;
					case "WindowHeight": WINDOW_HEIGHT = Integer.parseInt(split[1]); break;
					case "WorldWidth": WORLD_WIDTH = Integer.parseInt(split[1]); break;
					case "WorldHeight": WORLD_HEIGHT = Integer.parseInt(split[1]); break;
					case "WorldCellSize": WORLD_CELL_SIZE = Integer.parseInt(split[1]); break;
					case "InitialEntityPoolSize": ENTITY_INIT_COUNT = Integer.parseInt(split[1]); break;
					case "InitialAgentPoolSize": AGENT_INIT_COUNT = Integer.parseInt(split[1]); break;
				}
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(Exception nfe){
			JOptionPane.showMessageDialog(null, "Error reading \"Config.ini\".");
			System.exit(0);
		}
	}
	
	public static int getWindowWidth(){return WINDOW_WIDTH;}
	public static int getWindowHeight(){return WINDOW_HEIGHT;}
	public static int getWorldWidth(){return WORLD_WIDTH;}
	public static int getWorldHeight(){return WORLD_HEIGHT;}
	public static int getWorldCellSize(){return WORLD_CELL_SIZE;}
	public static int getEntityInitCount(){return ENTITY_INIT_COUNT;}
	public static int getAgentInitCount(){return AGENT_INIT_COUNT;}
}