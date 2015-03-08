import java.util.*;

class ObjectPool<T>{
	private ArrayDeque<T> pool;
	private Class<T> reference;
	
	public ObjectPool(Class<T> ref){
		this(0, ref);
	}
	public ObjectPool(int initialCapacity, Class<T> ref){
		try{
			pool = new ArrayDeque<T>();
			for(int i=0; i<initialCapacity; i++){
				pool.offer(ref.newInstance());
			}
			reference = ref;
		}catch(Exception e){
		}
	}
	
	public T requestInstance(){
		if(pool.size() == 0){
			try{
				return reference.newInstance();
			}catch(Exception e){
				return null;
			}
		}
		return pool.poll();
	}
	
	public void returnInstance(T object){
		pool.offer(object);
	}
	
	public int size(){
		return pool.size();
	}
}