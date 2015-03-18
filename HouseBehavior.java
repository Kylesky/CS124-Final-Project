import java.awt.*; 

public class HouseBehavior
{
	String name, code;
	Color color;
	int width, height, cost, capacity, power, water;
	public HouseBehavior(String name, String code, int power, int water, int cost, Color color, int capacity, int width, int height)
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
	}
	
	public String getName(){return name;}
	public void process(long deltaTime, House house){
		if(house.isTimeFlagged() && house.getTimeFlagHour() == 0 && house.getTimeFlagMinute() == 0){
			if(house.getSat() > (house.getPop()+0.0)/capacity) house.addAgent(1);
			//consume/degenerate needs
		}
		
		if(house.isTimeFlagged() && 6 <= house.getTimeFlagHour() && house.getTimeFlagHour() < 10){
			if(Math.random() < house.getInPop()/(4.0*house.getPop())){
				house.getWorld().spawnAgent(house.getAgent(), house.getR(), house.getC());
			}
		}
	}
	
	public int getCost(){return cost;}
	public Color getColor(){return color;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY, int level)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+width*cellsize < 0 || y+height*cellsize < 0) return;
		g.fillRect(x,y,width*cellsize,height*cellsize); 
		g.setColor(Color.WHITE);
		g.setFont(Paint.mapFont);
		g.drawString(code+"("+level+")", x+5, y+Config.getWorldCellSize()/2);
	}
}