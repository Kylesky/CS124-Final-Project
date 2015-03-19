import java.awt.*; 

public class HouseBehavior
{
	String name, code;
	Color color;
	int width, height, cost, capacity, power, water, startingAgents, startingMoney;
	public HouseBehavior(String name, String code, int power, int water, int cost, Color color, int capacity, int width, int height, int startingAgents, int startingMoney)
	{
		this.name = name;
		this.code = code;
		this.color = color;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.capacity = capacity;
		this.power = power;
		this.water = water;
		this.startingAgents = startingAgents;
		this.startingMoney = startingMoney;
	}
	
	public String getName(){return name;}
	public void process(long deltaTime, House house){
		if(house.isTimeFlagged() && house.getTimeFlagHour() == 0 && house.getTimeFlagMinute() == 0){
			if(house.getSat() > 100.0*house.getPop()/capacity) house.addAgent(1);
			else if(house.getHealth() < 25) house.addAgent(-1);
			
			house.addNeed("FOOD", -Math.min(house.getPop()*house.getWealthLevel()*2, house.getNeed("FOOD")));
			house.addNeed("NONFOOD", -Math.min(house.getPop()*house.getWealthLevel()*2, house.getNeed("NONFOOD")));
			house.addNeed("ENTERTAINMENT", -Math.min(house.getPop()*house.getWealthLevel()*2, house.getNeed("ENTERTAINMENT")));
			house.addNeed("EDUCATION", -Math.min(house.getPop()*house.getWealthLevel()*2, house.getNeed("EDUCATION")));
			house.addWealth((int)(house.getSat()*house.getPop()*house.getWealthLevel()));
		}
		
		if(house.isTimeFlagged() && 6 <= house.getTimeFlagHour() && house.getTimeFlagHour() < 22){
			if(Math.random() < house.getInPop()/(4.0*house.getPop())){
				house.getWorld().spawnAgent(house.getAgent(), house.getR(), house.getC());
			}
		}
	}
	
	public int getCost(){return cost;}
	public Color getColor(){return color;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getStartingAgents(){return startingAgents;}
	public int getStartingMoney(){return startingMoney;}
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY, int level, House house)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
		g.setColor(Color.WHITE);
		g.setFont(Paint.mapFont);
		g.drawString((house.toDemolish()?"!":"")+code+"("+level+")", x+5, y+Config.getWorldCellSize()/2);
	}
}