//Amiran Dugiev 2020-09-09
//This is an implementation of a double linked iterable FIFO queue
//The unit tests test for enqueue and dequeue for both integers and characters
//time complexity: O(1), memory complexity O(N)
/*
 * output
 * 
[5],[-5],[10]
[5],[-5],[10],[-6]
[-5],[10],[-6]
[c]
 */

import java.util.Iterator;
import java.util.Scanner;

public class Labb1Asgn3 
{
	public static void main(String[] args)
	{
		DoubleQueue<Integer> q = new DoubleQueue<Integer>();
		q.enqueue(5);
		q.enqueue(-5);
		q.enqueue(10);
		Iterator<Integer> iterator = q.iterator();
		while(iterator.hasNext())
		{
			System.out.print("[" + iterator.next() + "]");
			if(iterator.hasNext()) {System.out.print(",");}
		}
		System.out.println();
		q.enqueue(-6);
		iterator = q.iterator();
		while(iterator.hasNext())
		{
			System.out.print("[" + iterator.next() + "]");
			if(iterator.hasNext()) {System.out.print(",");}
		}
		System.out.println();
		q.dequeue();
		iterator = q.iterator();
		while(iterator.hasNext())
		{
			System.out.print("[" + iterator.next() + "]");
			if(iterator.hasNext()) {System.out.print(",");}
		}
		System.out.println();
		DoubleQueue<Character> z = new DoubleQueue<Character>();
		z.enqueue('c');
		Iterator<Character> iterator2 = z.iterator();
		while(iterator2.hasNext())
		{
			System.out.print("[" + iterator2.next() + "]");
			if(iterator2.hasNext()) {System.out.print(",");}
		}
	}
}
