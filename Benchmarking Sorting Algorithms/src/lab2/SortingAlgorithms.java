package lab2;

import java.util.Arrays;

/**
 * Class for Part 01 of Lab 02 for Unit 01. Creates two int arrays, fills them with random
 * whole numbers, sorts each of the arrays using a different method, and prints
 * the runtime after each sort.
 * 
 * @author Thomas Olmsted
 *
 */
public class SortingAlgorithms
{
    /**
     * A constant holding the size for the arrays
     */
    static final int ARRAY_SIZE = 1000;
    
    /**
     * The start of the program. Creates the two arrays and calls the
     * <code>FillArrays</code> and <code>SortArrays</code> methods.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        int A[] = new int[ARRAY_SIZE];
        int B[] = new int[ARRAY_SIZE];
        
        FillArrays(A, B);
        SortArrays(A, B);
        
    }
    
    /**
     * Fills two arrays with the same random whole numbers. The numbers range
     * from greater than 0 to less than <code>Integer.MAX_VALUE</code>
     * 
     * @param a an array to be filled with random whole numbers
     * @param b an array to be filled with random whole numbers
     */
    static void FillArrays(int a[], int b[])
    {
        for (int i = 0; i < ARRAY_SIZE; i++)
        {
            int rnd = (int) (Integer.MAX_VALUE * Math.random());
            a[i] = rnd;
            b[i] = rnd;
        }
    }
    
    
    /**
     * Sorts each array using a different sorting method and prints the
     * approximate run time. The first sorting method is the Selection Sort, the
     * second method uses the <code>Arrays.sort</code> method.
     * 
     * @param a The array that is sorted using the selection sort.
     * @param b The array that is sorted using <code>Arrays.sort</code>.
     */
    static void SortArrays(int a[], int b[])
    {
        System.out.printf("Array Size:\t%,9d%n", ARRAY_SIZE);
        
        /* Selection Sort */
        long startTime = System.currentTimeMillis();
        selectionSort(a);
        long runTime = System.currentTimeMillis() - startTime;
        System.out.printf("Selection Sort:\t%6d ms%n", runTime);
        
        /* Arrays.sort */
        startTime = System.currentTimeMillis();
        Arrays.sort(b);
        runTime = System.currentTimeMillis() - startTime;
        System.out.printf("Arrays.sort:\t%6d ms%n", runTime);
    }
    
    /**
     * Sorts an int array using a selection sort
     * <p>
     * Code from: http://math.hws.edu/eck/cs124/javanotes5/c7/s4.html
     * 
     * @param A an array to be sorted using a selection sort
     */
    static void selectionSort(int[] A)
    {
        // Sort A into increasing order, using selection sort
        
        for (int lastPlace = A.length - 1; lastPlace > 0; lastPlace--)
        {
            // Find the largest item among A[0], A[1], ...,
            // A[lastPlace], and move it into position lastPlace
            // by swapping it with the number that is currently
            // in position lastPlace.
            
            int maxLoc = 0;  // Location of largest item seen so far.
            
            for (int j = 1; j <= lastPlace; j++)
            {
                if (A[j] > A[maxLoc])
                {
                    // Since A[j] is bigger than the maximum we've seen
                    // so far, j is the new location of the maximum value
                    // we've seen so far.
                    maxLoc = j;
                }
            }
            
            int temp = A[maxLoc];  // Swap largest item with A[lastPlace].
            A[maxLoc] = A[lastPlace];
            A[lastPlace] = temp;
            
        }  // end of for loop
        
    }
}
