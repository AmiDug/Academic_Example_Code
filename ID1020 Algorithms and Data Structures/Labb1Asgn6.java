//Amiran Dugiev 2020-09-09
//This is an implementation of a sorted linked list queue
//The main method adds nodes that contain int values that the linked list sorts in ascending order
//time complexity: O(1), memory complexity: O(N)
/*
 * output
Created Linked List
[1],[3],[5],[7],[9],[10]
 */

public class Labb1Asgn6 
{
    public static void main(String args[]) 
    { 
        LinkedList llist = new LinkedList(); 
        LinkedList.Node new_node; 
        new_node = llist.newNode(5); 
        llist.sortedInsert(new_node); 
        new_node = llist.newNode(10); 
        llist.sortedInsert(new_node); 
        new_node = llist.newNode(7); 
        llist.sortedInsert(new_node); 
        new_node = llist.newNode(3); 
        llist.sortedInsert(new_node); 
        new_node = llist.newNode(1); 
        llist.sortedInsert(new_node); 
        new_node = llist.newNode(9); 
        llist.sortedInsert(new_node); 
        System.out.println("Created Linked List"); 
        llist.printList(); 
    } 
}
