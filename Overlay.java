import java.util.*;
import java.awt.*;

public class Overlay{
	private String name, basis;
	private boolean building, house, road, agent;
	private ArrayList<Integer> ranges;
	private ArrayList<Color> colors;
	
	public Overlay(String name, String basis, boolean building, boolean house, boolean road, boolean agent, ArrayList<Integer> ranges, ArrayList<Color> colors){
		this.name = name;
		this.basis = basis;
		this.building = building;
		this.house = house;
		this.road = road;
		this.agent = agent;
		this.ranges = ranges;
		this.colors = colors;
	}
	
	public String getName(){return name;}
	public String getBasis(){return basis;}
	public boolean isBuildingAffected(){return building;}
	public boolean isHouseAffected(){return house;}
	public boolean isRoadAffected(){return road;}
	public boolean isAgentAffected(){return agent;}
	public int getRanges(){return ranges.size();}
	public int getRange(int i){return ranges.get(i);}
	public Color getColor(int i){return colors.get(i);}

	public void process(Graphics2D g, Entity e){
		if(e.getType() == Entity.BUILDING){
			Building b = (Building)e;
			if(building){
				int val = 0;
				switch(basis){
					case "WEALTH": val = b.getBehavior().getWealth(); break;
				}
				for(int i=0; i<ranges.size(); i++){
					if(val <= ranges.get(i)){
						g.setColor(colors.get(i));
						break;
					}
				}
			}else{
				if(b.getState() == Building.STATE_CLOSED){
					g.setColor(Paint.CLOSED_GREY);
				}else{
					g.setColor(b.getBehavior().getColor());
				}
			}
		}else if(e.getType() == Entity.HOUSE){
			House h = (House)e;
			if(house){
				int val = 0;
				switch(basis){
					case "SATISFACTION": val = (int)h.getSat(); break;
					case "WEALTH": val = h.getWealth(); break;
					default: val = h.getScale(basis); break;
				}
				for(int i=0; i<ranges.size(); i++){
					if(val <= ranges.get(i)){
						g.setColor(colors.get(i));
						break;
					}
				}
			}else{
				g.setColor(h.getBehavior().getColor());
			}
		}else if(e.getType() == Entity.ROAD){
			Road r = (Road)e;
			if(road){
			}else{
				g.setColor(Color.BLACK);
			}
		}
	}
	
	public void process(Graphics2D g, Agent a){
		if(agent){
			int val = 0;
			switch(basis){
				case "WEALTH": val = a.getHouse().getWealth(); break;
			}
			for(int i=0; i<ranges.size(); i++){
				if(val <= ranges.get(i)){
					g.setColor(colors.get(i));
					break;
				}
			}
		}else{
			g.setColor(a.getBehavior().getColor());
		}
	}
}