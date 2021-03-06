import java.awt.*;

abstract class Entity{
	private int r, c, type, timeFlag;
	private boolean active, toDemolish;
	private World world;
	public static final int BUILDING = 0;
	public static final int HOUSE = 1;
	public static final int ROAD = 2;
	
	public Entity(){}
	public Entity(int r, int c, World world, int type){
		this.r = r;
		this.c = c;
		this.type = type;
		active = true;
		this.world = world;
		toDemolish = false;
	}
	
	public void setR(int r){this.r = r;}
	public void setC(int c){this.c = c;}
	public void setActive(boolean b){active = b;}
	public int getR(){return r;}
	public int getC(){return c;}
	public boolean isActive(){return active;}
	public void setWorld(World world){this.world = world;}
	public World getWorld(){return world;}
	public void setType(int type){this.type = type;}
	public int getType(){return type;}
	public void setTimeFlag(int flag){timeFlag = flag;}
	public void toggleDemolish(){toDemolish ^= true;}
	public boolean toDemolish(){return toDemolish;}
	public boolean isTimeFlagged(){return world.getTimeFlag(timeFlag);}
	public int getTimeFlagHour(){return timeFlag/60;}
	public int getTimeFlagMinute(){return timeFlag%60;}
	public void __process(long deltaTime){
		process(deltaTime);
		if(world.getTimeFlag(timeFlag)){
			timeFlag++;
			timeFlag %= 1440;
		}
	}
	public abstract void onBuild();
	public abstract void onDemolish();
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getCost();
	public abstract String getBehaviorName();
	public abstract void draw(Graphics2D g, int offsetX, int offsetY);
	public abstract void process(long deltaTime);
	public abstract void acceptAgent(Agent agent);
}