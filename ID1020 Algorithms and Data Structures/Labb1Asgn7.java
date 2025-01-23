//Amiran Dugiev 2019-09-08 Labb1_7
//This implementation checks if paranthesis are balanced or not
  
public class Labb1Asgn7 
{ 
    static class stack  
    { 
        int top=-1; 
        char items[] = new char[100]; 
  
        void push(char x)  
        { 
            if (top == 99)  
            { 
                System.out.println("Stack full"); 
            }  
            else 
            { 
                items[++top] = x; 
            } 
        } 
  
        char pop()  
        { 
            if (top == -1)  
            { 
                System.out.println("Underflow error"); 
                return '\0'; 
            }  
            else 
            { 
                char element = items[top]; 
                top--; 
                return element; 
            } 
        } 
        boolean isEmpty()  
        { 
            return (top == -1) ? true : false; 
        } 
    } 
    // if character 1 and character 2 are matching paranthesis, return true
    static boolean isMatchingPair(char character1, char character2) 
    { 
       if (character1 == '(' && character2 == ')') 
         return true; 
       else if (character1 == '{' && character2 == '}') 
         return true; 
       else if (character1 == '[' && character2 == ']') 
         return true; 
       else
         return false; 
    } 
      
    // If paranthesis are balanced, returns true
    static boolean areParenthesisBalanced(char exp[]) 
    { 
       stack st=new stack(); 
       // Loops through available expressions
       for(int i=0;i<exp.length;i++) 
       { 
            
          // if it's a starting paranthesis, push it to the stack
          if (exp[i] == '{' || exp[i] == '(' || exp[i] == '[') 
            st.push(exp[i]); 
       
          // if it's an ending paranthesis pop from stack and check if it matches
          if (exp[i] == '}' || exp[i] == ')' || exp[i] == ']') 
          { 
                   
              // if there's no pair, return false
             if (st.isEmpty()) 
               { 
                   return false; 
               }  
       
             // if it's a mismatch, return false
             else if ( !isMatchingPair(st.pop(), exp[i]) ) 
               { 
                   return false; 
               } 
          } 
            
       } 
          
       // if the expression isn't empty by the end, its not balanced
        
       if (st.isEmpty()) 
         return true; //balanced
       else
         {  
             return false;  //not balanced
         }  
    }  
      
    // main method with unit testing
    public static void main(String[] args)  
    { 
        char exp[] = {'{','(',')','}','[',']'}; 
          if (areParenthesisBalanced(exp)) 
            System.out.println("Balanced "); 
          else
            System.out.println("Not Balanced ");   
    } 
  
}