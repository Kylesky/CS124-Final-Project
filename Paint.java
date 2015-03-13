import java.util.*;
import java.awt.*;
import java.awt.image.*;

class Paint extends Canvas{
	private BufferedImage buf;
	private Graphics2D bufg;
	private boolean firstDraw, movx, movy;
	private int imgWidth, imgHeight;
	private double offsetX, offsetY;
	private World world;
	private Color GRID_COLOR;
	public static Stroke solidStroke, brokenStroke, roadStroke;
	public static Font defFont, UIFont;
	private int sidebarPage;
	
	public Paint(World world){
		firstDraw = true;
		offsetX = -world.getWidth()*world.getCellSize()/2;
		offsetY = -world.getHeight()*world.getCellSize()/2;
		imgWidth = Config.getWindowWidth()+50;
		imgHeight = Config.getWindowHeight()+50;
		this.world = world;
		movx = movy = false;
		sidebarPage = 0;
		
		GRID_COLOR = new Color(0, 0, 0, 31);
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		if(firstDraw){
			firstDraw = false;
			setBackground(Color.white);
			buf = (BufferedImage)createImage(imgWidth, imgHeight);
			bufg = (Graphics2D)buf.getGraphics();
			bufg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			solidStroke = bufg.getStroke();
			brokenStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{world.getCellSize()/4}, 0);
			roadStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{0, world.getCellSize()/8, world.getCellSize()/4, world.getCellSize()/8}, 0);
			defFont = bufg.getFont();
			UIFont = bufg.getFont().deriveFont(23.f);
		}
		bufg.clearRect(0, 0, imgWidth, imgHeight);

        bufg.setStroke(brokenStroke);
		bufg.setColor(GRID_COLOR);
		for(int i=0; i<=world.getWidth(); i++){
			int x = (int)(i*world.getCellSize()+offsetX);
			if(x < 0) continue;
			if(x > imgWidth) continue;
			bufg.drawLine(x, -3*world.getCellSize()/8+(int)(offsetY)%world.getCellSize()-(movy?1:0), x, imgHeight);
		}
		for(int i=0; i<=world.getHeight(); i++){
			int y = (int)(i*world.getCellSize()+offsetY);
			if(y < 0) continue;
			if(y > imgHeight) break;
			bufg.drawLine(-3*world.getCellSize()/8+(int)(offsetX)%world.getCellSize()-(movx?1:0), y, imgWidth, y);
		}
        bufg.setStroke(solidStroke);
		
		for(int c=0; c<world.getWidth(); c++){
			for(int r=0; r<world.getHeight(); r++){
				if(world.isEmpty(r, c)) continue;
				Entity toDraw = world.getCell(r, c);
				if(toDraw.getR() == r && toDraw.getC() == c)
					toDraw.draw(bufg, (int)offsetX, (int)offsetY);
			}
		}
		
		bufg.setColor(Color.BLUE);
		bufg.fillRect(0, imgHeight-80, imgWidth, 40);
		bufg.setColor(Color.BLACK);
		bufg.setFont(UIFont);
		bufg.drawString(world.getTime(), 10, imgHeight-52);
		bufg.drawRect(5, imgHeight-75, 67, 28);
		bufg.drawString(String.format("%07d", world.getPlayerMoney()), 90, imgHeight-52);
		bufg.drawRect(85, imgHeight-75, 100, 28);
		bufg.drawString("Build", 200, imgHeight-52);
		bufg.drawRect(195, imgHeight-75, 58, 28);
		bufg.drawString("Demolish", 270, imgHeight-52);
		bufg.drawRect(265, imgHeight-75, 103, 28);
		bufg.drawString("Overlays", 385, imgHeight-52);
		bufg.drawRect(380, imgHeight-75, 100, 28);
		bufg.drawString("Statistics", 500, imgHeight-52);
		bufg.drawRect(495, imgHeight-75, 100, 28);
		
		bufg.setColor(Color.BLUE);
		bufg.fillRect(imgWidth-240, 0, 200, imgHeight);
		bufg.setColor(Color.BLACK);
		bufg.setFont(defFont);
		if(Main.UIState == Main.UI_BUILD){
			int index = sidebarPage*Config.getCommandsPerPage();
			do{
				bufg.drawString(Main.buildCommands.get(index).getDisplayString(), imgWidth-230, 20+(index%Config.getCommandsPerPage())*30);
				bufg.drawRect(imgWidth-235, 5+(index%Config.getCommandsPerPage())*30, 170, 20);
			
				index++;
			}while(index%10 != 0 && index < Main.buildCommands.size());
		}else if(Main.UIState == Main.UI_OVERLAYS){
			
		}
		
		bufg.drawString(Main.world.getTime(), (int)(imgWidth+200+offsetX), (int)(imgHeight+200+offsetY));
		
		g.drawImage(buf, 0, 0, this);
	}
	
	public int getOffsetX(){
		return (int)offsetX;
	}
	
	public int getOffsetY(){
		return (int)offsetY;
	}
	
	public void addOffsetX(double x){
		movx = true;
		offsetX += x;
		if(offsetX > 0) offsetX = 0;
		if(offsetX < -(world.getWidth()+5)*world.getCellSize()+Config.getWindowWidth()+9) offsetX = -(world.getWidth()+5)*world.getCellSize()+Config.getWindowWidth()+9;
	}
	
	public void addOffsetY(double y){
		movy = true;
		offsetY += y;
		if(offsetY > 0) offsetY = 0;
		if(offsetY < -(world.getHeight()+1)*world.getCellSize()+Config.getWindowHeight()+9) offsetY = -(world.getHeight()+1)*world.getCellSize()+Config.getWindowHeight()+9;
	}
	
	public void setOffsetX(double x){
		movx = true;
		offsetX = x;
		if(offsetX > 0) offsetX = 0;
		if(offsetX < -(world.getWidth()+5)*world.getCellSize()+Config.getWindowWidth()+9) offsetX = -(world.getWidth()+5)*world.getCellSize()+Config.getWindowWidth()+9;
	}
	
	public void setOffsetY(double y){
		movy = true;
		offsetY = y;
		if(offsetY > 0) offsetY = 0;
		if(offsetY < -(world.getHeight()+1)*world.getCellSize()+Config.getWindowHeight()+9) offsetY = -(world.getHeight()+1)*world.getCellSize()+Config.getWindowHeight()+9;
	}
	
	public void setSidebarPage(int page){
		sidebarPage = page;
	}
}