import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Labb3Asgn4 
{
        public static void main(String[] args) throws FileNotFoundException
        {
            File file = new File("D:\\BansheeBomb\\Memes\\98-0.txt");
            BinarySearchST<String, LinkedList<Integer>> st = new BinarySearchST<String, LinkedList<Integer>>();
            Scanner scanner = new Scanner(file, "UTF-8"); 
            String keyword = "city";
            int index = 0;
            while (scanner.hasNext()) 
            {
            	index++;
            	String input = scanner.next();
            	if(input == keyword)
            	{
            		st.put(input, new LinkedList<Integer>());
            		st.get(input).add(index);
            	}
            }
            System.out.print("index: ");
    		for(Integer i : st.get(keyword)) {
    			System.out.print(i + " ");
    		}
            scanner.close();
            }
}

