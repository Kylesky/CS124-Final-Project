public class OverlaySwitchCommand extends Command{
	private String overlay;
	public OverlaySwitchCommand(String overlay){
		this.overlay = overlay;
	}
	
	public String getDisplayString(){
		return overlay;
	}
	
	public void execute(Object[] o){
		OverlayHandler.getInstance().setOverlay(overlay);
	}
}