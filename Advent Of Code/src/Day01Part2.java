import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day01Part2 {
	public static void main(String[] args) throws IOException {
		int [] compassDirections = {0, 1, 2, 3}; 

		ArrayList<String> placesIveBeen = new ArrayList<String>(); //List of all places ive been
		String [] directions = getInput("src/inputDay01.txt");
		int x = 0, y = 0;
		int facing = compassDirections[0];
		
		boolean locationFound = false;

		for (int idx = 0; idx < directions.length && !locationFound; idx++) {
			String direction = directions[idx];
			char turn = direction.charAt(0); 
			int distance = Integer.parseInt(direction.substring(1)); //get number of spaces
			facing = (turn == 'R') ? (compassDirections[(facing + 1) % 4]) : (compassDirections[((facing - 1) % 4 + 4) % 4]);

			for (int i = 0; i < distance && !locationFound; i++) { 
				switch (facing) {
				case 0:
					y++;
					break;
				case 1:
					x++;
					break;
				case 2:
					y--;
					break;
				case 3:
					x--;
					break;
				}
				String loc = x + "," + y;
				boolean beenHere = placesIveBeen.contains(loc);
				if (beenHere) {
					System.out.println((Math.abs(x) + Math.abs(y)) + " blocks"); 
					locationFound = true;
				}
				placesIveBeen.add(loc);
			}
		}

	}

	public static String[] getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		String fullString = "";

		String str = null;
		while((str = br.readLine()) != null){
			fullString += str;
		}

		br.close();
		fullString = fullString.replaceAll(" ", "");

		StringTokenizer st = new StringTokenizer(fullString, ",");
		String [] elements = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreElements()) {
			String token = (String) st.nextElement();
			elements[i++] = token;
		}
		return elements;
	}
}
