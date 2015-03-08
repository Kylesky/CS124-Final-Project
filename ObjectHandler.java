import java.util.*;
import java.io.*;

class ObjectHandler{
	private static final int ENTITY_INIT_COUNT = SystemPreferences.ENTITY_INIT_COUNT;

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
	
	public void setup(){
		entityPool = new ObjectPool<Entity>(ENTITY_INIT_COUNT, Entity.class);
		entityPrototypes = new ArrayList<Entity>();
		entityPrototypeIds = new HashMap<String, Integer>();
		
		/* read prototypes from file
		try{
			BufferedReader sc = new BufferedReader(new FileReader(""));
		}catch(IOException ioe){
		}
		*/
	}
	
	public Entity clone(Entity prototype){
		Entity instance = entityPool.requestInstance();
		//copy prototype
		
		return instance;
	}
	
	public Entity createEntity(int id){
		return clone(entityPrototypes.get(id));
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
}