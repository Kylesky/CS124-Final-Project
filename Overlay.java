import java.awt.*;

public class Overlay{
	private String name;
	public Overlay(String name){
		this.name = name;
	}
	
	public String getName(){return name;}

	public void process(Graphics2D g, Entity e){
		if(e.getType() == Entity.BUILDING){
			Building b = (Building)e;
			if(b.getState() == Building.STATE_CLOSED){
				g.setColor(Paint.CLOSED_GREY);
			}else{
				g.setColor(b.getBehavior().getColor());
			}
		}else if(e.getType() == Entity.HOUSE){
			House h = (House)e;
			g.setColor(h.getBehavior().getColor());
		}else if(e.getType() == Entity.ROAD){
			Road r = (Road)e;
			g.setColor(Color.BLACK);
		}
	}
	
	public void process(Graphics2D g, Agent a){
		g.setColor(a.getBehavior().getColor());
	}
}