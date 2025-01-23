class LinkedList 
{ 
    Node head;
  
    //Linked list node
    class Node 
    { 
        int data; 
        Node next; 
        Node(int d) {data = d; next = null; } 
    } 
  
    // inserts a new node in ascending order
    void sortedInsert(Node new_node) 
    { 
         Node current; 
         if (head == null || head.data >= new_node.data) 
         { 
            new_node.next = head; 
            head = new_node; 
         } 
         else { 
            current = head; 
            while (current.next != null && 
                   current.next.data < new_node.data) 
                  current = current.next; 
  
            new_node.next = current.next; 
            current.next = new_node; 
         } 
     } 
  
    // Creates a Node
    Node newNode(int data) 
    { 
       Node x = new Node(data); 
       return x; 
    } 
  
    // Prints the linked list
     void printList() 
     { 
         Node temp = head; 
         while (temp != null) 
         { 
            System.out.print("[" + temp.data+"]");
            temp = temp.next; 
            if(temp != null) {System.out.print(",");}
         } 
     } 
} 