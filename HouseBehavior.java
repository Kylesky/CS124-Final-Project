import java.awt.*; 

public abstract class HouseBehavior
{
	String name;
	Color color;
	World world;
	int width, height;
	public HouseBehavior(String name, Color color, int width, int height, World world)
	{
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
		this.world = world;
	}
	
	public String getName(){return name;}
	public abstract void process(long deltaTime, House house);
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY; 
		g.setColor(color);
		g.fillRect(x,y,width*cellsize,height*cellsize); 
	}
}