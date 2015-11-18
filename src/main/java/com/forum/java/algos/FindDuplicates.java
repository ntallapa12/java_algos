/**
 * 
 */
package com.forum.java.algos;

/**
 * In this class we attempt to find duplicates in an array 
 * at time complexity of O(n) and space complexity of O(n)
 * 
 * @author ntallapa
 *
 */
public class FindDuplicates {

	int size;
	MyInt[] hashArray;

	public FindDuplicates(int size) {
		this.size = size;
		hashArray = new MyInt[size];
	}
	
	class MyInt {
		int value;
		MyInt next;
		
		public MyInt(int value) {
			this.value = value;
		}
	}
	
	private int getSupplementalHash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	public void findDuplicates(Integer currentElement) {
		int idx = getSupplementalHash(currentElement.hashCode()) % size;
		//int idx = (currentElement.hashCode()) % size;
		MyInt existingElement = hashArray[idx];

		for(;existingElement != null; existingElement = existingElement.next) {
			if(existingElement.value == currentElement) {
				// duplicate
				System.out.println(currentElement+" is a duplicate");
				return;
			} else {
				System.out.println("identified collision, adding "+existingElement.value+" to the list");
			}
		}
		
		System.out.println("adding "+currentElement+" to the list");
		MyInt mi = new MyInt(currentElement);
		// insert element at the head to avoid tail traversing
		mi.next = hashArray[idx];
		hashArray[idx] = mi;
		
		System.out.println("------------------------------------");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//int[] testArr = {10,0,100,20,200,30,300,40,400,50, 10, 20, 30, 40, 50};
		//int[] testArr = {1, 2, 3, 3, 4, 5, 6, 8, 8, 1, 2,8};
		int[] testArr = {10,0,100,20,20};
		System.out.println("Input array size is "+testArr.length);
		FindDuplicates fd = new FindDuplicates(testArr.length);
		
		for (int i = 0; i < testArr.length; i++) {
			fd.findDuplicates(testArr[i]);
		}
	}
}