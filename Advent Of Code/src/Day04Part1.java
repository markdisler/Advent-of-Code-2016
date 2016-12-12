import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Day04Part1 {
	public static void main (String [] args) throws IOException {
		ArrayList<String>rooms = getInput("src/inputDay04.txt"); // Pull data from file
		int sum = 0; //sum
		for (String s : rooms) { //go through each room
			while (count('-', s) != 1) {  //remove the dashes
				s = s.replaceFirst("-", "");
			}
			int index1 = s.indexOf('-'); //split of name & sector id
			int indexOfBracket = s.indexOf('['); //start of checksum
			
			int sectorId = Integer.parseInt(s.substring(index1 + 1, indexOfBracket)); //actual sector id
			String checksum = s.substring(indexOfBracket+1, s.length() - 1); //actual checksum
			
			if (isValid(checksum, s.substring(0, index1))) { //check if valid
				sum += sectorId; //add sector id to total
			}
		}
		System.out.println(sum);
	}

	public static boolean isValid(String checksum, String name) {
		// Build map of all chars and their occurrences 
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (char c : name.toCharArray()) { // go through each character in name
			int count = count(c, name);  //count up occurrences
			map.put(""+c, count); //add to dict
		}

		// Convert to a list
		ArrayList<OccurancePair>list = new ArrayList<OccurancePair>();
		for (Entry<String, Integer> entry : map.entrySet()) {
			list.add(new OccurancePair(entry.getKey().charAt(0), entry.getValue()));
		}

		//Sort list by occurrence and by letter
		Collections.sort(list, new Comparator<OccurancePair>() {
			public int compare(OccurancePair o1, OccurancePair o2) {
				int comp = Integer.valueOf(o2.count).compareTo(o1.count);  //largest occurrence
				if (comp == 0) 	
					return (o2.c > o1.c) ? -1 : 1;  // continue with alphabetical order
				else 
					return comp; // base on occurrence if there was a definitive comparison
			}
		});
		
		// Construct appropriate checksum (CS)
		String validCS = "";
		for (int i = 0; i < 5; i++) {
			validCS += list.get(i).c;
		}
		return validCS.equals(checksum);
	}

	public static int count(char c, String inStr) {
		int count = 0;
		for (char l : inStr.toCharArray()) {
			if (l == c) 
				count++;
		}
		return count;
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

	public static class OccurancePair {
		public char c;
		public int count;
		public OccurancePair(char c, int count) {
			this.c = c;
			this.count = count;
		}
	}
}
