import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day11Part1 {
	
	// INPUT
	static String [][] startFloor = {
			{"E","TG","TM","PG","SG"},
			{"PM", "SM"},
			{"PRG", "PRM", "RG", "RM"},
			{}
	};
	
//	static String [][] startFloor = { {"E","HM","LM"}, {"HG"}, {"LG"}, {} };

	public static void main (String [] args) throws IOException {

		// CONVERT starting state to the state form
		ArrayList<HashSet<String>> startState = new ArrayList<HashSet<String>>();
		for (int i = 0; i < startFloor.length; i++) {
			startState.add(new HashSet<String>());
			for (int j = 0; j < startFloor[i].length; j++) {
				if (!startFloor[i][j].equals(""))
					startState.get(i).add(startFloor[i][j]);
			}
		}

		HashSet<ArrayList<HashSet<String>>> visited = new HashSet<ArrayList<HashSet<String>>>(); 	// STORE visited elements
		Queue <Vertex> queue = new LinkedList<Vertex>();  // CREATE queue for BFS

		// ADD initial state
		visited.add(startState);
		queue.add(new Vertex(startState));

		while (!queue.isEmpty()) {
			Vertex v = queue.remove();
			ArrayList<HashSet<String>> next = getNextUnvisitedNeighbor(v.getState(), visited);

			while (next != null && !isGoal(next)) {
				visited.add(next);

				Vertex newVert = new Vertex(next);
				newVert.setPathLength(v.getPathLength() + 1);
				queue.add(newVert);
				next = getNextUnvisitedNeighbor(v.getState(), visited);
			}

			if (next != null && isGoal(next)) {
				System.out.println(v.getPathLength() + 1);
				break;
			}
		}			
	}

	public static ArrayList<HashSet<String>> moveItem(ArrayList<HashSet<String>> startingState, int elevator, int destinationOffset, String item1, String item2) {
		ArrayList<HashSet<String>> floors = deepCopyState(startingState);  // make copy of state
		floors.get(elevator).remove("E");  // remove the elevator

		if ((destinationOffset < 0 && elevator > 0) || (destinationOffset > 0 && elevator < startingState.size() - 1)) {
			floors.get(elevator + destinationOffset).add("E");  // add the elevator back at the new floor

			if (item1 != null) {  // move item 1 to the new floor
				floors.get(elevator + destinationOffset).add(item1);
				floors.get(elevator).remove(item1);
			}
			if (item2 != null) {  // move item 2 to the next floor
				floors.get(elevator + destinationOffset).add(item2);
				floors.get(elevator).remove(item2);
			}
			return floors;  // return the new state
		}
		return null;
	}

	public static void addAsSuccessorIfValid(ArrayList<ArrayList<HashSet<String>>> successors, ArrayList<HashSet<String>> state) {
		if (state != null) {
			boolean valid = true;

			for (int i = 0; i < state.size() && valid; i++) {
				HashSet<String> items = state.get(i);
				for (String item : items) {
					if (item.contains("M") && !chipHasGenerator(item, items) && getNumberOfGenerators(items) >= 1) {
						valid = false;
						break;
					}
				}
			}

			if (valid && !successors.contains(state)) {
				successors.add(state);
			}
		}
	}

	private static boolean chipHasGenerator(String item, HashSet<String> floor) {
		for (String itemOnFloor : floor) {
			if (!itemOnFloor.equals("")){
				if (!itemOnFloor.equals(item) && itemOnFloor.substring(0, itemOnFloor.length() - 1).equals(item.substring(0, item.length() - 1))) {
					return true;
				}
			}
		}
		return false;
	}

	private static int getNumberOfGenerators(HashSet<String> floor) {
		int count = 0;
		for (String it : floor)  // ITERATE through all items on floor
			if (it.contains("G"))   // if an item on a floor has a G, it's a generator...
				count++;  // INCREMENT counter
		return count;
	}

	private static ArrayList<HashSet<String>> getNextUnvisitedNeighbor(ArrayList<HashSet<String>> state, HashSet<ArrayList<HashSet<String>>> visited) {
		ArrayList<ArrayList<HashSet<String>>> next = new ArrayList<ArrayList<HashSet<String>>>();

		// LOCATE the elevator
		int elevatorLocation = 0;
		for (int i = 0; i < state.size(); i++) {
			HashSet<String> floor = state.get(i);
			if (floor.contains("E")) {
				elevatorLocation = i;
				break;
			}
		}

		HashSet<String> floor = state.get(elevatorLocation);  // GET the floor with the elevator
		for (String item1 : floor) {  // ITERATE through all elements on floor
			if (!item1.equals("") && !item1.equals("E")) {  // LOOK at the elements that are not the elevator/not empty
				ArrayList<HashSet<String>> aState = moveItem(state, elevatorLocation, 1, item1, null);  // Get state for up shift
				ArrayList<HashSet<String>> bState = moveItem(state, elevatorLocation, -1, item1, null); // Get state for down shift
				addAsSuccessorIfValid(next, aState);  // ADD if and only if the state is valid (nothing will go wrong)
				addAsSuccessorIfValid(next, bState);  // ADD if and only if the state is valid (nothing will go wrong)

				// GET all states for all combinations of item movements
				for (String item2 : floor) {  
					if (!item1.equals(item2) && !item2.equals("") && !item2.equals("E")) {
						ArrayList<HashSet<String>> cState = moveItem(state, elevatorLocation, 1, item1, item2); // Get state for up shift
						ArrayList<HashSet<String>> dState = moveItem(state, elevatorLocation, -1, item1, item2);// Get state for down shift
						addAsSuccessorIfValid(next, cState);
						addAsSuccessorIfValid(next, dState);
					}
				}
			}
		}

		for (int i = 0; i < next.size(); i++) {
			if (!visited.contains(next.get(i))) {
				return next.get(i);
			}
		}
		return null;
	}

	public static ArrayList<HashSet<String>> deepCopyState(ArrayList<HashSet<String>> state) {
		ArrayList<HashSet<String>> stateCopy = new ArrayList<HashSet<String>>();
		for (int i = 0; i < state.size(); i++) {
			stateCopy.add(new HashSet<String>());
			for (String itm : state.get(i)) {
				stateCopy.get(i).add(itm);
			}
		}
		return stateCopy;
	}

	public static boolean isGoal(ArrayList<HashSet<String>> state) {
		for (int i = 0; i < 3; i++) 
			if (state.get(i).size() != 0) // if something is found on floors 1-3...
				return false;  // everything is not on floor 4, so goal is not met
		return true;
	}

	private static class Vertex {
		private int pathLength;
		private ArrayList<HashSet<String>> state;
		public Vertex(ArrayList<HashSet<String>> state) {
			this.state = state;
			this.pathLength = 0;
		}
		public void setPathLength(int len) {			this.pathLength = len;		}
		public ArrayList<HashSet<String>> getState() {  return this.state; 			}	
		public int getPathLength() {     				return this.pathLength;     }
	}
}
