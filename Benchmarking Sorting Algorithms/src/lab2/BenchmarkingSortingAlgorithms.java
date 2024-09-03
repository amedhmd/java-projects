package lab2;
import java.util.*;

public class  BenchmarkingSortingAlgorithms{
	// Compute banchmarks of two different sorting techniques
	
	int maxArraySize=10000; // Array Size 
	int[] sortingArray1 = new int[maxArraySize]; // First Array 
	int[] sortingArray2 = new int[maxArraySize]; // Second Array
	
	public BenchmarkingSortingAlgorithms(){ 
		// Class Constructor
	
	for (int i = 0; i < sortingArray1.length; i++) { 
	// Filling two arrays with the same random numbers. 
		
		sortingArray1[i]=(int)(Integer.MAX_VALUE * Math.random()); 
		sortingArray2[i]=sortingArray1[i]; }
	
	long startTimeArray1 = System.currentTimeMillis(); // Start computing time for SelectionSort 
	
	selectionSort(sortingArray1); // Sorting Array1 with SelectionSort 
	
	long runTimeArray1 = System.currentTimeMillis() - startTimeArray1; // Time to run the SelectionSort
	
	
	long startTimeArray2 = System.currentTimeMillis();// Start computing time for Arrays.sort 
	
	Arrays.sort(sortingArray2); // Sorting Array2 with Arrays.sort 
	
	long runTimeArray2 = System.currentTimeMillis() - startTimeArray2; // Time to run the Arrays.sort
	
	System.out.println("SelectionSort time(sec):"+runTimeArray1/1000.0); // Print results 
	
	System.out.println("Arrays Sort time(sec):"+runTimeArray2/1000.0); // Print results 
	}

	static void selectionSort(int[] A) {
	// Sort A in increasing order, using selection 
	for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) { 
		// Find the largest item among A[0], A[1], ..., 
		// A[lastPlace], and move it into position lastPlace 
		// by swapping it with the number that is currently 
		// in position lastPlace. 
		int maxLoc = 0; 
		// Location of largest item seen so far. 
		for (int j = 1; j <= lastPlace; j++) { 
		if (A[j] > A[maxLoc]) { 
			// Since A[j] is bigger than the maximum we have seen 
			// so far, j is the new location of the maximum value 
			// we have seen so far. 
			maxLoc = j; 
			} 
		} 
		int temp = A[maxLoc]; // Swap largest item with 
		A[maxLoc] = A[lastPlace]; 
		A[lastPlace] = temp; } 
	// end of for loop
	
		}
	

	public static void main(String[] args) {
		BenchmarkingSortingAlgorithms SortingTest = new BenchmarkingSortingAlgorithms(); 
		// Create new class of type BenchmarkingSortingAlgorithms
	

	}

}
