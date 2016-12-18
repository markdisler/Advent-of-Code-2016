
public class Day18Part2 {

	public static void main(String[] args) {
		String input = ".^^^.^.^^^.^.......^^.^^^^.^^^^..^^^^^.^.^^^..^^.^.^^..^.^..^^...^.^^.^^^...^^.^.^^^..^^^^.....^....";
		int rowCount = 400000;
		String[] rows = new String[rowCount]; // STORE all rows
		rows[0] = input;  // ADD the first row
		
		for (int i = 1; i < rows.length; i++) {
			rows[i] = rowForPreviousRow(rows[i - 1]); // BUILD each row
		}
				
		int safeTiles = 0; 
		for (String row : rows) {  // ITERATE through all rows
			for (char position : row.toCharArray())  // ITERATE through all positions in row
				if (position == '.')  // CHECK if the position is safe
					safeTiles++;  // COUNT it as a safe tile
		}
		System.out.println(safeTiles);
	}
	
	public static String rowForPreviousRow(String row) {
		String newRow = "";
		for (int i = 0; i < row.length(); i++) {
			char left = (i > 0) ? row.charAt(i - 1) : '.';  // GET left. Assume safe if edge.
			char center = row.charAt(i);  // GET center
			char right = (i < row.length() - 1) ? row.charAt(i + 1) : '.';  // GET right. Assume safe if edge.
			newRow += willBeTrap(left, center, right) ? '^' : '.';  // BUILD row based on if it should be a trap
		}		
		return newRow;
	}
	
	public static boolean willBeTrap(char left, char center, char right) {
		if (left == '^' && center == '^' && right == '.')
			return true;	
		if (left == '.' && center == '^' && right == '^')
			return true;
		if (left == '^' && center == '.' && right == '.')
			return true;
		if (left == '.' && center == '.' && right == '^')
			return true;
		
		return false;
	}
}
