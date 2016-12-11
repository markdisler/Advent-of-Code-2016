import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day09Part2 {

	public static void main (String [] args) throws IOException {
		String line = getInput("src/inputDay9.txt"); // Pull data from file
		System.out.println(decompressLength(line));
	}

	public static long decompressLength(String line) {
		long len = 0;

		while (line.length() != 0) {
			int indexOfOpenParen = line.indexOf("(");
			if (indexOfOpenParen != - 1){
				String sPart = line.substring(0, indexOfOpenParen);
				len += sPart.length();
				line = line.substring(sPart.length());
				indexOfOpenParen -= sPart.length();
				int indexOfClosing = line.indexOf(")");
				String marker = line.substring(indexOfOpenParen + 1, indexOfClosing);

				int numCharacters = Integer.parseInt(marker.substring(0, marker.indexOf("x")));
				int times = Integer.parseInt(marker.substring(marker.indexOf("x") + 1));
				String section = line.substring(indexOfClosing + 1, indexOfClosing + numCharacters + 1);
				
				long repeatedLength;
				if (section.contains("(")) {
					repeatedLength = times * (long)decompressLength (section);
				} else {
					repeatedLength = times * numCharacters;
				}
				len += repeatedLength;
				line = line.substring(numCharacters + marker.length() + 2);
			} else {
				len += 1;
				line = line.substring(1);
			}
		}
		return len;
	}
	
	public static String getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		String s = "";
		String str = null;
		while((str = br.readLine()) != null){
			s += str;
		}
		br.close();
		return s;
	}
}