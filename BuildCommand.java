public class BuildCommand extends Command{
	private int type;
	private String toBuild;
	private Entity prototype;
	
	public BuildCommand(int type, String toBuild, Entity prototype){
		this.type = type;
		this.toBuild = toBuild;
		this.prototype = prototype;
	}
	
	public String getDisplayString(){
		if(type == Entity.ROAD) return "ROAD";
		return toBuild;
	}
	
	public Entity getPrototype(){
		return prototype;
	}
	
	public void execute(Object o){
		
	}
}