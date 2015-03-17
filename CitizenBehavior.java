import java.util.*;
import java.awt.*; 

public class CitizenBehavior extends AgentBehavior
{
	private static int[] mr = {0, 1, 0, -1};
	private static int[] mc = {1, 0, -1, 0};
	
	private Stack<Integer> pathr, pathc;
	private int goalr, goalc;
	
	public CitizenBehavior(String name, Color color, int radius)
	{
		super(name, color, radius);
	}
	
	public void setup(int r, int c, Agent agent){
		TreeMap<Double, Integer> priority = new TreeMap<Double, Integer>();
		double[] goals = agent.getHouse().getNeedManager().getGoalWeights();
		for(int i=0; i<goals.length; i++){
			if(goals[i] > 1e-7) priority.put(goals[i], i);
		}
		boolean returnHome = true;
		while(priority.size()>0){
			Map.Entry<Double, Integer> goal = priority.firstEntry();
			priority.remove(goal.getKey());
			if(findGoal(r, c, agent, goal.getValue())){
				returnHome = false;
				break;
			}
		}
		if(returnHome) findGoal(r, c, agent, -1);
	}
	
	public boolean findGoal(int r, int c, Agent agent, int goal){
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
			Arrays.fill(parr, -1);
			Arrays.fill(parc, -1);
		}
		
		Entity e = w.getCell(r, c);
		for(int i=e.getC()-1; i<=e.getC()+e.getWidth(); i++){
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
			for(int i=0; i<4; i++){
				int cr2 = cr+mr[i];
				int cc2 = cc+mc[i];
				int cm2 = cm-1;
				if(w.isEmpty(cr2, cc2)) continue;
				
				if(w.isRoad(cr2, cc2)){
					qr.offer(cr2); qc.offer(cc2); qm.offer(cm2);
					parr[cr2][cc2] = cr; parc[cr2][cc2] = cc;
				}else if(w.getCell(cr2, cc2).getType() == Entity.BUILDING   ){
				}
			}
		}
		
		return false;
	}
	
	public void process(long deltaTime, Agent agent){
		
	}
}