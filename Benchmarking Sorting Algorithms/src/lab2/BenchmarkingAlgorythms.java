package lab2;

import java.util.Arrays;
import java.util.Scanner;

public class BenchmarkingAlgorythms {
	private static Scanner scan;
	public static void selectionSort(int arr[]) {
		int n = arr.length;
		// One by one move boundary of the unsorted subarray

		for (int i = 0; i < n-1; i++)
{
			// Find the minimum element in the unsorted array

		int min_idx = i;

		for (int j = i+1; j < n; j++)

		if (arr[j] < arr[min_idx])

		min_idx = j;
		// Swap the found minimum element with the first
		int temp = arr[min_idx];

		arr[min_idx] = arr[i];

		arr[i] = temp;

 }

}

		public static void printArrayBySelectionSort(int[] arr) {

		for(int i=0;i<arr.length;i++) {

		System.out.print(arr[i]+" ");

}
 }

	 public static void sortByInBuilt(int arr[]) {

		Arrays.sort(arr);
	}

		public static void main(String[] args) {

		scan = new Scanner(System.in);

		System.out.println("Enter the limit of the array:");

		int limit = scan.nextInt();

		int[] arr = new int[limit];

		for(int i = 0;i<arr.length;i++) {

		arr[i] = (int) (Math.random() * 100);

		}

		long startTime = System.currentTimeMillis();

		selectionSort(arr);

		long runTime = System.currentTimeMillis() - startTime;


		System.out.println("\nRun time of the Selection sort is:"+runTime/1000.0+" Seconds");


		long startTime1 = System.currentTimeMillis();


		sortByInBuilt(arr);


		long runTime1 = System.currentTimeMillis()-startTime1;


		System.out.println("\nRun time of the In Built Sort method: "+runTime1/1000.0+" Seconds");


		printArrayBySelectionSort(arr);


 }

}