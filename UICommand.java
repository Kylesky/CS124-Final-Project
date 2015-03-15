public class UICommand extends Command{
	private int UIConv;
	private Paint canvas;
	public UICommand(int UIConv, Paint canvas){
		this.UIConv = UIConv;
		this.canvas = canvas;
	}
	public void execute(Object[] o){
		switch(UIConv){
			case Main.UI_BUILD:
				Main.UIState = Main.UI_BUILD;
				Main.buildCommandSelected = null;
				canvas.setSidebarPage(0);
				break;
			case Main.UI_DEMOLISH:
				Main.UIState = Main.UI_DEMOLISH;
				Main.buildCommandSelected = null;
				canvas.setSidebarPage(0);
				break;
			case Main.UI_OVERLAYS:
				Main.UIState = Main.UI_OVERLAYS;
				Main.buildCommandSelected = null;
				canvas.setSidebarPage(0);
				break;
			case Main.UI_STATISTICS:
				Main.UIState = Main.UI_STATISTICS;
				Main.buildCommandSelected = null;
				canvas.setSidebarPage(0);
				break;
			case Main.UI_PREVIOUS_PAGE:
				canvas.addSidebarPage(-1);
				break;
			case Main.UI_NEXT_PAGE:
				canvas.addSidebarPage(1);
				break;
		}
	}
}