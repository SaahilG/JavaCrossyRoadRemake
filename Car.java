// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

import java.awt.Color;

public class Car {
	public Color[] colors = {Color.RED, Color.GREEN, Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.YELLOW}; // color array to choose color from
	private int xPos; // X Position of Car
	private int start; // start position of Car
	private int change; // the actual x change of the car
	private int length; // length of car
	private int speed; // speed of car
	private Color color = colors[(int) (Math.random()*5)]; // color of car
	
	/**
	 * Constructor for car object
	 * @param start Determines if car moves right or left
	 */
	public Car(int start) {
		length = 86;
		speed = 7;
		this.start = start;
		if (start == 0) {
			xPos = (int) (Math.random()*720);
			change = speed;
		} else {
			xPos = (int) (Math.random()*720);
			change = -speed;
		}
		
	}
	
	/**
	 * returns xPosition
	 * @return xPos
	 */
	public int getX() {
		return xPos;
	}
	
	/**
	 * updates the car position according to place on screen
	 */
	public void update() {
		if (start == 0) {
			
			change = speed;
		} else {
			
			change = -speed;
		}
		if (start==0) {
			if (xPos <= 720) {
				xPos+=change;
			} else {
				xPos = 0 - length;
			}
		} else {
			if(xPos >= 0-length) {
				xPos += change;
			} else {
				xPos = start+length;
			}
		}
		speed = 7 + Main.score/5;
	}
	
	/**
	 * moves one x position
	 */
	public void move() {
		xPos++;
	}
	
	/**
	 * return color
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * returns start
	 * @return start
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * returns length
	 * @return length
	 */
	public int getLength() {
		return length;
	}
}
