public class firstsubroutines
{
    public static void main(String a[]) throws Exception
	{
		System.out.println("Welcome to the firstsubroutines project!");	
		System.out.println("This program will test a phrase to see if it is a palindrome.");
		System.out.println("What phrase would you like to test?");
		//collecting the user's input
        String input=TextIO.getln();                       
      //ask the subroutine to remove spaces
        String nospace=removePunc(input);    
        //ask the subroutine to reverse the string
        String backwards = reverseString(nospace);        
      //This is an output line to show if the subroutines are working
        System.out.println(input + "-->" + nospace+ "-->" + backwards);  
     // This output will indicate if the phrase entered is a palindrome.
        if (nospace.equalsIgnoreCase(backwards))                        
            System.out.println(input+ " is a palindrome!");
            else
            System.out.println(input+ " is not a palindrome.");
	}
 // This is the subroutine that will reverse a String
	public static String reverseString(String str){                     
	    String reverse;
        int i;
        
        reverse = "";

        for (i = str.length() - 1; i >= 0; i--) {

        reverse = reverse + str.charAt(i);
    	}
    	return reverse;
	}
	// This is the subroutine that will remove the spaces from the phrase.
    public static String removePunc(String str){           
        String lettersonly;
        int i;
        lettersonly = "";
        str=str.toLowerCase();
        
        for (i=0; i<=str.length()-1; i++) {
            char x = str.charAt(i);
           if(x >= 'a' && x <= 'z'){
               lettersonly = lettersonly + x;
            }else {
            continue;
           }
          }
          return lettersonly;
        }
        
    
}

