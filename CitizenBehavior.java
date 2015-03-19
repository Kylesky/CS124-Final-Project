import java.util.*;
import java.awt.*; 

public class CitizenBehavior extends AgentBehavior
{
	private static int[] mr = {0, 1, 0, -1};
	private static int[] mc = {1, 0, -1, 0};
	
	public CitizenBehavior(String name, Color color, int radius)
	{
		super(name, color, radius);
	}
	
	public void setup(int r, int c, Agent agent){
		if(agent.getHouse().getWorld().getHour() >= 21 || agent.getHouse().getWorld().getHour() < 6){
			findGoal(r, c, agent, -1);
			return;
		}
	
		TreeMap<Double, Integer> priority = new TreeMap<Double, Integer>();
		double[] goals = NeedManager.getInstance().getGoalWeights(agent.getHouse());
		for(int i=0; i<goals.length; i++){
			if(goals[i] > 1e-7){
				while(priority.containsKey(goals[i])) goals[i] -= 1e-7;
				priority.put(goals[i], i);
			}
		}
		while(priority.size()>0){
			Map.Entry<Double, Integer> goal = priority.lastEntry();
			priority.remove(goal.getKey());
			if(findGoal(r, c, agent, goal.getValue())){
				return;
			}
		}
		findGoal(r, c, agent, -1);
	}
	
	public boolean findGoal(int r, int c, Agent agent, int goal){
		if(goal == -1 && agent.getHouse().getWorld().isOccupied(r, c) && agent.getHouse().getWorld().getCell(r, c) == agent.getHouse()){
			agent.setDestinationR(r);
			agent.setDestinationC(c);
			agent.setPathX(new Stack<Double>());
			agent.setPathY(new Stack<Double>());
			return true;
		}
	
		ArrayDeque<Integer> qr = new ArrayDeque<Integer>();
		ArrayDeque<Integer> qc = new ArrayDeque<Integer>();
		ArrayDeque<Integer> qm = new ArrayDeque<Integer>();
		ArrayList<Integer> possibler = new ArrayList<Integer>();
		ArrayList<Integer> possiblec = new ArrayList<Integer>();
		ArrayList<Integer> possiblew = new ArrayList<Integer>();
		
		World w = agent.getHouse().getWorld();
		int[][] parr = new int[w.getHeight()][w.getWidth()];
		int[][] parc = new int[w.getHeight()][w.getWidth()];
		for(int i=0; i<w.getHeight(); i++){
			Arrays.fill(parr[i], -1);
			Arrays.fill(parc[i], -1);
		}
		
		Entity e = w.getCell(r, c);
		for(int i=e.getC(); i<e.getC()+e.getWidth(); i++){
			if(w.isRoad(e.getR()-1, i)){
				qr.offer(e.getR()-1); qc.offer(i); qm.offer(Config.getAgentMaxWalkDistance());
				parr[e.getR()-1][i] = e.getR()-1; parc[e.getR()-1][i] = i;
			}
			if(w.isRoad(e.getR()+e.getHeight(), i)){
				qr.offer(e.getR()+e.getHeight()); qc.offer(i); qm.offer(Config.getAgentMaxWalkDistance());
				parr[e.getR()+e.getHeight()][i] = e.getR()+e.getHeight(); parc[e.getR()+e.getHeight()][i] = i;
			}
		}
		for(int i=e.getR(); i<e.getR()+e.getHeight(); i++){
			if(w.isRoad(i, e.getC()-1)){
				qr.offer(i); qc.offer(e.getC()-1); qm.offer(Config.getAgentMaxWalkDistance());
				parr[i][e.getC()-1] = i; parc[i][e.getC()-1] = e.getC()-1;
			}
			if(w.isRoad(i, e.getC()+e.getWidth())){
				qr.offer(i); qc.offer(e.getC()+e.getWidth()); qm.offer(Config.getAgentMaxWalkDistance());
				parr[i][e.getC()+e.getWidth()] = i; parc[i][e.getC()+e.getWidth()] = e.getC()+e.getWidth();
			}
		}
		
		while(qr.size()>0){
			int cr = qr.poll();
			int cc = qc.poll();
			int cm = qm.poll();
			if(cm < 0 && goal != -1) break;
			for(int i=0; i<4; i++){
				int cr2 = cr+mr[i];
				int cc2 = cc+mc[i];
				int cm2 = cm-1;
				if(w.isEmpty(cr2, cc2) || parr[cr2][cc2] != -1) continue;
				
				if(w.isRoad(cr2, cc2) || (goal == -1 && w.getCell(cr2, cc2).getType() == Entity.BUILDING)){
					qr.offer(cr2); qc.offer(cc2); qm.offer(cm2);
					parr[cr2][cc2] = cr; parc[cr2][cc2] = cc;
				}else if(w.getCell(cr2, cc2).getType() == Entity.BUILDING &&
					NeedManager.conv(((Building)w.getCell(cr2, cc2)).getBehavior().getNeedServiced()) == goal &&
					agent.getHouse().getWealth() >= ((Building)w.getCell(cr2, cc2)).getPrice() &&
					((Building)w.getCell(cr2, cc2)).getBehavior().canEnter(agent.getHouse().getWealthLevel())){
					possibler.add(cr2); possiblec.add(cc2); possiblew.add(cm2);
					parr[cr2][cc2] = cr; parc[cr2][cc2] = cc;
				}else if(goal == -1 && w.getCell(cr2, cc2) == agent.getHouse()){
					parr[cr2][cc2] = cr; parc[cr2][cc2] = cc;
					rebuild(parr, parc, cr2, cc2, agent);
					return true;
				}
			}
		}
		
		if(possibler.size() == 0) return false;
		
		int sum = 0;
		for(int i=0; i<possiblew.size(); i++) sum += possiblew.get(i);
		int rand = (int)(Math.random()*sum);
		for(int i=0; i<possiblew.size(); i++){
			if(rand < possiblew.get(i)){
				rebuild(parr, parc, possibler.get(i), possiblec.get(i), agent);
				return true;
			}else{
				rand -= possiblew.get(i);
			}
		}
		return false;
	}
	
	private void rebuild(int[][] parr, int[][] parc, int r, int c, Agent agent){
		agent.setDestinationR(r);
		agent.setDestinationC(c);
		Stack<Double> pathr = new Stack<Double>(); pathr.push((r+Math.random()*0.8+0.1)*Config.getWorldCellSize());
		Stack<Double> pathc = new Stack<Double>(); pathc.push((c+Math.random()*0.8+0.1)*Config.getWorldCellSize());
		while(true){
			int r2 = parr[r][c];
			int c2 = parc[r][c];
			if(r2 == r && c2 == c) break;
			r = r2; c = c2;
			pathr.push((r+Math.random()*0.8+0.1)*Config.getWorldCellSize());
			pathc.push((c+Math.random()*0.8+0.1)*Config.getWorldCellSize());
		}
		agent.setPathX(pathc);
		agent.setPathY(pathr);
	}
	
	public void process(long deltaTime, Agent agent){
		if(agent.getPathX().size() == 0){
			Entity destination = agent.getHouse().getWorld().getCell(agent.getDestinationR(), agent.getDestinationC());
			destination.acceptAgent(agent);
			agent.getHouse().getWorld().removeAgent(agent);
			return;
		}
		
		double destx = agent.getPathX().peek();
		double desty = agent.getPathY().peek();
		double dx = destx-agent.getX();
		double dy = desty-agent.getY();
		double v = deltaTime/100000000. * Config.getAgentSpeed();
		if(dx*dx+dy*dy < v*v){
			agent.setX(destx);
			agent.setY(desty);
			agent.getPathX().pop();
			agent.getPathY().pop();
			return;
		}
		
		double angle = Math.atan2(dy, dx);
		agent.setX(agent.getX() + v*Math.cos(angle));
		agent.setY(agent.getY() + v*Math.sin(angle));
	}
}