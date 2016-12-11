import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day07Part2 {

	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay7.txt"); // Pull data from file
		
		int count = 0;
		for (String ip : lines) {
			ArrayList<String>hypernetSeqs = new ArrayList<String>();
			ArrayList<String>outerSeqs = new ArrayList<String>();
			
			// Separate out all hypernet sequences and all supernet sequences
			int i = ip.indexOf("[" , 0);
			outerSeqs.add(ip.substring(0, i));
			while (i != -1) {
				int closing = ip.indexOf("]", i);
				hypernetSeqs.add(ip.substring(i + 1, closing));
				
				i = ip.indexOf("[", i + 1);
				if (i != -1)
				  outerSeqs.add(ip.substring(closing + 1, i));
				else {
					if (closing + 1 < ip.length()) {
						outerSeqs.add(ip.substring(closing + 1, ip.length()));
					}
				}
			}
			
			// Check if any ABAs exist and make sure there is at least one valid BAB for it
			if (listContainsABAndCorrespondingBAB(outerSeqs, hypernetSeqs)) {
				count++;
			}
		}
		System.out.println(count);
	}
	
	public static boolean listContainsABAndCorrespondingBAB(ArrayList<String> supernetSeq, ArrayList<String> hypernetSeq) {
		for (String s : supernetSeq) {
			ArrayList<String> allABAs = getABAs(s); // Request the ABAs for a supernet sequence
			for (String aba : allABAs) { // check if each ABA has a BAB in the hypernet sequences
				boolean hasBAB = listContainsBAB(hypernetSeq, convertABAtoBAB(aba)); 
				if (hasBAB) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String convertABAtoBAB(String s) {
		return "" + s.charAt(1) + s.charAt(0) + s.charAt(1);  // invert the ABA to a BAB
	}
	
	public static boolean listContainsBAB(ArrayList<String> list, String bab) {
		for (String s : list) { // check list of sequences to see if it contains a given BAB
			if (s.contains(bab)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<String> getABAs(String s) {
		ArrayList<String> abas = new ArrayList<String>();
		if (s.length() < 3) { //ABA is at least 3 chars so ensure that
			return abas;
		}
		
		// Move through the string checking for potential ABAs
		for (int i = 0; i <= s.length() - 3; i++) {
			String possibleABA = s.substring(i, i + 3);
			boolean outerCharsMatch = (possibleABA.charAt(0) == possibleABA.charAt(2));
			boolean differentChars = (possibleABA.charAt(0) != possibleABA.charAt(1));
			if (outerCharsMatch && differentChars) { // Confirm based on ABA rules
				abas.add(possibleABA); //make list of all possible ABAs
			}
		}
		return abas;
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
