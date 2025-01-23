import edu.princeton.cs.algs4.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Labb3Asgn3 {
    public static class SequentialSearchST<Key, Value> {
        private Node first;

        private class Node {
            Key key;
            Value val;
            Node next;

            public Node(Key key, Value val, Node next) {
                this.key = key;
                this.val = val;
                this.next = next;
            }
        }

        public Value get(Key key) {
            for (Node x = first; x != null; x = x.next) 
                if (key.equals(x.key))                  
                    return x.val;                    
            return null;                              
        }

        public void put(Key key, Value val) {
            for (Node x = first; x != null; x = x.next) 
                if (key.equals(x.key)) {               
                    x.val = val;                       
                    return;
                }
            first = new Node(key, val, first);          
        }

        public boolean contains(Key key) {
            return get(key) != null;       
        }

        public void printST() {
            Node node = first;      
            while (node != null) {
                System.out.print(" " + node.key + ": " + node.val + ",");  
                node = node.next;   
            }
        }

        /**
         * Iterator to go through all keys, returns a queue with
         * the keys
         */
        public Iterable<Key> keys() {
            Queue<Key> queue = new Queue<Key>();
            for (Node x = first; x != null; x = x.next) 
                queue.enqueue(x.key);                   
            return queue;
        }
    }

    public static class SeparateChainingHashST<Key, Value> {
        private static final int INIT_CAPACITY = 4;
        private int N;                                
        private int M;                               
        private SequentialSearchST<Key, Value>[] st;  

        public SeparateChainingHashST() {            
            this(INIT_CAPACITY);
        }

        public SeparateChainingHashST(int M) {  
            this.M = M;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
            for (int i = 0; i < M; i++)
                st[i] = new SequentialSearchST(); 
        }

        private void resize(int chains) {
            SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
            for (int i = 0; i < M; i++) {            
                for (Key key : st[i].keys()) {      
                    temp.put(key, st[i].get(key));
                }
            }
            this.M = temp.M;       
            this.N = temp.N;
            this.st = temp.st;
        }

        private int hash(Key key) {                   
            return (key.hashCode() & 0x7fffffff) % M; 
        }

        public Value get(Key key) {
            return (Value) st[hash(key)].get(key);
        }

        public void put(Key key, Value val) {
            if (N >= 8 * M) resize(2 * M);   

            int i = hash(key);
            if (!st[i].contains(key))
                N++;                         
            st[i].put(key, val);
        }

        public void printTable() {
            for (int i = 0; i < M; i++) {
                System.out.print(i + ".");
                st[i].printST();
                System.out.println();
                System.out.println();
            }
        }
        
        public boolean contains(Key key) {
            return get(key) != null;
        }
    }

    public static void main(String[] args) throws IOException {  
    	SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
    	SeparateChainingHashST<Integer, Integer> stHash = new SeparateChainingHashST<Integer, Integer>();
        File file = new File("D:\\BansheeBomb\\Memes\\98-0.txt");
        Scanner scanner = new Scanner(file);
        String line;
        
        int minlen = 1; 
		int uniHash = 0;
		int uniWord = 0;
		
		while (scanner.hasNext()) {
			String word = scanner.next().toLowerCase();
			int hashKey = word.hashCode();
			
			if (word.length() < minlen)
				continue;
			
			if (st.contains(word)) {
				st.put(word, st.get(word) + 1);				 
			} 
			else {
				st.put(word, 1);
				uniWord++;		
			}
			
			if (stHash.contains(hashKey)) {
				stHash.put(hashKey, stHash.get(hashKey) + 1);
			}
			else {
				stHash.put(hashKey, 1);
				uniHash++;	
			}
		}
		
		System.out.println();
		System.out.println("unique hashes: " + uniHash);
		System.out.println("unique words: " + uniWord);;
    }
}