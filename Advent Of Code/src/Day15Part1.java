import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day15Part1 {

	public static void main (String [] args) throws IOException {
		ArrayList<String>lines = getInput("src/inputDay15.txt"); // Pull data from file
		ArrayList<Disk> startDisks = getDisksFromInput(lines);  // BUILD original disks list from starting position
		
		int time = 0;  // button press time that will work
		boolean fellThrough = false; // FLAG to know when done
		
		while (!fellThrough) { 
			ArrayList<Disk> disks = deepCopyDisksList(startDisks); // CREATE copy of the starting disks at the current time
			for (int i = 0; i < disks.size(); i++) {  // LOOK at each disk to determine if the capsule can pass
				incrementDisks(disks); // MOVE the disks to the next position
				if (!disks.get(i).canPass()) {  // if no opening...
					break; // stop checking at this start time
				}
				if (i == disks.size() - 1) { // If we reach the last disk...
					fellThrough = true;  // the capusle did fall through
					break; // stop checking 
				}
			}
			incrementDisks(startDisks); // move start disks forward
			time++; // move the starting button press time forward
		}
		System.out.println(time - 1);  // output the button press time (minus 1 to counter the last 'time++'
	}
	
	public static void incrementDisks(ArrayList<Disk> disks) {
		for (Disk disk : disks) {  // Iterate through all disks
			disk.incrementPosition();  // Increment their position
		}
	}

	public static ArrayList<Disk> deepCopyDisksList(ArrayList<Disk> disks) {
		ArrayList<Disk> disksCopy = new ArrayList<Disk>();  // CREATE new arraylist
		for (Disk disk : disks) {  // DEEP COPY the disks from the original list
			disksCopy.add(new Disk(disk.currentPosition(), disk.size()));  
		}
		return disksCopy;
	}
	
	public static ArrayList<Disk> getDisksFromInput(ArrayList<String> lines) {
		ArrayList<Disk> disks = new ArrayList<Disk>();
		for (String s : lines) {
			String [] tokens = s.split(" ");
			int size = Integer.parseInt(tokens[3]);
			int startPos = Integer.parseInt(tokens[11].substring(0, tokens[11].length() - 1));
			disks.add(new Disk(startPos, size));  // CREATE disk from each line in the input
		}
		return disks;		
	}
		
	private static class Disk {
		private int size;
		private int currentPos;
		
		public Disk (int startPos, int size) {
			this.size = size;
			this.currentPos = startPos;
		}
		
		public void incrementPosition() {
			this.currentPos = (this.currentPos + 1) % size;
		}
		
		public boolean canPass() {
			return this.currentPos == 0;
		}
		
		public int size() {
			return this.size;
		}
		
		public int currentPosition() {
			return this.currentPos;
		}		
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
