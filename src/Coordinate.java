/**
 * Coordinate defines a coordinate i,j for the game board.
 */
public record Coordinate(int i, int j) {

	@Override
	public String toString() {
		return "(" + i + "," + j + " )";
	}

}
