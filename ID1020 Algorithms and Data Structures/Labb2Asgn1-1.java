//2020-09-17 Amiran Dugiev

//This program uses insertion sort to sort a users numerical inputs

import java.util.Scanner;

class Labb2Asgn1
{ 
	//Sorts the array
	void sort(int[] arr) {
	    for (int i = 1; i < arr.length; i++) { 
	        int key = arr[i]; 
	        int j = i - 1;
	        while (j >= 0 && arr[j] > key) {
	            arr[j + 1] = arr[j];
	            j = j - 1;
	        }
	        arr[j + 1] = key;
	        printArray(arr);
	    }
	}
  
    // Prints the array 
    void printArray(int arr[]) 
    { 
        int n = arr.length; 
        for (int i=0; i<n; ++i) 
            System.out.print(arr[i]+" "); 
        System.out.println(); 
    } 
  
    // Driver code to test above 
    public static void main(String args[]) 
    { 
        Labb2Asgn1 ob = new Labb2Asgn1();
        Scanner scan = new Scanner(System.in);
        int amount = scan.nextInt();
        int arr[] = new int[amount];
        for(int i = 0; i < amount; i++)
        {
        	arr[i] = scan.nextInt();
        }
        ob.sort(arr); 
        System.out.println("Sorted array"); 
        ob.printArray(arr);
    } 
}