//Amiran Dugiev 2020-09-09
//This is an implementation of a circular linked list where you can add and remove
//variables from the start and end, also includes unit testing main method
//time complexity: O(1), memory complexity: O(N)
/*
 * output
[5]
[5],[-6]
[-6]
[0],[-6]
[0]
[h]
 */

import java.util.Iterator;

public class Labb1Asgn4 
{
	public static void main(String[] args)
	{
		CircularLinkedList<Integer> list = new CircularLinkedList<Integer>();
		list.addStart(5);
		Iterator<Integer> iterator = list.iterator();
		System.out.println("[" + iterator.next() + "]");
		list.addEnd(-6);
		iterator = list.iterator();
		System.out.println("[" + iterator.next() + "],[" + iterator.next() + "]");
		list.removeStart();
		iterator = list.iterator();
		System.out.println("[" + iterator.next() + "]");
		list.addStart(0);
		iterator = list.iterator();
		System.out.println("[" + iterator.next() + "],[" + iterator.next() + "]");
		list.removeEnd();
		iterator = list.iterator();
		System.out.println("[" + iterator.next() + "]");
		
		CircularLinkedList<Character> list2 = new CircularLinkedList<Character>();
		list2.addStart('h');
		Iterator<Character> iterator2 = list2.iterator();
		System.out.println("[" + iterator2.next() + "]");
	}
}
