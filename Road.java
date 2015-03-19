import java.awt.*;
public class Road extends Entity
{
	public static final int[] mr = {0, 1, 0, -1};
	public static final int[] mc = {1, 0, -1, 0};

	public Road(){}
	public Road(int r, int c, World world)
	{
		super(r,c,world,Entity.ROAD);
	}
	
	public void draw(Graphics2D g, int offsetX, int offsetY)
	{
		int cellSize = getWorld().getCellSize();
		int x = getC()*cellSize+offsetX;
		int y = getR()*cellSize+offsetY;
		if(x > Config.getWindowWidth() || y > Config.getWindowHeight() || x+Config.getWorldCellSize() < 0 || y+Config.getWorldCellSize() < 0) return;
		
		g.setColor(Color.BLACK);
		g.fillRect(x+1, y+1, cellSize-2, cellSize-2);
		
		g.setColor(Color.YELLOW);
		g.setStroke(Paint.roadStroke);
		for(int i=0; i<4; i++){
			if(getWorld().isRoad(getR()+mr[i], getC()+mc[i])){
				g.drawLine(x+cellSize/2, y+cellSize/2, x+cellSize/2*(1+mc[i]), y+cellSize/2*(1+mr[i]));
			}
		}
		g.setStroke(Paint.solidStroke);
		g.drawString((toDemolish()?"!":""), x+5, y+Config.getWorldCellSize()/2);
	}
	
	public void process(long deltaTime){
		//do nothing
	}
	
	public void onBuild(){}
	public void onDemolish(){}
	
	public void acceptAgent(Agent agent){}
	
	public int getCost(){return Config.getRoadCost();}
	public int getWidth(){return 1;}
	public int getHeight(){return 1;}
	public String getBehaviorName(){return "ROAD";}
}