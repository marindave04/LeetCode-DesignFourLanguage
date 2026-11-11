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
/*
* Optimal Code using DLL and HashMap
* */
class LRUCache1 {
    class Node{
        int key;
        int value;
        Node prev;
        Node next;
        public Node(int key,int value){
            this.key=key;
            this.value=value;
        }
    }
    private HashMap<Integer,Node> map;
    private int capacity;
    private Node head;
    private Node tail;
    public LRUCache1(int capacity) {
        this.capacity=capacity;
        map=new HashMap<>();
        head=new Node(-1,-1);
        tail=new Node(-1,-1);
        head.next=tail;
        tail.prev=head;
    }
    public void remove(Node node){
        Node n=node.next;
        Node p=node.prev;
        p.next=n;
        n.prev=p;
    }
    public void addToHead(Node node){
        Node first=head.next;
        head.next=node;
        node.prev=head;

        node.next=first;
        first.prev=node;
    }
    public void moveToHead(Node node){
        remove(node);
        addToHead(node);
    }
    public int get(int key) {
        if(map.containsKey(key)==false){
            return -1;
        }
        Node node=map.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        //check if key is already exists or not
        if(map.containsKey(key)){
            Node node=map.get(key);
            node.value=value;
            //make that node MRU
            moveToHead(node);
        }else{
            Node node=new Node(key,value);
            map.put(key,node);
            //make that node MRU
            addToHead(node);
            if(map.size()>capacity){
                //remove the LRU which is at the left of tail
                Node lru=tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */