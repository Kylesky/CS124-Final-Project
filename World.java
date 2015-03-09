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
		agents = new ArrayList<Agent>();
	}
	
	public boolean isEmpty(int r, int c){
		return grid[r][c] == null;
	}
	
	public boolean isOccupied(int r, int c){
		return grid[r][c] != null;
	}
	
	public void process(long deltaTime){
		timeNanos += deltaTime;
	}
	
	public String getTime(){
		long time = (timeNanos/1000000000L)%(1440);
		return String.format("%02d:%02d", time/60, time%60);
	}
}