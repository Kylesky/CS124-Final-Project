public class UISwitchCommand extends Command{
	private int UIState;
	public UISwitchCommand(int UIState){
		this.UIState = UIState;
	}
	public void execute(Object o){
		Main.UIState = UIState;
	}
}