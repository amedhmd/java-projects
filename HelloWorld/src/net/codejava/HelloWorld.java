package net.codejava;

public class HelloWorld {

	public static void main(String[] args) {
		String x = stringFunction("mississippi");
		static String stringFunction(String str) {
		        String test;
		        int i;
		        test = "";
		        for (i = str.length() - 1; i >= 0; i--) {
		            test = test + str.charAt(i);
		        }
		        return (test);
		    }
        }

}
