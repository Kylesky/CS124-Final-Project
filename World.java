import java.awt.*;
import java.awt.image.*;
import java.util.*;
class World{
	private int width, height;
	private Entity[][] grid;
	private ArrayList<Agent> agents;
	private int CELL_SIZE = Config.getWorldCellSize();
	private long timeNanos;

	public World(int width, int height){
		this.width = width;
		this.height = height;
		this.timeNanos = 0;
		grid = new Entity[height][width];
		agents = new ArrayList<Agent>();
	}
	
	public boolean boundsCheck(int r, int c){
		return r >= 0 && c >= 0 && r <= height && c <= width;
	}
	
	public boolean isEmpty(int r, int c){
		if(!boundsCheck(r, c)) return false;
		return grid[r][c] == null;
	}
	
	public boolean isOccupied(int r, int c){
		if(!boundsCheck(r, c)) return false;
		return grid[r][c] != null;
	}
	
	public boolean isRoad(int r, int c){
		if(!boundsCheck(r, c)) return false;
		return isOccupied(r, c) && grid[r][c].getType() == Entity.ROAD;
	}
	
	public Entity getCell(int r, int c){
		if(!boundsCheck(r, c)) return null;
		return grid[r][c];
	}
	
	public void process(long deltaTime){
		timeNanos += deltaTime;
	}
	
	public String getTime(){
		long time = (timeNanos/1000000000L)%(1440);
		return String.format("%02d:%02d", time/60, time%60);
	}
	
	public void spawnAgent(Agent e, int r, int c)
	{
		
	}
	
	public long getCurrentTime()
	{
		return timeNanos; 
	}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getCellSize() {return CELL_SIZE;}
}