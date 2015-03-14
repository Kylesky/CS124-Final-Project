import java.awt.*;

public class Overlay{
	private String name;
	public Overlay(String name){
		this.name = name;
	}
	
	public String getName(){return name;}

	public void process(Graphics2D g, Entity e){
		if(e.getType() == Entity.BUILDING){
			g.setColor(((Building)e).getBehavior().getColor());
		}else if(e.getType() == Entity.HOUSE){
			g.setColor(((House)e).getBehavior().getColor());
		}else if(e.getType() == Entity.ROAD){
			g.setColor(Color.BLACK);
		}
	}
}