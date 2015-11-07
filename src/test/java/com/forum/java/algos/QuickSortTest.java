/**
 * 
 */
package com.forum.java.algos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ntallapa
 *
 */
public class QuickSortTest {
	QuickSort qs = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		qs = new QuickSort();
	}

	@Test
	public void testArray1() {
		int[] arr = {14,12,16,13,11,15};
		int[] result = qs.recursiveQuickSort(arr, 0, 5);
		
		int[] expectedArr = {11,12,13,14,15,16};
		Assert.assertArrayEquals(result, expectedArr);
	}
	
	@Test
	public void testArray2() {
		int[] arr = {9,2,4,7,3,7,10};
		int[] result = qs.recursiveQuickSort(arr, 0, 6);
		
		int[] expectedArr = {2,3,4,7,7,9,10};
		Assert.assertArrayEquals(result, expectedArr);
	}

	@Test
	public void testArray3() {
		int[] arr = {24,2,45,20,56,75,2,56,99,53,12};
		int[] result = qs.recursiveQuickSort(arr, 0, 10);
		
		int[] expectedArr = {2,2,12,20,24,45,53,56,56,75,99};
		Assert.assertArrayEquals(result, expectedArr);
	}
}
