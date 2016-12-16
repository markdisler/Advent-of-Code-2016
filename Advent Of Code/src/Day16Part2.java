
public class Day16Part2 {

	public static void main(String[] args) {		
		String input = "10011111011011001";
		int lengthNeeded = 35651584;
		
		// GENERATE fake data
		String fakeData = generateFakeData(input);
		while (fakeData.length() < lengthNeeded) {
			fakeData = generateFakeData(fakeData);
		}
		fakeData = fakeData.substring(0, lengthNeeded);

		// OUTPUT checksum for fake data
		String checksum = generateChecksum(fakeData);
		System.out.println(checksum);
	}
	
	public static String generateChecksum(String s) {
		StringBuilder checksumBuilder = new StringBuilder();  // CREATE string to store checksum
		for (int i = 0; i < s.length(); i+=2) { 
			checksumBuilder.append((s.charAt(i) == s.charAt(i+1)) ? "1" : "0");  // APPEND a 1 if the pair has the same # or a 0 if they differ
		}
		
		if (checksumBuilder.length() % 2 == 0) { // IF the checksum length is even...
			checksumBuilder = new StringBuilder(generateChecksum(checksumBuilder.toString())); // GENERATE a checksum on the checksum
		}		
		return checksumBuilder.toString();
	}
	
	public static String generateFakeData(String a) {
		StringBuilder strBldr = new StringBuilder(); // CREATE string to store b-section
		for (char c : a.toCharArray()) {  // ITERATE through each character in a-section
			strBldr.append((c == '1') ? '0' : '1'); // swap 1's for 0's and vice-versa
		}
		StringBuilder reversed = new StringBuilder();
		for (int i = strBldr.length() - 1; i >= 0; i--) {  // REVERSE the b section
			reversed.append(strBldr.charAt(i));
		}
		return a + "0" + reversed.toString();  // BUILD fake data string
	}

}
