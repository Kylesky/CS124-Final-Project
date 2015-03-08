import java.awt.*;
import java.awt.image.*;

class World{
	private int width, height;
	private Entity[][] grid;
	private int CELL_SIZE = Config.getWorldCellSize();

	public World(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public boolean isEmpty(int r, int c){
		return grid[r][c] == null;
	}
	
	public boolean isOccupied(int r, int c){
		return grid[r][c] != null;
	}
}