import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Day06Part1 {

	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay06.txt"); // Pull data from file
		
		for (int i = 0; i < 8; i++) {
			String col = "";
			for (String s : lines) { 
				col += s.charAt(i);
			}
			System.out.print(mostFrequentCharacterInString(col));
		}
	}
	
	public static String mostFrequentCharacterInString(String s) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (char c : s.toCharArray()) {
			int count = count(c, s);
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
		
		return "" + list.get(0).c;
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
