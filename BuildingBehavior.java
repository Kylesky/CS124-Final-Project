import java.awt.*; 

//Building behavior also counts as a flyweight
public abstract class BuildingBehavior
{
	String name; 
	Color color; 
	World world;
	int w,h; 
	public BuildingBehavior(String name, Color color, int w, int h, World world)
	{
		this.name = name;
		this.color = color;
		this.w = w;
		this.h = h;
		this.world = world; 
	}
	
	public abstract void execute();
	
	public void draw(Graphics2D g, int r, int c, int offsetX, int offsetY)
	{
		int cellsize = Config.getWorldCellSize(); 
		int x = c*cellsize + offsetX;
		int y = r*cellsize + offsetY; 
		g.fillRect(x,y,w,h); 
	}
}