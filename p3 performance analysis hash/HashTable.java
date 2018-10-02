/**
 * HashTable class used to construct a hash table that 
 * uses a linked node data structure
 * Due: 3/19/2018
 * Bugs: none known
 *
 * @author       Chris Sullivan (csullivan9@wisc.edu)
 * @see also     PerformanceAnalysisHash.java, results.txt
 */
import java.util.ArrayList;

public class HashTable<K, V> implements HashTableADT<K, V> {
    //array to hold nodes
    private HashNode<K, V>[] bucket;
    //size of array
    private int currCapacity;
    //for determining when to resize array
    private double loadFactor; 
    //number of elements in hash table
    private int size;
    
    /**
     * constructor sets initial capacity and load factor
     * Param: initialCapacity, loadFactor
     */
    public HashTable(int initialCapacity, double loadFactor) {
        this.currCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        bucket = new HashNode[initialCapacity];
        size = 0;
    }
    
    /**
     * used to insert elements into hash table
     * param: key, value
     * returns: value associated with key
     */
    @Override
    public V put(K key, V value) {
        if(key == null) {
            throw new NullPointerException();
        }
        //if hash table does not need to be resized
        if(size != currCapacity * loadFactor) {
            int index = Math.abs(key.hashCode() % currCapacity);
            HashNode<K, V> node = new HashNode(key, value);
            if(bucket[index] == null) {
                bucket[index] = node;
            } else {
                node.next = bucket[index];
                bucket[index] = node;
            }
            size++;
        } else {
            //increase size of hash table and rehash
            currCapacity = currCapacity * 2;
            HashNode<K, V>[] temp = new HashNode[currCapacity];
            for(int i = 0; i < bucket.length; i++) {
                if(bucket[i] != null) {
                    HashNode tempNode = bucket[i];
                    while(tempNode != null) {
                        HashNode insert = new HashNode(tempNode.key, tempNode.value);
                        insert.next = null;
                        int rehash = Math.abs(tempNode.key.hashCode() % currCapacity);
                        HashNode currNode = temp[rehash];
                        if(currNode == null) {
                            temp[rehash] = insert;
                        } else {
                            while(currNode.next != null) {
                                currNode = currNode.next;
                            }
                            currNode.next = insert;
                        }
                        tempNode = tempNode.next;
                    }
                }
            }
            bucket = temp;
            put(key, value);
        }
        return value;
    }
    
    /**
     * clears the hash table
     */
    @Override
    public void clear() {
        bucket = null;
    }
    
    /**
     * gets the value associated with key
     * params: key -> converted into hash index 
     * returns: value associated with key
     */
    @Override
    public V get(K key) {
        HashNode<K, V> curr = bucket[Math.abs(key.hashCode() % currCapacity)];
        int random = key.hashCode() % currCapacity;
        while(!curr.key.equals(key)) {
            if(curr.next != null) {
                curr = curr.next;
            }
        }
        return curr.value;
    }
    
    /**
     * checks to see if hash table is empty
     * returns: true if is empty otherwise false
     */
    @Override
    public boolean isEmpty() {
        if(bucket.length == 0)
            return true;
        return false;
    }
    
    /**
     * removes node associated with key
     * params: key that is to be converted into hash table index
     * returns: value of key that was removed
     */
    @Override
    public V remove(K key) {
        HashNode<K, V> curr = bucket[Math.abs(key.hashCode() % currCapacity)];
        while(!curr.key.equals(key)) {
            if(curr.next != null) {
                curr = curr.next;
            }
        }
        size--;
        return curr.value;
    }

    @Override
    public int size() {
        return size;
    }
}

/**
 * inner node class for constructing hash table
 *
 * @param <K>
 * @param <V>
 */
class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next;
    
    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
