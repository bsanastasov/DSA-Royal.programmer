package frogs;

import java.util.ArrayList;
import java.util.Collections;

public class FrogGame {

	static class Node {
		public ArrayList<Character> state;
		public boolean visited;

		Node(ArrayList<Character> state) {
			this.state = state;
		}

		public static ArrayList<Character> generateInitialState(int n) {

			ArrayList<Character> initialStateArray = new ArrayList<Character>();
			ArrayList<Character> initialStateArrayRight = new ArrayList<Character>();
			for (int i = 0; i < n; ++i) {
				initialStateArray.add('L');
				initialStateArrayRight.add('R');
			}
			initialStateArray.add('0');
			initialStateArray.addAll(initialStateArrayRight);

			return initialStateArray;
		}

		public static ArrayList<Character> getFinalState(ArrayList<Character> state) {
			ArrayList<Character> finalState = new ArrayList<Character>(state);
			Collections.reverse(finalState);

			return finalState;
		}

		public static ArrayList<Node> generateChaveta(ArrayList<Character> state) {
			ArrayList<Node> children = new ArrayList<Node>();
			int indexOfEmpty = state.indexOf('0');
			if (indexOfEmpty > 0 && state.get(indexOfEmpty - 1).equals('L')) {
				ArrayList<Character> copyOneLeftSwap = new ArrayList<Character>(state);
				Collections.swap(copyOneLeftSwap, indexOfEmpty, indexOfEmpty - 1);
				Node nodeChildOneLeft = new Node(copyOneLeftSwap);
				children.add(nodeChildOneLeft);
			}
			if (indexOfEmpty > 1 && state.get(indexOfEmpty - 2).equals('L')) {
				ArrayList<Character> copyTwoLeftSwap = new ArrayList<Character>(state);
				Collections.swap(copyTwoLeftSwap, indexOfEmpty, indexOfEmpty - 2);
				Node nodeChildTwoLeft = new Node(copyTwoLeftSwap);
				children.add(nodeChildTwoLeft);
			}
			if ((indexOfEmpty < state.size() - 1) && state.get(indexOfEmpty + 1).equals('R')) {
				ArrayList<Character> copyOneRightSwap = new ArrayList<Character>(state);
				Collections.swap(copyOneRightSwap, indexOfEmpty, indexOfEmpty + 1);
				Node nodeChildOneRight = new Node(copyOneRightSwap);
				children.add(nodeChildOneRight);
			}
			if ((indexOfEmpty < state.size() - 2) && state.get(indexOfEmpty + 2).equals('R')) {
				ArrayList<Character> copyTwoRightSwap = new ArrayList<Character>(state);
				Collections.swap(copyTwoRightSwap, indexOfEmpty, indexOfEmpty + 2);
				Node nodeChildTwoRight = new Node(copyTwoRightSwap);
				children.add(nodeChildTwoRight);
			}

			return children;
		}
	}

	public static boolean dfs(ArrayList<Character> finalState, Node node1, int count) {

		count++;
		System.out.println(node1.state + " count:" + count);
		if (node1.state.equals(finalState)) {
			System.out.println("SOLUTION:" + node1.state + " count:" + count);
			return true;
		}

		ArrayList<Node> neighbours = Node.generateChaveta(node1.state);
		for (int i = 0; i < neighbours.size(); i++) {
			Node n = neighbours.get(i);
			if (!n.visited) {
				n.visited = true;
				if (dfs(finalState, n, count)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int numberOfFrogsFromOneSide = 10;
		ArrayList<Character> initialState = Node.generateInitialState(numberOfFrogsFromOneSide);
		ArrayList<Character> finalState = Node.getFinalState(initialState);
		Node node = new Node(initialState);
		int count = 0;
		dfs(finalState, node, count);
	}

}
