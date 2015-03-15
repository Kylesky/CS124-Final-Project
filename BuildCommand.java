public class BuildCommand extends Command{
	private int type;
	private Entity prototype;
	
	public BuildCommand(int type, Entity prototype){
		this.type = type;
		this.prototype = prototype;
	}
	
	public String getDisplayString(){
		return prototype.getBehaviorName();
	}
	
	public Entity getPrototype(){
		return prototype;
	}
	
	public void execute(Object[] o){
		int c = (int)o[0];
		int r = (int)o[1];
		World world = (World)o[2];
		
		if(world.isBuildable(prototype, r, c)){
			world.build(type, prototype, r, c);
		}
	}
}