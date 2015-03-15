public class DestroyCommand extends Command{
	public DestroyCommand(){
	}
	
	public void execute(Object[] o){
		int c = (int)o[0];
		int r = (int)o[1];
		World world = (World)o[2];
		
		if(world.isOccupied(r, c)){
			world.destroy(world.getCell(r, c).getR(), world.getCell(r, c).getC());
		}
	}
}