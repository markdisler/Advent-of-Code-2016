
public class Day14Part1 {

	public static void main (String [] args) {
		String input = "qzyelonm";
		
		int keyCount = 0;  // COUNT the number of keys
		int i = 0;  // index
		while (keyCount < 64) { // SEARCH while we haven't located the 64th key
			String hashed = MD5(input + i);  // HASH the first value
			char c = threeInARow(hashed);  // GET the character that is 3x in a row (if none, returns ' ')
			while (c == ' ') { // WHILE there is no character repeated 3x...
				i++;  // INCREMENT i
				hashed = MD5(input + i);  // HASH new value
				c = threeInARow(hashed);  // GET the character that is 3x in a row (potentially none)
			}

			boolean isKey = false;  // CHECK for valid key
			for (int a = i + 1; a < i + 1000; a++) {  // CHECK the next 1000 values
				hashed = MD5(input + a);
				isKey = (fiveInARow(hashed) == c);  // CHECK if it is a key (the character 5x in a row must equal the character 3x in a row from earlier)
				if (isKey) {  // Valid key?
					keyCount++;  // INCREMENT counter
					break;
				}
			}
			i++;  // INCREMENT i to move on past the index we just looked into
		}
		System.out.println(i - 1);  // SUBTRACT one from the index to cancel the "i++" at the end of the loop [final answer]
	}

	public static char fiveInARow(String s)	{  
		for (int i = 0; i < s.length() - 4; i++) {
			String subset = s.substring(i, i + 5);
			char [] portion = subset.toCharArray();
			if (portion[0] == portion[1] && portion[0] == portion[2] && portion[0] == portion[3] && portion[0] == portion[4])
				return portion[0];
		}
		return ' ';
	}

	public static char threeInARow(String s) {  
		for (int i = 0; i < s.length() - 2; i++) {
			String subset = s.substring(i, i + 3);
			char [] portion = subset.toCharArray();
			if (portion[0] == portion[1] && portion[0] == portion[2])
				return portion[0];
		}
		return ' ';
	}
	
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {	}
		return null;
	}

}
