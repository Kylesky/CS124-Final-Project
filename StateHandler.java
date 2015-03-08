import java.util.*;

class StateHandler{
	private static StateHandler unique;
	
	private StateHandler(){}
	public static StateHandler getInstance(){
		if(unique == null){
			unique = new StateHandler();
		}
		return unique;
	}
	
	//private static HashMap<String, __State> __States;
	
	public void setup(){
		//__States = new HashMap<String, __State>();
		//__States.put("__", new __State("__"));
	}
	
	/*
	public static __State request__State(String s){
		if(!__States.containsKey(s)){
			System.out.println("Warning! Requested state does not exist");
			return null;
		}else{
			return __States.get(s);
		}
	}
	*/
}