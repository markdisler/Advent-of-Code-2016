import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day7Part1 {

	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay7.txt"); // Pull data from file

		int count = 0;  // keep count
		for (String ip : lines) {
			// Keep all hypernet sequences and sequences outside of brackets
			ArrayList<String>hypernetSeqs = new ArrayList<String>();
			ArrayList<String>outerSeqs = new ArrayList<String>();

			int i = ip.indexOf("[" , 0); //find first open bracket
			outerSeqs.add(ip.substring(0, i)); //record first outer bracket sequence

			while (i != -1) { // while we haven't located all the bracket sections
				int closing = ip.indexOf("]", i); //find the closing bracket
				hypernetSeqs.add(ip.substring(i + 1, closing)); //add the hypernet

				i = ip.indexOf("[", i + 1); //move to next bracket
				if (i != -1) {  
					outerSeqs.add(ip.substring(closing + 1, i)); //add this outer sequence
				} else {
					if (closing + 1 < ip.length()) { // check if there is more after the last bracket
						outerSeqs.add(ip.substring(closing + 1, ip.length())); //outer sequence ends str length
					}
				}
			}

			// check if the outer sequences have an ABBA and the inner ones don't
			if (listContainsABBA(outerSeqs) && !listContainsABBA(hypernetSeqs)) {
				count++;
			}
		}
		System.out.println(count);
	}

	public static boolean listContainsABBA(ArrayList<String> list) {
		for (String s : list) {
			if (stringContainsABBA(s))
				return true;
		}
		return false;
	}

	public static boolean stringContainsABBA(String s) {
		if (s.length() < 4) { // ABBA is at least 4 chars so ensure that
			return false;
		}
		
		// Move through the string checking for a possible ABBA
		for (int i = 0; i <= s.length() - 4; i++) {
			String possibleABBA = s.substring(i, i + 4);
			boolean outerCharsMatch = (possibleABBA.charAt(0) == possibleABBA.charAt(3));
			boolean innerCharsMatch = (possibleABBA.charAt(1) == possibleABBA.charAt(2));
			boolean differentChars = (possibleABBA.charAt(0) != possibleABBA.charAt(1));
			if (outerCharsMatch && innerCharsMatch && differentChars) { // Confirm based on ABBA rules
				return true;
			}
		}
		return false;
	} 

	public static ArrayList<String> getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		ArrayList<String> list = new ArrayList<String>();
		String str = null;
		while((str = br.readLine()) != null){
			list.add(str);
		}
		br.close();
		return list;
	}

}
