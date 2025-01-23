//Amiran Dugiev 2020-09-09

//This program keeps reading lines from the user until a newline character is encountered
//it then reverses all previous inputs using a resizing array stack
//time complexity: O(N), memory complexity: O(1)
/*
 * output
abcdefg
gfedcba
 */

import java.util.Scanner;

public class Labb1Asgn2Rec 
{
	public static void main(String[] args)
	{
		String text = new String();
		text = stringBuild(text);
		text = reverseString(text);
		System.out.println(text);
	}
	public static String stringBuild(String text)
	{
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		//String testInput = " ";
		//String testInput = "-1";
		//String testInput = "abcdefg";
		text += input;
		if (input.contains("\\n")) 
		{
			return text;
		}
		stringBuild(text);
		scan.close();
		return text;
	}
	public static String reverseString(String text)
	{
		if(text.isEmpty()) {return text;}
		return reverseString(text.substring(1)) + text.charAt(0);
	}
}
