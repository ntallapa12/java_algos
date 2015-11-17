package com.forum.java.algos;
 
public class KMPAlgo {
 
    /**
     * Pre processes the pattern array based on proper prefixes and proper
     * suffixes at every position of the array
     * 
     * @param ptrn
     *            word that is to be searched in the search string
     * @return partial match table which indicates
     */
    public int[] preProcessPattern(char[] ptrn) {
        int i = 0, j = -1;
        int ptrnLen = ptrn.length;
        int[] b = new int[ptrnLen + 1];
 
        b[i] = j;
        while (i < ptrnLen) {
            while (j >= 0 && ptrn[i] != ptrn[j]) {
                // if there is mismatch consider next widest border
                j = b[j];
            }
            i++;
            j++;
            b[i] = j;
        }
        // print pettern, partial match table and index
        System.out
                .println("printing pattern, partial match table, and its index");
        System.out.print(" ");
        for (char c : ptrn) {
            System.out.print(c + "   ");
        }
        System.out.println(" ");
        for (int tmp : b) {
            System.out.print(tmp + "   ");
        }
        System.out.print("\n ");
        for (int l = 0; l < ptrn.length; l++) {
            System.out.print(l + "   ");
        }
        System.out.println();
        return b;
    }
 
    /**
     * Based on the pre processed array, search for the pattern in the text
     * 
     * @param text
     *            text over which search happens
     * @param ptrn
     *            pattern that is to be searched
     */
    public void searchSubString(char[] text, char[] ptrn) {
        int i = 0, j = 0;
        // pattern and text lengths
        int ptrnLen = ptrn.length;
        int txtLen = text.length;
 
        // initialize new array and preprocess the pattern
        int[] b = preProcessPattern(ptrn);
 
        while (i < txtLen) {
            while (j >= 0 && text[i] != ptrn[j]) {
                System.out.println("Mismatch happened, between text char "
                        + text[i] + " and pattern char " + ptrn[j]
                        + ", \nhence jumping the value of " + "j from " + j
                        + " to " + b[j] + " at text index i at " + i
                        + " based on partial match table");
                j = b[j];
            }
            i++;
            j++;
 
            // a match is found
            if (j == ptrnLen) {
                System.out.println("FOUND SUBSTRING AT i " + i + " and index:"
                        + (i - ptrnLen));
                System.out.println("Setting j from " + j + " to " + b[j]);
                j = b[j];
            }
        }
    }
 
    // only for test purposes
    public static void main(String[] args) {
        KMPAlgo stm = new KMPAlgo();
        // pattern
        char[] ptrn = "abcabdabc".toCharArray();
 
        char[] text = "abcabdabcabeabcabdabcabd".toCharArray();
        System.out.print(" ");
        for (char c : text) {
            System.out.print(c + "   ");
        }
        System.out.println();
        // search for pattern in the string
        stm.searchSubString(text, ptrn);
    }
}