import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day08Part1 {

	public static void main (String [] args) throws IOException {

		// Pull data from file
		ArrayList<String>lines = getInput("src/inputDay8.txt"); 

		// Create board 50 wide and 6 tall
		int[][] screen = new int[6][50];

		// Parse the operations
		for (String operation : lines) {
			String[] tokens = operation.split(" "); // split tokens in operation

			if (tokens[0].equals("rotate")) { // look for rotate operations
				int offset = Integer.parseInt(tokens[4]);  //get the offset
				if (tokens[1].equals("row")) {  // perform shift on row
					int row = Integer.parseInt(tokens[2].substring(tokens[2].indexOf("=") + 1));
					for (int i = 0; i < offset; i++) {
						shiftRow(row, screen);
					}
				} else if (tokens[1].equals("column")) { // perform shift on column
					int col = Integer.parseInt(tokens[2].substring(tokens[2].indexOf("=") + 1));
					for (int i = 0; i < offset; i++) {
						shiftColumn(col, screen);
					}
				}
			} else { // look for anything else (rect)
				int w = Integer.parseInt(tokens[1].substring(0, tokens[1].indexOf("x"))); // get rect width (# before 'x')
				int h = Integer.parseInt(tokens[1].substring(tokens[1].indexOf("x") + 1)); // get rect height (# after 'x')
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						screen[i][j] = 1;
					}
				}
			}
		}

		// Count up the number of on-pixels
		int count = 0;
		for (int i = 0; i < screen.length; i++) {
			for (int j = 0; j < screen[i].length; j++) {
				if (screen[i][j] == 1) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

	public static void shiftColumn(int c, int[][] array) {
		int lastVal = array[array.length - 1][c];
		for (int i = array.length - 1; i > 0; i--) {
			array[i][c] = array[i-1][c];
		}
		array[0][c] = lastVal;
	}
	
	public static void shiftRow(int r, int[][] array) {
		int lastVal = array[r][array[r].length - 1];
		for (int i = array[r].length - 1; i > 0; i--) {
			array[r][i] = array[r][i-1];
		}
		array[r][0] = lastVal;
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
