// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;

public class Main {
	
	
	private static Color lightGreen = new Color(78, 204, 99); // used for grass color
	private static Color darkGreen = new Color(70, 179, 88); // used for grass color
	private static Color brown = new Color(145, 117, 61); // used for player leg color
	private static Color darkGray = new Color(112, 112, 111); // used for shadow color
	private static Color darkDarkGray = new Color(79, 79, 79); // used for shadow color
	private static int upFrames = 0; // used to decide how long player remained in air during hop
	private static ArrayList<Integer> takenSpots = new ArrayList<Integer>(); // prevents car crowding
	private static String quality = "high"; // set to "high" for full graphics, "low" for better performance
	private static int tileSize = 80; //width and height of a square tile
	private static int pWidth = 720; // panel width
	private static int pHeight = 640; // panel height
	private static int carElementLoc = 36; // used for some placement of elements of car
	private static boolean changeFrame = false;
	
	public static int score = 0; // keeps track of score (distance traveled) -- accessed by other classes for things like
	                            //  speed of cars as score goes higher
	/**
	 * Increases score by one, to be called on by other Classes to change score from outside of Main.java
	 */
	public static void scoreIncrease() {
		score++;
	}
	
	/**
	 * Main method - Creates drawing panel and player and runs the main game method startGame()
	 */
	public static void main(String[] args) {
		DrawingPanel panel = new DrawingPanel(pWidth, pHeight);
			    MapScreen m = new MapScreen();
	    Player p = new Player(panel);
		startGame(panel, m, p);
		
	}
	
	/**
	 * Runs the actual game itself
	 * @param panel Drawing panel where game is drawn onto
	 * @param m MapScreen that holds all the MapPieces to form endless map
	 * @param p Player object controlled by user
	 */
	public static void startGame(DrawingPanel panel, MapScreen m, Player p) {
		Graphics g = panel.getGraphics();
		panel.addKeyListener(p);
		Image Road = null;
		Font scoreFont = new Font( "SansSerif", Font. PLAIN, 40 );
		Font gameOverFont = new Font ("SansSerif", Font.BOLD, 53);
		g.setFont(scoreFont);
		// reading image file for road image
		try {
			Road = ImageIO.read(new File("C:/Users/SaahilG25/eclipse-workspace/Crossy Road/src/Road3.0.jpg"));
			}
		catch (Exception e) {
			System.out.println(e);
		}
		boolean leave = false;
		while(true) {
			m.makeUpdatedMap(p);
			draw(g, m, p, Road);
			while(!changeFrame) {
				
			}
			panel.sleep(105);
			if (m.getMap().get(0).getPiece().get(0).getType()==Tile.Type.ROAD) {
				for(Car c : m.getMap().get(0).getListOfCars()) {
					if ((p.getPos()>=c.getX()-30 && p.getPos()<c.getX()+78)) { // collision detected
						leave = true;
						break;
					}
				}
				if (leave) {
					break;
				}
			}
		}
		draw(g, m, null, Road);
		
		// Game Over Screen with Final Score
		g.setColor(Color.RED);
		g.fillRect(160, 160, 400, 320);
		g.setColor(Color.WHITE);
		g.fillRect(180, 180, 360, 280);
		g.setFont(gameOverFont);
		g.setColor(Color.BLACK);
		g.drawString("GAME OVER!", 190, 240);
		g.setFont(scoreFont);
		g.drawString("Score:", 310, 300);
		g.drawString(String.valueOf(score),355-(12*((String.valueOf(score)).length()-1)), 345);
		
		
	}
	
	/**
	 * Draws all elements onto the drawing panel
	 * @Param g Graphics from drawing panel use to draw
	 * @Param m The full map object
	 * @Param p Player object controlled through input
	 * @Param Road The image of the road used
	 */
	public static void draw(Graphics g, MapScreen m, Player p, Image Road) { // although there are still some "magic" numbers,
		changeFrame = false;												 // reworking all the numbers to be proportionate
		g.setColor(Color.WHITE);											 // and in variables would require way too much 
		g.fillRect(0, 0, tileSize, tileSize);							     // math, so at this stage, all elements will just
		if (p!=null) {													     // have to remain the same size
		if (p.getPosY()!=600) {												 
			upFrames++;												
		}
		if (upFrames > 2) {
			p.setPosY(600);
			upFrames = 0;
		}
		}
		for (MapPiece mp : m.getMap()) {
			for (Tile t : mp.getPiece()) {
				if (t.getType()==Tile.Type.GRASS) {
					// setting color of row to alternate from light and dark green for grass
					if (m.getMap().indexOf(mp)%2==0) {
						if (m.getFlip()) {
						g.setColor(lightGreen);
						} else {
							g.setColor(darkGreen);
						}
					} else {
						if (m.getFlip()==false) {
						g.setColor(lightGreen);}
						else {
							g.setColor(darkGreen);
							
						}
					}
					g.fillRect(mp.getPiece().indexOf(t)*tileSize, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize), tileSize, tileSize);
				} else {
					// using road image to draw road tile
					g.drawImage(Road, mp.getPiece().indexOf(t)*tileSize, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize), null);
				}
			}
			
			// code to ensure no overlapping cars
			takenSpots.clear();
			boolean breaker = false;
			if (mp.getPiece().get(0).getType()==Tile.Type.ROAD) {
				for (Car c : mp.getListOfCars()) {
					if(takenSpots.size()>0) {
						for (int i : takenSpots) {
							if (c.getX()<i+tileSize+10 && c.getX()>i-tileSize-10) {
									// deletes car if it is overlapping another car
									c = null;
									mp.getListOfCars().remove(c);
									breaker = true;
									break;
							} 
						}
						if (breaker) {
							breaker = false;
							continue;
						} else {
							takenSpots.add(c.getX());
							c.update();
							if (quality.equals("high")) {
							//car shadow
							g.setColor(darkDarkGray);
							g.fillOval(c.getX()-(carElementLoc/2), pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+59, 86, 4 );
							}
							//car wheels
							g.setColor(Color.BLACK);
							g.fillOval(c.getX()-8, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+46, (carElementLoc-6)/2, (carElementLoc-6)/2);
							g.fillOval(c.getX()+42, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+46, (carElementLoc-6)/2, (carElementLoc-6)/2);
							//car body
							g.setColor(c.getColor());
							g.fillRect(c.getX(), pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 50 , carElementLoc);
							g.fillRect(c.getX()-(carElementLoc/2),pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+carElementLoc,(carElementLoc/2), (carElementLoc/2));
							g.fillRect(c.getX()+50,pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+carElementLoc,(carElementLoc/2), (carElementLoc/2));
							if (quality.equals("high")) {
							//wind shield + handle + head light
							g.setColor(Color.lightGray);
							if(c.getStart()==0) {
								g.fillRect(c.getX()+32, 645-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , (carElementLoc/2) , 13);
								g.setColor(Color.BLACK);
								g.fillRect(c.getX()+30, 660-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 7 , 2);
								g.setColor(Color.YELLOW);
								g.fillRect(c.getX()+60, 686-(tileSize+m.getMap().indexOf(mp)*tileSize) , 8, 4);
							} else {
								g.fillRect(c.getX(), 645-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , (carElementLoc/2) , 13);
								g.setColor(Color.BLACK);
								g.fillRect(c.getX()+13, 660-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 7 , 2);
								g.setColor(Color.YELLOW);
								g.fillRect(c.getX()-(carElementLoc/2), 686-(tileSize+m.getMap().indexOf(mp)*tileSize) , 8, 4);
							}
							}
						}
					} else {
						takenSpots.add(c.getX());
						c.update();
						if (quality.equals("high")) {
						//car shadow
						g.setColor(darkDarkGray);
						g.fillOval(c.getX()-(carElementLoc/2), pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+59, tileSize+6, 4 );
						}
						//car wheels
						g.setColor(Color.BLACK);
						g.fillOval(c.getX()-8, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+46, (carElementLoc-6)/2, (carElementLoc-6)/2);
						g.fillOval(c.getX()+42, pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+46, (carElementLoc-6)/2, (carElementLoc-6)/2);
						//car body
						g.setColor(c.getColor());
						g.fillRect(c.getX(), pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 50 , carElementLoc);
						g.fillRect(c.getX()-(carElementLoc/2),pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+carElementLoc,(carElementLoc/2), (carElementLoc/2));
						g.fillRect(c.getX()+50,pHeight-(tileSize+m.getMap().indexOf(mp)*tileSize)+carElementLoc,(carElementLoc/2), (carElementLoc/2));
						if (quality.equals("high")) {
						//wind shield + handle + head light
						g.setColor(Color.lightGray);
						if(c.getStart()==0) {
							g.fillRect(c.getX()+32, 645-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , (carElementLoc/2) , 13);
							g.setColor(Color.BLACK);
							g.fillRect(c.getX()+30, 660-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 7 , 2);
							g.setColor(Color.YELLOW);
							g.fillRect(c.getX()+60, 686-(tileSize+m.getMap().indexOf(mp)*tileSize) , 8, 4);
						} else {
							g.fillRect(c.getX(), 645-(tileSize+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , (carElementLoc/2) , 13);
							g.setColor(Color.BLACK);
							g.fillRect(c.getX()+13, 660-(80+m.getMap().indexOf(mp)*tileSize) + (carElementLoc/2) , 7 , 2);
							g.setColor(Color.YELLOW);
							g.fillRect(c.getX()-(carElementLoc/2), 686-(80+m.getMap().indexOf(mp)*tileSize) , 8, 4);
						}
						}
					}
					
			
				}
			}
			if (p!=null) {
			if (quality.equals("high")) { // adding extra graphics if the quality is set to "high"
			//player shadow
			if(m.getMap().get(0).getPiece().get(0).getType()==Tile.Type.GRASS){
				if (m.getFlip()==true) {
				g.setColor(darkGray);
				} else {
					g.setColor(darkDarkGray);
				}
			} else {
				g.setColor(Color.BLACK);
			}
			g.fillOval(p.getPos()-10-(p.getPosY()-600)/2, 615, 20-600+p.getPosY(), 8);
			}
			//player feet
			g.setColor(brown);
			g.fillRect(p.getPos()-4, p.getPosY()+13 , 2, 5);
			g.fillRect(p.getPos()+2, p.getPosY()+13 , 2, 5);
			//player body
			g.setColor(Color.YELLOW);
			g.fillOval(p.getPos()-5, p.getPosY()-15 , 10, 10);
			g.fillOval(p.getPos()-10, p.getPosY()-7, 20, 20);
			//score (top-left)
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(score), 30-(12*((String.valueOf(score)).length()-1)), 50);
			}
		}
		changeFrame = true;
	}
}
