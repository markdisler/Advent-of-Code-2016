import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day21Part1 {

	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay21.txt"); // get instructions
		String s = "abcdefgh";	// starting string
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i); 
			String [] tokens = line.split(" "); 
			
			if (tokens[0].equals("swap")) {
				if (tokens[1].equals("position")) {
					s = swapPositions(s, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[5]));
				} else if (tokens[1].equals("letter")) {
					s = swapLetters(s, tokens[2].charAt(0), tokens[5].charAt(0));
				}
			} else if (tokens[0].equals("rotate")) {
				if (tokens[1].equals("left")) {
					s = rotate(s, -Integer.parseInt(tokens[2]));
				} else if (tokens[1].equals("right")) {
					s = rotate(s, Integer.parseInt(tokens[2]));
				} else if (tokens[1].equals("based")) {
					s = rotateByPosition(s, tokens[6].charAt(0));
				}
			} else if (tokens[0].equals("reverse")) {
				s = reverseSection(s, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]));
			} else if (tokens[0].equals("move")) {
				s = move(s, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[5]));
			}
		}
		System.out.println(s);
	}
	
	private static String swapPositions(String s, int p1, int p2) {
		if (p1 > p2) {  // make sure p1 < p2
			int temp = p1;
			p1 = p2;
			p2 = temp;
		} 
		return s.substring(0, p1) +  s.charAt(p2) + s.substring(p1 + 1, p2) + s.charAt(p1) + s.substring(p2 + 1);
	}
	
	private static String swapLetters(String s, char c1, char c2) {
		return s.replace(c1, '*').replace(c2, c1).replace('*', c2);  // SWAP using a temporary placeholder
	}
	
	private static String rotate(String s, int steps) {
		String newS = s;
		for (int i = 0; i < Math.abs(steps); i++) {
			if (steps > 0) 
				newS = newS.charAt(newS.length() - 1) + newS.substring(0, newS.length() - 1);
			else 
				newS = newS.substring(1, newS.length()) + newS.charAt(0);
		}
		return newS;
	}
	
	private static String rotateByPosition(String s, char c) {
		String newS = s;
		int index = newS.indexOf(c);
		newS = rotate(newS, 1);  // make one rotation
		for (int i = 0; i < index; i++)  // make rotations for the index
			newS = rotate(newS, 1);
		if (index >= 4)  /// make one extra rotation if index is at least 4
			newS = rotate(newS, 1);
		return newS;
	}
	
	private static String reverseSection(String s, int start, int end) {
		String subsection = s.substring(start, end + 1);
		StringBuilder bldr = new StringBuilder();
		for (int i = subsection.length() - 1; i >= 0; i--) {  // reverse subsection
			bldr.append(subsection.charAt(i));
		}
		return s.substring(0, start) + bldr.toString() + s.substring(end + 1);
	}
		
	private static String move(String s, int from, int to) {   // hello  1, 3
		char removed = s.charAt(from);
		String p1 = s.substring(0, from) + s.substring(from + 1);
		return p1.substring(0, to) + removed + p1.substring(to);
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
