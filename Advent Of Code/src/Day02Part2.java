import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day02Part2 {
	public static void main (String [] args) throws IOException {
		String [] instructions = getInput("src/inputDay02.txt");
		
		char [][] keypad = {
				{' ', ' ', '1', ' ', ' '},
				{' ', '2', '3', '4', ' '},
				{'5', '6', '7', '8', '9'},
				{' ', 'A', 'B', 'C', ' '},
				{' ', ' ', 'D', ' ', ' '}
		};
		
		int x = 0;
		int y = 2;
		String code = "";
		
		for (int i = 0; i < instructions.length; i++) {
			for (int j = 0; j < instructions[i].length(); j++) {
				char direction = instructions[i].charAt(j);
				switch (direction) {
				case 'U':
					if (y != 0 && keypad[y-1][x] != ' ') {
						y--;
					} 
					break;
				case 'D':
					if (y != 4 && keypad[y+1][x] != ' ') {
						y++;
					} 
					break;
				case 'R':
					if (x != 4 && keypad[y][x+1] != ' ') {
						x++;
					} 
					break;
				case 'L':
					if (x != 0 && keypad[y][x-1] != ' ') {
						x--;
					} 
					break;
				}
			}
			code += keypad[y][x];
		}
		System.out.println(code);
	}
	
	public static String[] getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		ArrayList<String> list = new ArrayList<String>();

		String str = null;
		while((str = br.readLine()) != null){
			list.add(str);
		}

		br.close();
		String [] array = new String [list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
}
