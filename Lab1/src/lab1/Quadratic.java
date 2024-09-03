package lab1;

import lab1.TextIO;

public class Quadratic {
	/**
	 * Returns the larger of the two roots of the quadratic equation
	 * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
	 * if the discriminant, B*B - 4*A*C, is negative, then an exception
	 * of type IllegalArgumentException is thrown.
	 */
	static public double root( double A, double B, double C ) 
	                              throws IllegalArgumentException {
	    if (A == 0) {
	      throw new IllegalArgumentException("A can't be zero.");
	    }
	    else {
	       double disc = B*B - 4*A*C;
	       if (disc < 0)
	          throw new IllegalArgumentException("Discriminant < zero.");
	       return  (-B + Math.sqrt(disc)) / (2*A);
	    }
	}
	
	public static void main(String[] args) {
		System.out.println("This program will ask you to enter three double "
				+ "values for the A, B, and C coefficients of a quadratic "
				+ "equation, and it will print the root.");
			System.out.println("It will allow you to continue entering "
				+ "equations until you do not want to continue.");
			System.out.println();
			
			double A;  // Leading coefficient
			double B;  // Linear coefficient
			double C;  // Constant term
			boolean wantToContinue = true;
			
			while (wantToContinue) {
				System.out.print("Enter value for A: ");
				A = TextIO.getDouble();
				System.out.println("Enter value for B: ");
				B = TextIO.getDouble();
				System.out.println("Enter value for C: ");
				C = TextIO.getDouble();
				
				double largeRoot;  // Result of root() subroutine
				try {
					largeRoot = root(A, B, C);
					System.out.println();
					System.out.println("The root is " + largeRoot + ".");
				} catch (IllegalArgumentException e) {
					System.out.println();
					System.out.println("Error: " + e.getMessage());
				}
				
				System.out.println();
				System.out.println("Would you like to continue? Y/N");
				wantToContinue = TextIO.getBoolean();
			} // end while
		}
	}