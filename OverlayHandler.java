import java.util.*;
import java.io.*;
import java.awt.*;

class OverlayHandler{
	private static OverlayHandler unique;
	
	private OverlayHandler(){}
	public static OverlayHandler getInstance(){
		if(unique == null){
			unique = new OverlayHandler();
		}
		return unique;
	}
	
	private ArrayList<Overlay> overlays;
	private HashMap<String, Overlay> overlayMap;
	private Overlay currentOverlay;
	
	public void setup() throws Exception{
		overlays = OverlayTypeReader.getOverlays();
		overlayMap = new HashMap<String, Overlay>();
		
		for(int i=0; i<overlays.size(); i++){
			overlayMap.put(overlays.get(i).getName(), overlays.get(i));
		}
		currentOverlay = overlayMap.get("BASIC");
	}
	
	public void setOverlay(String name){
		if(overlayMap.containsKey(name))
			currentOverlay = overlayMap.get(name);
	}
	
	public void applyOverlay(Graphics2D g, Entity e){
		currentOverlay.process(g, e);
	}
	
	public void applyOverlay(Graphics2D g, Agent a){
		currentOverlay.process(g, a);
	}
	
	public void generateOverlaySwitchCommands(ArrayList<OverlaySwitchCommand> list){
		for(int i=0; i<overlays.size(); i++){
			list.add(new OverlaySwitchCommand(overlays.get(i).getName()));
		}
	}
}