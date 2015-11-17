package com.forum.java.algos.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Consider a two-dimensional grid of cells, each of which may be empty or
 * filled. The filled cells that are connected form a sector. Two cells are said
 * to be connected if they are adjacent to each other horizontally, vertically
 * or diagonally. There may be several sectors on the grid. 
 * 
 * This program will find
 * the largest sector (in terms of number of cells) on the grid.
 * 
 * @author ntallapa
 * 
 */
public class SectorsOfMatrix {
	private static final Logger logger = Logger
			.getLogger(SectorsOfMatrix.class.getName());

	private int currentDepth;
	private static int ctr = -1;
	private int noOfArrays;
	private static int[][] intArr;

	private class Matrix {
		private int currentArrWidth;
		private int currentArrHeight;
		private int[][] matrix;

		public Matrix(int currentArrWidth, int currentArrHeight, int[][] matrix) {
			this.currentArrHeight = currentArrHeight;
			this.currentArrWidth = currentArrWidth;
			this.matrix = matrix;
		}

		public int getCurrentArrWidth() {
			return currentArrWidth;
		}

		public int getCurrentArrHeight() {
			return currentArrHeight;
		}

		public int[][] getMatrix() {
			return matrix;
		}
	}

	/**
	 * Core logic for finding out the number of connected cells recursively
	 * 
	 * @param n
	 *            2-D Array
	 * @param bool
	 *            boolean array for back tracking
	 * @param i
	 * @param j
	 * @return
	 */
	public int findMaxDepth(int[][] n, boolean[][] bool, int i, int j) {
		// Check if i, j (indexes) are within the size of array
		// Check if the cell is already traversed or not using bool array
		// Check if the cell value is 1 before counting it as part of a sector
		if (i >= 0 && i < n.length && j >= 0 && j < n[0].length
				&& bool[i][j] != true && n[i][j] != 0) {
			currentDepth++;

			// Mark the status of the cell for backtracking purpose
			bool[i][j] = true;

			// left traversal
			findMaxDepth(n, bool, i - 1, j);
			// right traversal
			findMaxDepth(n, bool, i + 1, j);

			// top traversal
			findMaxDepth(n, bool, i, j - 1);
			// bottom traversal
			findMaxDepth(n, bool, i, j + 1);

			// Top-Bottom diagnol
			// diagnol-down traversal
			findMaxDepth(n, bool, i + 1, j + 1);
			// diagnol-up traversal
			findMaxDepth(n, bool, i - 1, j - 1);

			// Bottom-Top diagnol
			// diagnol-down traversal
			findMaxDepth(n, bool, i + 1, j - 1);
			// diagnol-up traversal
			findMaxDepth(n, bool, i - 1, j + 1);
		}
		return currentDepth;
	}

	/**
	 * Read the input file, recognize the new array input through new line
	 * characters and feed each line of the array into list and pass this list
	 * to construct an array
	 * 
	 */
	public List<Matrix> readFile(String fileLocation) {
		BufferedReader br = null;
		List<Matrix> matrixList = new ArrayList<Matrix>();
		int currentArrWidth = 0;
		int currentArrHeight = 0;
		
		try {
			br = new BufferedReader(new FileReader(new File(fileLocation)));
			List<String> matrixAsStrList = new ArrayList<String>();

			// Read the very first line and put it array count
			noOfArrays = Integer.parseInt(br.readLine());
			logger.info("Number of arrays given: " + noOfArrays);
			ctr++;
			// Read File Line By Line
			String strLine;
			while ((strLine = br.readLine()) != null && ctr <= noOfArrays) {

				if (strLine.length() == 0) {
					if (matrixAsStrList.size() != 0) {
						currentArrHeight = matrixAsStrList.size();
						// process the string array
						if (currentArrHeight <= 25 && currentArrWidth <= 25) {
							intArr = new int[currentArrHeight][currentArrWidth];

							int rowIdx = 0;
							for (String matrixRow : matrixAsStrList) {
								for (int colIdx = 0; colIdx < matrixRow
										.length(); colIdx++) {
									String s = (new Character(
											matrixRow.charAt(colIdx)))
											.toString();
									intArr[rowIdx][colIdx] = Integer
											.parseInt(s);
								}
								rowIdx++;
							}

							Matrix m = new Matrix(currentArrWidth,
									currentArrHeight, intArr);
							matrixList.add(m);
						} else {
							intArr = null;
							logger.warning("Matrix of dimension greater than 25 is not supported.");
						}

					}

					// over-ride the previous data
					matrixAsStrList = new ArrayList<String>();
					currentArrWidth = 0;
					currentArrHeight = 0;
					ctr++;
				} else {
					if (currentArrWidth == 0) {
						currentArrWidth = strLine.length();
					}
					matrixAsStrList.add(strLine);
				}
			}
			br.close();

			// At the completion of file reading if no of arrays are not
			// equal to the number given in the first line of file then
			// throw exception
			if (ctr < noOfArrays) {
				logger.warning("Number of actual arrays are less than the number given.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matrixList;
	}

	// Traverse through the above constructed array to get the maximum
	// sector connected cells
	public List<String> traverseArray(List<Matrix> matrixList) {
		List<String> outList = new ArrayList<String>();
		for (Matrix m : matrixList) {
			intArr = m.getMatrix();
			int currentArrHeight = m.getCurrentArrHeight();
			int currentArrWidth = m.getCurrentArrWidth();
			boolean[][] visitedBoolArr = new boolean[currentArrHeight][currentArrWidth];

			int depth, maxDepth = 0;
			if (intArr != null) {
				for (int i = 0; i < currentArrHeight; i++) {
					for (int j = 0; j < currentArrWidth; j++) {
						if (intArr[i][j] == 1 && visitedBoolArr[i][j] == false) {
							currentDepth = 0;
							depth = findMaxDepth(intArr, visitedBoolArr, i, j);
							if (depth > maxDepth) {
								maxDepth = depth;
							}
						}
					}
				}
				outList.add(maxDepth + "");
			} else {
				outList.add("Array Dimension Not Supported");
			}

			logger.info("Maximum depth of array" + ctr + " is: " + maxDepth);
		}
		return outList;
	}

	/**
	 * Write the output to the file as format mentioned in the requirement doc
	 */
	public void writeOutput(List<String> outList) {
		Writer output = null;
		File file = new File("output.txt");
		try {
			output = new BufferedWriter(new FileWriter(file));
			for (Iterator<String> iterator = outList.iterator(); iterator
					.hasNext();) {
				String string = iterator.next();
				output.write(string + "\n");
			}

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("create output file " + file.getAbsolutePath());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SectorsOfMatrix matrixSearch = new SectorsOfMatrix();
		String fileLocation = "C:/AMEX/ML Workspace/java_algos/src/main/resources/input.txt";

		if (args == null || args.length != 1) {
			// default
			fileLocation = "C:/AMEX/ML Workspace/java_algos/src/main/resources/input.txt";
		} else {
			fileLocation = args[0];
			// validate
			try {
				File f = new File(args[0]);
				if (!f.exists()) {
					// default
					fileLocation = "C:/AMEX/ML Workspace/java_algos/src/main/resources/input.txt";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Read through the input file and construct integer and boolean arrays
		// Traverse through the constructed arrays and find maximum depth of
		// connected cells
		List<Matrix> matrixList = matrixSearch.readFile(fileLocation);
		List<String> outList = matrixSearch.traverseArray(matrixList);

		// For every matrix print the maximum connected cell count into
		// output.txt file
		matrixSearch.writeOutput(outList);
	}
}