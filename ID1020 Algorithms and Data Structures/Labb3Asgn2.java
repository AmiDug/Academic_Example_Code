import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/*
time: (0.0010 seconds)
unique words: 77
total words: 100

time: (0.0030 seconds)
unique words: 258
total words: 500

time: (0.0030 seconds)
unique words: 529
total words: 1000

time: (0.0040 seconds)
unique words: 1923
total words: 5000

time: (0.0080 seconds)
unique words: 3302
total words: 10000

/*
time: (0.0010 seconds)
unique words: 77
total words: 100

time: (0.0020 seconds)
unique words: 258
total words: 500

time: (0.0020 seconds)
unique words: 529
total words: 1000

time: (0.0050 seconds)
unique words: 1923
total words: 5000

time: (0.0060 seconds)
unique words: 3302
total words: 10000

*/

public class Labb3Asgn2
{
 public static void main(String[] args) throws FileNotFoundException
 {
	 //BST<String, Integer> st = new BST<String, Integer>();
     BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();

     int minlen = 50000;
     int distinct = 0;
     int words = 0;
     File file = new File("D:\\BansheeBomb\\Memes\\98-0.txt");
     Scanner scanner = new Scanner(file, "UTF-8");
     while (minlen != 0) {
         String key = scanner.next();   
         words++;
         if (st.contains(key)) {           
             st.put(key, st.get(key) + 1); 
         } else {
             st.put(key, 1);                
             distinct++;
         }
         minlen--;
     }
     
     String max = "";
     st.put(max, 0);

     Stopwatch timer1 = new Stopwatch();   

     for (String word : st.keys()) {       
         if (st.get(word) > st.get(max))   
             max = word;
     }

     double time1 = timer1.elapsedTime();

     StdOut.printf("time: " + "(%.4f seconds)\n", time1);
     StdOut.println("unique words: " + distinct);
     StdOut.println("total words: " + words);
 }
}