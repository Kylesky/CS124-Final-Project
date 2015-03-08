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
	
	ObjectPool<Entity> entityPool;
	ArrayList<Entity> entityPrototypes;
	HashMap<String, Integer> entityPrototypeIds;
	ObjectPool<Agent> agentPool;
	ArrayList<Agent> agentPrototypes;
	HashMap<String, Integer> agentPrototypeIds;
	
	public void setup(){
		entityPool = new ObjectPool<Entity>(Config.getEntityInitCount(), Entity.class);
		entityPrototypes = new ArrayList<Entity>();
		entityPrototypeIds = new HashMap<String, Integer>();
		agentPool = new ObjectPool<Agent>(Config.getAgentInitCount(), Agent.class);
		agentPrototypes = new ArrayList<Agent>();
		agentPrototypeIds = new HashMap<String, Integer>();
		
		/* read prototypes from file
		try{
			BufferedReader sc = new BufferedReader(new FileReader(""));
		}catch(IOException ioe){
		}
		*/
	}
	
	public Entity createEntity(int id){
		Entity prototype = entityPrototypes.get(id);
		Entity instance = entityPool.requestInstance();
		//clone prototype
		
		return instance;
	}
	
	public Entity createEntity(String name){
		if(entityPrototypeIds.containsKey(name)){
			return createEntity(entityPrototypeIds.get(name));
		}else{
			return null;
		}
	}
	
	public void destroyEntity(Entity e){
		e.setActive(false);
		entityPool.returnInstance(e);
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
}