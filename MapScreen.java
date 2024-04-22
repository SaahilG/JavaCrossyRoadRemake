// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

import java.util.*;

//composition of Map Piece objects
public class MapScreen {
	
	private static int length;
	private static int place; 
	private static boolean curGrass; 
	private static boolean flip;
	private ArrayList<MapPiece> screen;
	
	public MapScreen() {
		length = 2; // how long of a grass/road stretch there will be - starts at 2, but becomes random after
		place = 0; // keep making new MapPieces until this becomes 7 (full screen)
		curGrass = true; // decides whether or not we are making a grass chain of map pieces or not
		flip = true; // variable that helps decide grass color
		screen = new ArrayList<MapPiece>(); // ArrayList holding Map Piece objects
	}
	
	/**
	 * Adds a MapPiece object to array of screen
	 * @param p MapPiece object to be added
	 */
	public void add (MapPiece p) {
		screen.add(p);
	}
	
	/**
	 * Returns ArrayList of MapPiece objects
	 * @return screen
	 */
	public ArrayList<MapPiece> getMap() {
		return screen;
	}
	
	/**
	 * Returns if color flip is needed for grass
	 * @return flip
	 */
	public boolean getFlip() {
		return flip;
	}
	
	/**
	 * Updates the map according to player upward movement
	 * @Param p Referencing the player object
	 */
	public void makeUpdatedMap(Player p) {
		if (p.getUp()) {
			p.setUp(false);
			MapPiece removal = screen.get(0);
			for (Tile t : removal.getPiece()) {
				t = null;
				removal.getPiece().remove(t);
			}
			for (Car c : removal.getListOfCars()) {
				c = null;
				removal.getListOfCars().remove(c);
			}
			removal = null;
			
			screen.remove(0); // shifting the screen down one
			
			place--;
			Main.scoreIncrease();
			if (flip) {
				flip = false;
			} else {
				flip = true;
			}
		}
		// loops until seven rows are made
		while (place < 7) {
			if (length == 0) { // code for randomizing how long the stretch of blocks will be
				length = (int) (Math.random()*4 + 1);
				curGrass = !curGrass;
				if (curGrass==false && length == 1) {
					length+=1;
				}
			}
			ArrayList<Tile> ret = new ArrayList<Tile>();
			if (curGrass == true) {
				for (int i = 0; i < 9; i++) {
					Tile newTile = new Tile (true, Tile.Type.GRASS);
					ret.add(newTile);
				}
				MapPiece newPiece = new MapPiece (ret);
				this.add(newPiece);
			
			} else {
				for (int i = 0; i < 9; i++) {
					Tile newTile = new Tile (true, Tile.Type.ROAD);
					ret.add(newTile);
				}
				MapPiece newPiece = new MapPiece (ret);
				this.add(newPiece);
			}
			length--;
			place++;
		}
	}
	
}
