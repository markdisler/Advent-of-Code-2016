import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Day4Part2 {
	
	public static void main (String [] args) throws IOException {
		ArrayList<String>rooms = getInput("src/inputDay4.txt"); // Pull data from file
	
		for (String s : rooms) { //go through each room
			
			// Create an unmodified copy of the room name
			String sFull = new String(s.substring(0, s.indexOf("["))); 
			
			// Validation Check
			while (count('-', s) != 1) { 
				s = s.replaceFirst("-", "");
			}
			int index1 = s.indexOf('-');
			int indexOfBracket = s.indexOf('[');
			int sectorId = Integer.parseInt(s.substring(index1 + 1, indexOfBracket));
			String checksum = s.substring(indexOfBracket+1, s.length() - 1);
			
			if (isValid(checksum, s.substring(0, index1))) {
				String decrypted = decrypt(sFull, sectorId);  //decrypt name
				if (decrypted.contains("pole")) {  //determine if "pole" is in the name
					System.out.println(sectorId);
				}
			}
		}

	}
	
	public static String decrypt(String r, int sectorId) {
		String decrypted = "";  // decrypted text
		for (char c : r.toCharArray()) {  // go through all characters in encrypted name
			if (c == '-')  // if dash, make it a space
				decrypted += " ";
			else {
				char newChar = c;  //start at old char
				for (int i = 0; i < sectorId; i++) {  //shift by sector id with wrapping
					if (newChar == 'z')
						newChar = 'a';
					else
						newChar++;
				}
				decrypted += newChar;  //append shifted character
			}
		}
		return decrypted; // return decrypted name
	}
	
	public static boolean isValid(String checksum, String name) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (char c : name.toCharArray()) {
			int count = count(c, name);
			map.put(""+c, count);
		}

		ArrayList<OccurancePair>list = new ArrayList<OccurancePair>();
		for (Entry<String, Integer> entry : map.entrySet()) {
			list.add(new OccurancePair(entry.getKey().charAt(0), entry.getValue()));
		}

		Collections.sort(list, new Comparator<OccurancePair>() {
			public int compare(OccurancePair o1, OccurancePair o2) {
				int comp = Integer.valueOf(o2.count).compareTo(o1.count);
				if (comp == 0) {
					return (o2.c > o1.c) ? -1 : 1;
				} else {
					return comp;
				}
			}
		});
		
		String validCS = "";
		for (int i = 0; i < 5; i++) {
			validCS += list.get(i).c;
		}
		return validCS.equals(checksum);
	}

	public static int count(char c, String inStr) {
		int count = 0;
		for (char l : inStr.toCharArray()) {
			if (l == c) {
				count++;
			}
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
