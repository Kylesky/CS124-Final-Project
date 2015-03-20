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
	public static Color GRID_COLOR, TRANS_GREEN, TRANS_RED, CLOSED_GREY;
	public static Stroke solidStroke, brokenStroke, roadStroke;
	public static Font defFont, UIFont, mapFont;
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
		TRANS_GREEN = new Color(0, 255, 0, 31);
		TRANS_RED = new Color(255, 0, 0, 31);
		CLOSED_GREY = new Color(200, 200, 200, 255);
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
			mapFont = bufg.getFont().deriveFont(Config.getWorldCellSize()/3.f);
		}
		bufg.clearRect(0, 0, imgWidth, imgHeight);

		/*
			MAIN VIEW
		*/
		if(Main.UIState == Main.UI_STATISTICS){
			bufg.setFont(UIFont);
			
			double sat = 0;
			double wealth = 0;
			double wealthlevel = 0;
			double health = 0;
			double food = 0;
			double nonfood = 0;
			double entertainment = 0;
			double education = 0;
			double environment = 0;
			double police = 0;
			double firehouse = 0;
			int totPop = 0;
			
			for(int c=0; c<world.getWidth(); c++){
				for(int r=0; r<world.getHeight(); r++){
					if(world.isEmpty(r, c) || world.getCell(r, c).getType() != Entity.HOUSE) continue;
					House house = (House)world.getCell(r,c);
					if(house.getR() == r && house.getC() == c){
						totPop += house.getPop();
						sat += house.getSat()*house.getPop();
						wealth += house.getWealth();
						wealthlevel += house.getWealthLevel()*house.getPop();
						health += house.getHealth()*house.getPop();
						food += house.getScale("FOOD")*house.getPop();
						nonfood += house.getScale("NONFOOD")*house.getPop();
						entertainment += house.getScale("ENTERTAINMENT")*house.getPop();
						education += house.getScale("EDUCATION")*house.getPop();
						environment += house.getScale("ENVIRONMENT")*house.getPop();
						police += house.getScale("POLICE")*house.getPop();
						firehouse += house.getScale("FIREHOUSE")*house.getPop();
					}
				}
			}
			sat /= totPop;
			wealth /= totPop;
			wealthlevel /= totPop;
			health /= totPop;
			food /= totPop;
			nonfood /= totPop;
			entertainment /= totPop;
			education /= totPop;
			environment /= totPop;
			police /= totPop;
			firehouse /= totPop;
			
			bufg.drawString("Average Satisfaction: ", 50, 20);
			bufg.drawString(String.format("%.2f/%", sat), 200, 20);
			bufg.drawString("Average Wealth per Citizen: ", 50, 60);
			bufg.drawString(String.format("%.2f/%", wealth), 200, 60);
			bufg.drawString("Average Wealth Level: ", 50, 100);
			bufg.drawString(String.format("%.2f/%", wealthlevel), 200, 100);
			bufg.drawString("Average Health: ", 50, 140);
			bufg.drawString(String.format("%.2f/%", health), 200, 140);
			bufg.drawString("Average Food Supply: ", 50, 180);
			bufg.drawString(String.format("%.2f/%", food), 200, 180);
			bufg.drawString("Average Nonfood Supply: ", 50, 220);
			bufg.drawString(String.format("%.2f/%", nonfood), 200, 220);
			bufg.drawString("Average Entertainment: ", 50, 260);
			bufg.drawString(String.format("%.2f/%", entertainment), 200, 260);
			bufg.drawString("Average Education: ", 50, 300);
			bufg.drawString(String.format("%.2f/%", education), 200, 300);
			bufg.drawString("Environment Reach: ", 50, 340);
			bufg.drawString(String.format("%.2f/%", environment), 200, 340);
			bufg.drawString("Police Reach: ", 50, 380);
			bufg.drawString(String.format("%.2f/%", police), 200, 380);
			bufg.drawString("Firehouse Reach: ", 50, 420);
			bufg.drawString(String.format("%.2f/%", firehouse), 200, 420);
		}else{
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
					if(toDraw.getR() == r && toDraw.getC() == c){
						OverlayHandler.getInstance().applyOverlay(bufg, toDraw);
						toDraw.draw(bufg, (int)offsetX, (int)offsetY);
					}
				}
			}
			
			for(int i=0; i<world.getAgents(); i++){
				Agent toDraw = world.getAgent(i);
				OverlayHandler.getInstance().applyOverlay(bufg, toDraw);
				toDraw.draw(bufg, offsetX, offsetY);
			}
		
			int x = (int)((MouseHandler.getInstance().getMouseX()-offsetX)/world.getCellSize());
			int y = (int)((MouseHandler.getInstance().getMouseY()-offsetY)/world.getCellSize());
			if(Main.UIState == Main.UI_BUILD){
				if(Main.buildCommandSelected != null){
					if(world.isBuildable(Main.buildCommandSelected.getPrototype(), y, x)){
						bufg.setColor(TRANS_GREEN);
					}else{
						bufg.setColor(TRANS_RED);
					}
					bufg.fillRect(x*world.getCellSize()+(int)offsetX, y*world.getCellSize()+(int)offsetY, Main.buildCommandSelected.getPrototype().getWidth()*world.getCellSize(), Main.buildCommandSelected.getPrototype().getHeight()*world.getCellSize());
				}
			}else if(Main.UIState == Main.UI_DEMOLISH){
				if(world.isOccupied(y, x)){
					bufg.setColor(TRANS_RED);
					bufg.fillRect(world.getCell(y, x).getC()*world.getCellSize()+(int)offsetX, world.getCell(y, x).getR()*world.getCellSize()+(int)offsetY, world.getCell(y, x).getWidth()*world.getCellSize(), world.getCell(y, x).getHeight()*world.getCellSize());
				}
			}
		}
		
		/*
			RIGHT-SIDE UI
		*/
		bufg.setColor(Color.BLUE);
		bufg.fillRect(imgWidth-240, 0, 200, imgHeight);
		bufg.setColor(Color.BLACK);
		bufg.setFont(defFont);
		
		bufg.drawString("Previous", imgWidth-230, imgHeight-87);
		bufg.drawRect(imgWidth-235, imgHeight-102, 60, 20);
		bufg.drawString("Next", imgWidth-85, imgHeight-87);
		bufg.drawRect(imgWidth-90, imgHeight-102, 35, 20);
		if(Main.UIState == Main.UI_BUILD){
			int index = sidebarPage*Config.getCommandsPerPage();
			do{
				bufg.drawString(Main.buildCommands.get(index).getDisplayString(), imgWidth-230, 20+(index%Config.getCommandsPerPage())*30);
				bufg.drawRect(imgWidth-235, 5+(index%Config.getCommandsPerPage())*30, 180, 20);
			
				index++;
			}while(index%Config.getCommandsPerPage() != 0 && index < Main.buildCommands.size());
		}else if(Main.UIState == Main.UI_OVERLAYS){
			int index = sidebarPage*Config.getCommandsPerPage();
			do{
				bufg.drawString(Main.overlaySwitchCommands.get(index).getDisplayString(), imgWidth-230, 20+(index%Config.getCommandsPerPage())*30);
				bufg.drawRect(imgWidth-235, 5+(index%Config.getCommandsPerPage())*30, 180, 20);
			
				index++;
			}while(index%Config.getCommandsPerPage() != 0 && index < Main.overlaySwitchCommands.size());
			Overlay legend = OverlayHandler.getInstance().getOverlay();
			for(int i=0; i<legend.getRanges(); i++){
				bufg.setColor(legend.getColor(i));
				bufg.fillRect(imgWidth-235, imgHeight-120-i*15, 25, 10);
				bufg.setColor(Color.BLACK);
				bufg.drawRect(imgWidth-235, imgHeight-120-i*15, 25, 10);
				bufg.drawString(legend.getDisplay(i), imgWidth-205, imgHeight-110-i*15);
			}
		}
		
		/*
			BOTTOM UI
		*/
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
		
		bufg.setFont(defFont);
		bufg.drawString("Power", 670, imgHeight-65);
		bufg.drawString(String.format("%05d/%05d", world.getConsumedPower(), world.getTotalPower()), 720, imgHeight-65);
		bufg.drawString("Water", 670, imgHeight-50);
		bufg.drawString(String.format("%05d/%05d", world.getConsumedWater(), world.getTotalWater()), 720, imgHeight-50);
		
		
		/*
			FINISHED
		*/
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
		fixSidebarPage();
	}
	
	public void addSidebarPage(int pages){
		sidebarPage += pages;
		fixSidebarPage();
	}
	
	public int getSidebarPage(){
		return sidebarPage;
	}
	
	public void fixSidebarPage(){
		if(sidebarPage < 0) sidebarPage = 0;
		if(Main.UIState == Main.UI_BUILD){
			if( (Main.buildCommands.size()-1)/Config.getCommandsPerPage() < sidebarPage)
				sidebarPage = (Main.buildCommands.size()-1)/Config.getCommandsPerPage();
		}else if(Main.UIState == Main.UI_OVERLAYS){
			if( (Main.overlaySwitchCommands.size()-1)/Config.getCommandsPerPage() < sidebarPage)
				sidebarPage = (Main.overlaySwitchCommands.size()-1)/Config.getCommandsPerPage();
		}
	}
}