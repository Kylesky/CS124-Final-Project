public class BuildCommand extends Command{
	private int type;
	private String toBuild;
	
	public BuildCommand(int type, String toBuild){
		this.type = type;
		this.toBuild = toBuild;
	}
	
	public String getDisplayString(){
		if(type == Entity.ROAD) return "Road";
		return toBuild;
	}
	
	public void execute(Object o){
		
	}
}