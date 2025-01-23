//Amiran Dugiev 2020-09-09
//This is an implementation of a generalized queue where you can insert element and remove them at a specified index
//time complexity: O(1), memory complexity O(N)
/*
 * output
true
[5]
[5],[6]
[5]
 */
public class Labb1Asgn5 
{
	public static void main(String[] args)
	{
		GeneralizedQueue queue = new GeneralizedQueue();
		System.out.println(queue.IsEmpty());
		queue.Insert(5);
		GeneralizedQueue.Iterator iterator = queue.iterator();
		System.out.println("[" + iterator.next() + "]");
		queue.Insert(6);
		iterator = queue.iterator();
		System.out.println("[" + iterator.next() + "],[" + iterator.next() + "]");
		queue.Delete(2);
		iterator = queue.iterator();
		System.out.println("[" + iterator.next() + "]");
	}
}
