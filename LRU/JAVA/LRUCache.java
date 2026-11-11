import java.util.HashMap;

public class LRUCache {
    private int time=0;
    private HashMap<Integer,Integer> lru=new HashMap<>();
    private int size=0;
    private int c;
    private HashMap<Integer,Integer> used=new HashMap<>();
    public LRUCache(int capacity) {
        this.c=capacity;
    }

    public int get(int key) {
        if(lru.containsKey(key)){
            time++;
            used.put(key,time);

            return lru.get(key);
        }
        return -1;
    }

    public void put(int key, int value) {
        // Existing key
        if(lru.containsKey(key)){
            lru.put(key,value);
            time++;
            used.put(key,time);
            return;
        }
        if(size<c){
            time++;
            used.put(key,time);
            lru.put(key,value);
            size=lru.size();
        }else{
            int min=-1;
            int f=(int)1e15;
            for(int k:used.keySet()){
                if(used.get(k)<f){
                    min=k;
                    f=used.get(k);
                }
            }
            lru.remove(min);
            used.remove(min);
            time++;
            used.put(key,time);
            lru.put(key,value);
            size=lru.size();
        }
    }
}
