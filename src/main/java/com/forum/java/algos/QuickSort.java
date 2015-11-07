/**
 * 
 */
package com.forum.java.algos;

import java.util.Arrays;


/**
 * @author ntallapa
 *
 */
public class QuickSort {

	/**
	 * Partition logic
	 * 
	 * @param a array to be modified based on pivot
	 * @param left lower bound of the array
	 * @param right upper bound of the array
	 * @return the partition index where elements to its left are less than it and 
	 * elements to its right are more than it 
	 */
	public int partition(int[] a, int left, int right) {
		// Get the pivot element
		int pivot = a[left];

		// Break when left is > right
		while(left <= right) {
			//increment the lower bound till you find the element more than the pivot
			while(a[left]<pivot)
				left++;
			//decrement the upper bound till you find the element less than the pivot
			while(a[right]>pivot)
				right--;

			// swap the values which are left by lower and upper bounds 
			if(left <= right) {
				int tmp = a[left];
				a[left] = a[right];
				a[right] = tmp;

				//increment left index and decrement right index
				left++;
				right--;
			}
		}   
		System.out.println("End of partition "+Arrays.toString(a));
		return left;
	}

	/**
	 * Recursive quicksort logic
	 * @param a input array
	 * @param i start index of the array
	 * @param j end index of the array
	 */
	public int[] recursiveQuickSort(int[] a, int i, int j) {
		// Handle logic to divide the array
		int idx = partition(a, i, j);

		// Recursively call quick sort with left part of the divided array
		if(i<idx-1) {
			System.out.println("left recursion with i: "+i+"; j: "+(idx-1));
			recursiveQuickSort(a, i, idx-1);
		}

		// Recursively call quick sort with right part of the divided array
		if(j>idx) {
			System.out.println("right recursion with i: "+idx+"; j: "+j);
			recursiveQuickSort(a, idx, j);
		}

		return a;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
		int[] arr = {14,12,16,13,11,15};

		int[] result = qs.recursiveQuickSort(arr, 0, 5);
		System.out.println("Final sorted array: "+Arrays.toString(result));
	}
}
