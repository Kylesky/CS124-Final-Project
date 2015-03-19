import java.awt.*;
import java.awt.image.*;
import java.util.*;
class World{
	private int width, height, playerMoney, totalPower, consumedPower, totalWater, consumedWater;
	private Entity[][] grid;
	private ArrayList<Agent> agents, toRemove;
	private int CELL_SIZE = Config.getWorldCellSize();
	private long timeNanos;
	private int timeFlag, timeFlagb;
	private boolean[] timeFlags;

	public World(int width, int height){
		this.width = width;
		this.height = height;
		this.timeNanos = 0;
		this.timeFlag = 0;
		this.timeFlagb = 0;
		grid = new Entity[height][width];
		timeFlags = new boolean[1440];
		agents = new ArrayList<Agent>();
		toRemove = new ArrayList<Agent>();
		playerMoney = Config.getStartingMoney();
		
		consumedWater = 0;
		consumedPower = 0;
		totalWater = 0;
		totalPower = 0; 
	}
	
	public int getConsumedPower(){return consumedPower;}
	public int getConsumedWater(){return consumedWater;}
	public int getTotalWater(){return totalWater;}
	public int getTotalPower(){return totalPower;}
	public void addTotalPower(int val){totalPower+=val;}
	public void addTotalWater(int val){totalWater+=val;}
	public void addConsumedPower(int val){consumedPower+=val;}
	public void addConsumedWater(int val){consumedWater+=val;}
	public void addTotalUtility(int val, String util)
	{
		if(util.equals("POWER")) totalPower+=val; 
		else totalWater+=val; 
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
		deltaTime *= Config.getGameSpeed();
		timeNanos += deltaTime;
		long time = (timeNanos/1000000000L)%1440;
		if(timeFlag < time){
			for(int i=(timeFlag==0)?1439:(timeFlag-1); timeFlags[i]; i=(i-1<0)?1439:(i-1)){
				timeFlags[i] = false;
			}
			while(timeFlag < time){
				timeFlags[timeFlag++] = true;
			}
		}else if(time < timeFlag){
			for(int i=(timeFlag==0)?1439:(timeFlag-1); timeFlags[i]; i=(i-1<0)?1439:(i-1)){
				timeFlags[i] = false;
			}
			while(timeFlag < time+1440){
				timeFlags[(timeFlag++)%1440] = true;
			}
			timeFlag %= 1440;
		}
		
		if( (getMinute() == 0 || getMinute() == 30) && timeFlags[timeFlagb]){
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					if(grid[i][j] == null) continue;
					if(grid[i][j].getR() != i || grid[i][j].getC() != j || grid[i][j].getType() != Entity.HOUSE) continue;
					((House)grid[i][j]).resetServices();
				}
			}
		}
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(grid[i][j] == null) continue;
				if(grid[i][j].getR() != i || grid[i][j].getC() != j) continue;
				grid[i][j].__process(deltaTime);
			}
		}
		
		for(int i=0; i<agents.size(); i++){
			agents.get(i).__process(deltaTime);
		}
		
		for(int i=0; i<toRemove.size(); i++){
			agents.remove(toRemove.get(i));
		}
		toRemove.clear();
		
		if(timeFlagb == 0 && timeFlags[timeFlagb]){
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					if(grid[i][j] == null) continue;
					if(grid[i][j].getR() == i && grid[i][j].getC() == j){
						if(grid[i][j].getType() == Entity.HOUSE){
							House h = ((House)grid[i][j]);
							playerMoney += ((int)(h.getSat()*h.getPop()*h.getWealthLevel()));
						}
					}
					if(grid[i][j].toDemolish()) demolish(i, j);
				}
			}
		}
		
		timeFlagb = timeFlag;
	}
	
	public void removeAgent(Agent agent){
		toRemove.add(agent);
	}
	
	public String getTime(){
		long time = (timeNanos/1000000000L)%1440;
		return String.format("%02d:%02d", time/60, time%60);
	}
	
	public int getHour(){
		return (int)(((timeNanos/1000000000L)%1440)/60);
	}
	
	public int getMinute(){
		return (int)(((timeNanos/1000000000L)%1440)%60);
	}
	
	public boolean getTimeFlag(int i){
		return timeFlags[i];
	}
	
	public int getCurrentTimeFlag(){
		return (int)((timeNanos/1000000000L)%1440);
	}
	
	public int getPlayerMoney(){
		return playerMoney;
	}
	
	public boolean isBuildable(Entity e, int r, int c){
		if(e.getCost() > playerMoney) return false;
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
	
	public void spawnAgent(Agent a, int r, int c){
		Entity e = grid[r][c];
		a.setX((e.getC()+e.getWidth()/2.0)*Config.getWorldCellSize());
		a.setY((e.getR()+e.getHeight()/2.0)*Config.getWorldCellSize());
		a.setup(r, c);
		agents.add(a);
	}
	
	public void build(int type, Entity prototype, int r, int c){
		Entity e = null;
		if(type == Entity.BUILDING){
			e = ObjectHandler.getInstance().createBuilding(prototype.getBehaviorName());
			e.setType(Entity.BUILDING);
		}else if(type == Entity.HOUSE){
			e = ObjectHandler.getInstance().createHouse(prototype.getBehaviorName());
			e.setType(Entity.HOUSE);
			((House)e).addAgent( ((House)prototype).getBehavior().getStartingAgents() );
		}else if(type == Entity.ROAD){
			e = ObjectHandler.getInstance().createRoad();
			e.setType(Entity.ROAD);
		}
		if(e == null) return;
		playerMoney -= prototype.getCost();
		e.setR(r);
		e.setC(c);
		e.setWorld(this);
		for(int i=0; i<e.getHeight(); i++){
			for(int j=0; j<e.getWidth(); j++){
				grid[r+i][c+j] = e;
			}
		}
	}
	
	public void toggleDemolish(int r, int c){
		grid[r][c].toggleDemolish();
	}
	
	public void demolish(int r, int c){
		Entity e = grid[r][c];

		for(int i=0; i<e.getHeight(); i++){
			for(int j=0; j<e.getWidth(); j++){
				grid[r+i][c+j] = null;
			}
		}
		e.onDemolish(); 
		ObjectHandler.getInstance().destroyEntity(e);
	}
}