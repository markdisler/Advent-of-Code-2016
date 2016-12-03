import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Day1Part1 {

	public static void main(String[] args) throws IOException {
		int [] compassDirections = {0, 1, 2, 3}; 

		String [] directions = getInput("src/inputDay1.txt");
		int x = 0, y = 0;
		int facing = compassDirections[0];

		for (int idx = 0; idx < directions.length; idx++) {
			String direction = directions[idx];
			char turn = direction.charAt(0); //get turn direction
			int distance = Integer.parseInt(direction.substring(1)); //get number of spaces
			facing = (turn == 'R') ? (compassDirections[(facing + 1) % 4]) : (compassDirections[((facing - 1) % 4 + 4) % 4]);

			switch (facing) {
			case 0:
				y += distance;
				break;
			case 1:
				x += distance;
				break;
			case 2:
				y -= distance;
				break;
			case 3:
				x -= distance;
				break;
			}
		}
		System.out.println((Math.abs(x) + Math.abs(y)) + " blocks"); 
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
