import java.awt.*;
import java.awt.image.*;
import java.util.*;
class World{
	private int width, height, playerMoney;
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
		playerMoney = 0;
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
	
	public int getAgents(){
		return agents.size();
	}
	
	public Agent getAgent(int i){
		if(i < 0 || i >= agents.size()) return null;
		return agents.get(i);
	}
	
	public void process(long deltaTime){
		timeNanos += deltaTime*Config.getGameSpeed();
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(grid[i][j] == null) continue;
				if(grid[i][j].getR() != i || grid[i][j].getC() != j) continue;
				grid[i][j].process(deltaTime);
			}
		}
		
		for(int i=0; i<agents.size(); i++){
			agents.get(i).process(deltaTime);
		}
	}
	
	public String getTime(){
		long time = (timeNanos/1000000000L)%(1440);
		return String.format("%02d:%02d", time/60, time%60);
	}
	
	public int getPlayerMoney(){
		return playerMoney;
	}
	
	public boolean isBuildable(Entity e, int r, int c){
		for(int i=0; i<e.getHeight(); i++){
			for(int j=0; j<e.getWidth(); j++){
				if(!isEmpty(r+i, c+j)) return false;
			}
		}
		return true;
	}
	
	public long getCurrentTime()
	{
		return timeNanos; 
	}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getCellSize() {return CELL_SIZE;}
	
	public void spawnAgent(Agent e, int r, int c)
	{
		
	}
	
	public void build(int type, Entity prototype, int r, int c){
		Entity e = null;
		if(type == Entity.BUILDING){
			e = ObjectHandler.getInstance().createBuilding(prototype.getBehaviorName());
			e.setType(Entity.BUILDING);
		}else if(type == Entity.HOUSE){
			e = ObjectHandler.getInstance().createHouse(prototype.getBehaviorName());
			e.setType(Entity.HOUSE);
		}else if(type == Entity.ROAD){
			e = ObjectHandler.getInstance().createRoad();
			e.setType(Entity.ROAD);
		}
		if(e == null) return;
		e.setR(r);
		e.setC(c);
		e.setWorld(this);
		for(int i=0; i<e.getHeight(); i++){
			for(int j=0; j<e.getWidth(); j++){
				grid[r+i][c+j] = e;
			}
		}
	}
	
	public void destroy(int r, int c){
		Entity e = grid[r][c];
		for(int i=0; i<e.getHeight(); i++){
			for(int j=0; j<e.getWidth(); j++){
				grid[r+i][c+j] = null;
			}
		}
		ObjectHandler.getInstance().destroyEntity(e);
	}
}