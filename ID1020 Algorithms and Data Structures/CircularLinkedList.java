import java.util.Iterator;

public class CircularLinkedList<Item> implements Iterable<Item>
{
	private Node head; 
	private Node tail;
	private int amount;
	private class Node // nested class to define nodes
	{
		Item data;
		Node next;
		public Node(Item data){this.data = data;}
	}
	public int size() {return amount;}
	public void addStart(Item data) // Add item to the start of the list.
	{
        Node n = new Node(data);
        if(amount == 0)
        {
            head = n;
            tail = n;
            n.next = head;
        }
        else
        {
            Node temp = head;
            n.next = temp;
            head = n;
            tail.next = head;
        }
        amount++;
	}
	public void removeStart() // Remove item at the start of the list.
	{
		if(amount == 0){System.out.println("\n List is Empty");}
		else
		{
            head = head.next;
            tail.next = head;
            amount--;
        }
	}
	public void addEnd(Item data) //Add item to the end of the list
	{
		if(amount ==0){addStart(data);}
		else
		{
        	Node n = new Node(data);
        	tail.next = n;
        	tail = n;
        	tail.next = head;
        	amount++;
    	}
	}
	public void removeEnd() // Remove item at the end of the list.
	{
		if(amount == 0){System.out.println("\n List is Empty");}
		else
		{
			Node current = head;
			for(int i = 0; i < amount - 1; i++)
			{
				Item data = current.data;
				current = current.next;
			}
            tail = current;
            head.next = tail;
            amount--;
        }
	}
	public Iterator<Item> iterator() //Returns iterator
	{ 
		return new IteratorCustom();
	}
	public class IteratorCustom implements Iterator<Item> //Iterates through list
	{
		private Node current = head;
		public Item next()
		{
			Item data = current.data;
			current = current.next;
			return data;
		}
		public boolean hasNext() 
		{
			return current != null;
		}
	}
}