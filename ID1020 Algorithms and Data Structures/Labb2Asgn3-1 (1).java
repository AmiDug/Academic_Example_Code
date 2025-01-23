//2020-09-17 Amiran Dugiev

//This program uses insertion sort to sort a users numerical inputs
//time complexity for inv: O(n^2)

import java.util.Scanner;

class Labb2Asgn3
{ 
	//Sorts the array
	void sort(int[] arr) {
		int swaps = 0;
	    for (int i = 1; i < arr.length; i++) {
	        int key = arr[i]; 
	        int j = i - 1;
	        while (j >= 0 && arr[j] > key) {
	            arr[j + 1] = arr[j];
	            swaps++;
	            j = j - 1;
	        }
	        arr[j + 1] = key;
	        printArray(arr);
	    }
	    System.out.println("Swaps: " + swaps);
	}
  
    // Prints the array 
    void printArray(int arr[]) 
    { 
        int n = arr.length; 
        for (int i=0; i<n; ++i) 
            System.out.print(arr[i]+" "); 
        System.out.println(); 
    }
    
    static void getInvCount(int[] arr) 
    { 
        int inv_count = 0; 
        for (int i = 0; i < arr.length - 1; i++) 
            for (int j = i + 1; j < arr.length; j++) 
                if (arr[i] > arr[j])
                {
                	System.out.println("[" + i + "," + arr[i] + "], " + 
                						"[" + j + "," + arr[j] + "], ");
                    inv_count++;
                }
  
        System.out.println("Inversions: " + inv_count); 
    }
  
    // Driver code to test above 
    public static void main(String args[]) 
    { 
        Labb2Asgn3 ob = new Labb2Asgn3();
        Scanner scan = new Scanner(System.in);
        int amount = scan.nextInt();
        int arr[] = new int[amount];
        for(int i = 0; i < amount; i++)
        {
        	arr[i] = scan.nextInt();
        }
        getInvCount(arr);
        ob.sort(arr); 
        System.out.println("Sorted array"); 
        ob.printArray(arr);
    } 
}