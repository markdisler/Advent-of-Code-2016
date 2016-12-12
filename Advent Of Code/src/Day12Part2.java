import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day12Part2 {

	static Map<String, Integer> registers = new HashMap<String, Integer>();
	
	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay12.txt"); // Pull data from file
		registers.put("a", 0);
		registers.put("b", 0);
		registers.put("c", 1);
		registers.put("d", 0);
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			String [] tokens = line.split(" ");

			if (tokens[0].equals("cpy")) {
				String register = tokens[2];
				Integer value = value(tokens[1]);
				registers.put(register, value);
			} else if (tokens[0].equals("inc")) {
				String register = tokens[1];
				Integer value = registers.get(register) + 1;
				registers.put(register, value);
			} else if (tokens[0].equals("dec")) {
				String register = tokens[1];
				Integer value = registers.get(register) - 1;
				registers.put(register, value);
			} else if (tokens[0].equals("jnz")) {
				if (value(tokens[1]) != 0) {
					i += (value(tokens[2]) - 1);
				}
			}
		}
		System.out.println(registers.get("a"));
	}

	public static int value(String k) {
		char asChar = k.charAt(0);
		if ('a' <= asChar && asChar <= 'z') {
			return registers.get(k);
		}
		return Integer.parseInt(k);
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
