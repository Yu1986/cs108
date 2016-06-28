package assign1;

// CS108 HW1 -- String static methods

public class StringCode {
//ttttt
	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if (str == null || str.length() == 0) return 0;
		char pre = str.charAt(0);
		
		int cR = 1;
	
		int maxRun = 1;
		for (int i= 1; i<str.length(); i++) {
			if (pre == str.charAt(i)) {
				cR++;
			} else {
				cR = 1;
			}
			if (cR > maxRun) {
				maxRun = cR;
			}
			pre = str.charAt(i);
		}
			return maxRun;
	}

	   
	/**
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		return null; // TODO ADD YOUR CODE HERE
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.o
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		return false; // TO DO ADD YOUR CODE HERE
	}
}
