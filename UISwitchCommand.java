public class UISwitchCommand extends Command{
	private int UIState;
	private Paint canvas;
	public UISwitchCommand(int UIState, Paint canvas){
		this.UIState = UIState;
		this.canvas = canvas;
	}
	public void execute(Object o){
		Main.UIState = UIState;
		canvas.setSidebarPage(0);
	}
}