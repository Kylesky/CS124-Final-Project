import java.util.*;
import java.io.*;

class ObjectHandler{
	private static ObjectHandler unique;
	
	private ObjectHandler(){}
	public static ObjectHandler getInstance(){
		if(unique == null){
			unique = new ObjectHandler();
		}
		return unique;
	}
	
	ObjectPool<Building> buildingPool;
	ObjectPool<House> housePool;
	ObjectPool<Road> roadPool;
	ObjectPool<Agent> agentPool;
	
	ArrayList<Building> buildingPrototypes;
	ArrayList<House> housePrototypes;
	ArrayList<Agent> agentPrototypes;
	
	HashMap<String, Integer> buildingPrototypeIds;
	HashMap<String, Integer> housePrototypeIds;
	HashMap<String, Integer> agentPrototypeIds;
	
	public void setup() throws Exception{
		buildingPool = new ObjectPool<Building>(Config.getBuildingInitCount(), Building.class);
		housePool = new ObjectPool<House>(Config.getHouseInitCount(), House.class);
		roadPool = new ObjectPool<Road>(Config.getRoadInitCount(), Road.class);
		agentPool = new ObjectPool<Agent>(Config.getAgentInitCount(), Agent.class);
		
		buildingPrototypes = new ArrayList<Building>();
		housePrototypes = new ArrayList<House>();
		agentPrototypes = new ArrayList<Agent>();
		
		buildingPrototypeIds = new HashMap<String, Integer>();
		housePrototypeIds = new HashMap<String, Integer>();
		agentPrototypeIds = new HashMap<String, Integer>();
		
		/* read prototypes from file
		try{
			BufferedReader sc = new BufferedReader(new FileReader(""));
		}catch(IOException ioe){
		}
		*/
		
		ArrayList<BuildingBehavior> buildingBehaviors = BuildingTypeReader.getBehaviors();
		for(int i=0; i<buildingBehaviors.size(); i++){
			Building prototype = new Building(-1, -1, buildingBehaviors.get(i), Main.world);
			buildingPrototypeIds.put(buildingBehaviors.get(i).getName(), buildingPrototypes.size());
			buildingPrototypes.add(prototype);
		}
		
		ArrayList<HouseBehavior> houseBehaviors = HouseTypeReader.getBehaviors();
		for(int i=0; i<houseBehaviors.size(); i++){
			House prototype = new House(-1, -1, houseBehaviors.get(i), Main.world);
			housePrototypeIds.put(houseBehaviors.get(i).getName(), housePrototypes.size());
			housePrototypes.add(prototype);
		}
	}
	
	public Building createBuilding(int id){
		Building prototype = buildingPrototypes.get(id);
		Building instance = buildingPool.requestInstance();
		
		instance.setWorld(Main.world);
		instance.setBehavior(prototype.getBehavior());
		instance.clearAgents(); 
		instance.clearTimes();
		instance.copyFields(prototype);
		instance.setActive(true);
		
		return instance;
	}
	
	public Building createBuilding(String name){
		if(buildingPrototypeIds.containsKey(name)){
			return createBuilding(buildingPrototypeIds.get(name));
		}else{
			return null;
		}
	}
	
	public void destroyBuilding(Building b){
		b.setActive(false);
		buildingPool.returnInstance(b);
	}
	
	public House createHouse(int id){
		House prototype = housePrototypes.get(id);
		House instance = housePool.requestInstance();
		
		instance.setWorld(Main.world);
		instance.setBehavior(prototype.getBehavior());
		instance.clearAgents();
		instance.copyFields(prototype);
		instance.setActive(true);
		
		return instance;
	}
	
	public House createHouse(String name){
		if(housePrototypeIds.containsKey(name)){
			return createHouse(housePrototypeIds.get(name));
		}else{
			return null;
		}
	}
	
	public void destroyHouse(House h){
		h.setActive(false);
		housePool.returnInstance(h);
	}
	
	public Road createRoad(){
		Road instance = roadPool.requestInstance();
		
		instance.setActive(true);
		
		return instance;
	}
	
	public void destroyRoad(Road r){
		r.setActive(false);
		roadPool.returnInstance(r);
	}
	
	public void destroyEntity(Entity e){
		if(e.getType() == Entity.BUILDING){
			destroyBuilding((Building)e);
		}else if(e.getType() == Entity.HOUSE){
			destroyHouse((House)e);
		}else if(e.getType() == Entity.ROAD){
			destroyRoad((Road)e);
		}
	}
	
	public Agent createAgent(int id){
		Agent prototype = agentPrototypes.get(id);
		Agent instance = agentPool.requestInstance();
		//clone prototype
		
		return instance;
	}
	
	public Agent createAgent(String name){
		if(agentPrototypeIds.containsKey(name)){
			return createAgent(agentPrototypeIds.get(name));
		}else{
			return null;
		}
	}
	
	public void destroyAgent(Agent a){
		a.setActive(false);
		agentPool.returnInstance(a);
	}
	
	public void generateBuildCommands(ArrayList<BuildCommand> list){
		list.add(new BuildCommand(Entity.ROAD, roadPool.requestInstance()));
		for(int i=0; i<housePrototypes.size(); i++){
			list.add(new BuildCommand(Entity.HOUSE, housePrototypes.get(i)));
		}
		for(int i=0; i<buildingPrototypes.size(); i++){
			list.add(new BuildCommand(Entity.BUILDING, buildingPrototypes.get(i)));
		}
	}
}