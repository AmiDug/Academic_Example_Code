public class GeneralizedQueue
{
	private Node first;
	private Node last;
	private int N = 0;
	private class Node
	{
		int data;
		Node next;
	}
	public boolean IsEmpty(){return N == 0;}
	public int Amount(){return N;}
	public void Insert(int data) //inserts a new node with given int value into the queue
	{
		Node oldlast = last;
		last = new Node();
		last.data = data;
		last.next = null;
		if (IsEmpty()) first = last;
		else oldlast.next = last;
		N++;
	}
	public int Delete(int index) //removes a node from specified index, index 1 is the last node inserted
	{
		Node temp = first;
		for(int i = N; i > index + 1; i--)
		{
			temp = temp.next;
		}
		int val = temp.next.data;
		temp.next = temp.next.next;
		return val;
	}
	public Iterator iterator()
	{ 
		return new Iterator(); 
	}
	public class Iterator //iterates through the linked list
	{
		public Node current = first;
		public int next()
		{
			int data = current.data;
			current = current.next;
			return data;
		}
	}
}