import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day03Part1 {
	public static void main (String [] args) throws IOException {
		String [] triangles = getInput("src/inputDay3.txt");
		int numValidTriangles = 0;
		for (String triangle : triangles) {
			int [] sides = stringToIntArray(triangle);
			if (sidesSatisfyTriangleRule(sides)) {
				numValidTriangles++;
			}
		}
		System.out.println(numValidTriangles + " are possible.");
	}
	
	private static boolean sidesSatisfyTriangleRule(int[] sides) {
		if (sides[0] + sides[1] <= sides[2]) 
			return false;
		if (sides[1] + sides[2] <= sides[0])
			return false;
		if (sides[0] + sides[2] <= sides [1])
			return false;
		return true;
	}
	
	public static int[] stringToIntArray(String s) {
		String [] sArr = s.split(" ");
		int [] intArr = new int[sArr.length];
		for (int i = 0; i < sArr.length; i++) {
			intArr[i] = Integer.parseInt(sArr[i]);
		}
		return intArr;
	}
	
	public static String[] getInput(String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(p));
		ArrayList<String> list = new ArrayList<String>();

		String str = null;
		while((str = br.readLine()) != null){
			str = str.trim();
			str = str.replaceAll("    ", " ");
			str = str.replaceAll("   ", " ");
			str = str.replaceAll("  ", " ");
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
