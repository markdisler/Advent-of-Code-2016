import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Day13Part2 {

	public static void main (String [] args)  {
		int numberOfReachablePositions = 0;
		ArrayList<Point> visited = new ArrayList<Point>(); // keep track of all visited nodes in graph
		Queue<Vertex> q = new LinkedList<Vertex>();  // keep a queue

		// ADD initial state
		Point initialState = new Point (1,1);
		visited.add(initialState);
		q.add(new Vertex(initialState));
		numberOfReachablePositions++; // COUNT the start point as a reachable location

		while (!q.isEmpty()) {
			Vertex v = q.remove(); // DEQUEUE next vertex
			Point next = getNextUnvisitedNeighbor(v.getState(), visited);  // GET unvisited neighbor

			while (next != null && !(next.x == 31 && next.y == 39)) {  // if there is a neighbor that's not the goal...
				visited.add(next); // MARK as visited

				Vertex newVert = new Vertex(next); // MAKE new vertex
				newVert.setPathLength(v.getPathLength() + 1);
				if (newVert.getPathLength() <= 50) {
					numberOfReachablePositions++;
				}

				q.add(newVert); // ADD this vertex to the queue
				next = getNextUnvisitedNeighbor(v.getState(), visited); // GET next neighbor			
			}

			// If goal is found:
			if (next != null && (next.x == 31 && next.y == 39)) {
				break;
			}
		}		
		System.out.println(numberOfReachablePositions);
	}

	private static Point getNextUnvisitedNeighbor(Point state, ArrayList<Point> visited) {
		ArrayList<Point> next = new ArrayList<Point>();
		if (isValidPosition(state.x + 1, state.y)) {
			next.add(new Point(state.x + 1, state.y));
		}
		if (isValidPosition(state.x - 1, state.y)) {
			next.add(new Point(state.x - 1, state.y));
		}
		if (isValidPosition(state.x, state.y + 1)) {
			next.add(new Point(state.x, state.y + 1));
		}
		if (isValidPosition(state.x, state.y - 1)) {
			next.add(new Point(state.x, state.y - 1));
		}
		for (int i = 0; i < next.size(); i++) {
			if (!visited.contains(next.get(i))) {
				return next.get(i);
			}
		}
		return null;
	}

	public static boolean isValidPosition(int x, int y) {
		if (x < 0 || y < 0) 
			return false;
		int favoriteNumber = 1364;  // puzzle input
		int val = x*x + 3*x + 2*x*y + y + y*y;
		val += favoriteNumber;
		String binary = Integer.toBinaryString(val);
		int oneCount = count('1', binary);
		return (oneCount % 2 == 0);
	}

	public static int count(char c, String inStr) {
		int count = 0;
		for (char l : inStr.toCharArray()) {
			if (l == c) 
				count++;
		}
		return count;
	}

	private static class Vertex {
		private int pathLength;
		private Point state;

		public Vertex(Point state) {
			this.state = state;
			this.pathLength = 0;
		}

		public void setPathLength(int len) {
			this.pathLength = len;
		}

		public Point getState() {
			return this.state;
		}

		public int getPathLength() {
			return this.pathLength;
		}
	}
}
