import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2Part1 {
	public static void main (String [] args) throws IOException {
		String [] instructions = getInput("src/inputDay2.txt");
		
		int [][] keypad = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		
		int x = 1;
		int y = 1;
		String code = "";
				
		for (int i = 0; i < instructions.length; i++) {
			for (int j = 0; j < instructions[i].length(); j++) {
				char direction = instructions[i].charAt(j);
				switch (direction) {
				case 'U':
					if (y != 0) {
						y--;
					} 
					break;
				case 'D':
					if (y != 2) {
						y++;
					} 
					break;
				case 'R':
					if (x != 2) {
						x++;
					} 
					break;
				case 'L':
					if (x != 0) {
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
