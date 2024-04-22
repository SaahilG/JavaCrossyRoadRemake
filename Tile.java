// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

public class Tile {
	
	private boolean isWalkable; // this variable is not used yet, perhaps something to add in
	private Type type; // the type of tile
	
	/**
	 * This documents "Type"
	 * @author SaahilG25
	 */
	enum Type {
		  ROAD,
		  GRASS,
		  TREE, // this element is not implemented yet
		  ROCK // this element is not implemented yet
		}
	
	/**
	 * Constructor for Tile object
	 * @param isWalkable Whether or not the tile can be walked on (not implemeted yet)
	 * @param type The type of tile it is
	 */
	public Tile (boolean isWalkable, Type type) {
		this.isWalkable = isWalkable;
		this.type = type;
	}
	
	/**
	 * Returns the type of tile
	 * @return Type
	 */
	public Type getType() {
		return this.type;
	}
	
}
