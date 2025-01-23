//Amiran Dugiev 2020-09-09


//This program keeps reading lines from the user until a newline character is encountered
//it then reverses all previous inputs using a resizing array stack
//time complexity: O(N), memory: O(1)
/*
 * output
nsh
han
[n],[a],[h],[h],[s],[n]
 */

import java.util.Scanner;

public class Labb1Asgn2
{
	public static void main(String[] args)
	{
		ResizingArrayStack<Character> stack = new ResizingArrayStack<Character>();
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine())
		{
			String input = scan.nextLine();
			//String testInput = " ";
			//String testInput = "-1";
			//String testInput = "abcdefg";
			if (input.contains("\n")) 
			{
				break;
			}
			for(int i = 0; i < input.length(); i++)
			{
				stack.push(input.charAt(i));
			}
		}
		while(!stack.isEmpty())
		{
			System.out.print("[" + stack.pop() + "]");
			if(!stack.isEmpty()) {System.out.print(",");}
		}
		scan.close();
	}
}
