// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

import java.util.*;


public class MapPiece {
	
	private ArrayList<Tile> rowOfTiles; // Arraylist holds tile objects
	private ArrayList<Car> listOfCars = new ArrayList<Car>(); // ArrayList holds car objects for road
	private int numCars; // number of cars on road
	private int direction; // direction cars will travel
	
	/**
	 * constructor for MapPiece object
	 * @param rowOfTiles
	 */
	public MapPiece(ArrayList<Tile> rowOfTiles) {
		int start;
		this.rowOfTiles = rowOfTiles;
		numCars = (int) (Math.random()*4+1);
		
		direction = (int) (Math.random()*4);
		if (direction <=2) {
			start = 0;
		} else {
			start = 720;
		}
		for (int i = 0; i < numCars; i++) {
			Car c = new Car(start);
		    listOfCars.add(c);
		}
	}
	
	/**
	 * returns the rowOfTiles
	 * @return rowOfTiles
	 */
	public ArrayList<Tile> getPiece(){
		return rowOfTiles;
	}
	 
	/**
	 * gets the List of Cars 
	 * @return listOfCars
	 */
	public ArrayList<Car> getListOfCars(){
		return listOfCars;
	}
	
}
