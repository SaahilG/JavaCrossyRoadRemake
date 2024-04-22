// CS III
// Saahil Gupta
// 5/26/2023
// Remake of game Crossy Road

import java.awt.event.*;

public class Player extends KeyAdapter 
	{
	   private int pos; // position x of player
	   private boolean up = false; // checks if the player has jumped
	   private int posY = 600; // position y of player
	   
	   /**
	   * Constructs a KeyListener
	   * @param panel the DrawingPanel to draw onto
	   */
	   public Player(DrawingPanel panel) 
	   {
	      this.pos = panel.getWidth()/2;
	   }
	   
	   /**
	   * Handles key presses for left and right and up arrow keys.
	   * @Param event the KeyEvent representing the key pressed by the user
	   */
	   @Override
	   public void keyPressed(KeyEvent event) 
	   {
		
	      int code = event.getKeyCode(); //Look up the KeyEvent Javadocs to see all possible KeyCodes
	      
	      //If they pressed the left arrow key, move the circle to the left
	      if (code == KeyEvent.VK_LEFT) 
	      {
	    	  
	    	  if (pos > 70) {
	    		  pos-=40;
	    		  posY-=4;
	    	  }
	      } 
	      //If they pressed the right arrow key, move the circle to the right
	      else if (code == KeyEvent.VK_RIGHT) 
	      {
	    	
	    	 if (pos < 650) {
	    		 pos+=40;
	    		 posY-=4;
	    	
	    	 }
	      } else if (code == KeyEvent.VK_UP) {
	    	  up = true;
	    	  posY-=4;
	      } else if (code == KeyEvent.VK_SPACE) {
	    	  System.exit(0);
	      }
	    
	   }
	   
	   /**
	    * Returns x position of player
	    * @return pos
	    */
	   public int getPos() {
		   return pos;
	   }
	   
	   /**
	    * Returns whether or not the player hopped
	    * @return up
	    */
	   public boolean getUp() {
		   return up;
	   }
	   
	   /**
	    * Sets the player to have hopped or not
	    * @param x up is set to x
	    */
	   public void setUp(boolean x) {
		   up = x;
	   }
	   
	   /**
	    * Returns the y position of player
	    * @return posY
	    */
	   public int getPosY() {
		   return posY;
	   }
	   
	   /**
	    * sets the y position of player to i
	    * @param i
	    */
	   public void setPosY(int i) {
		   posY = i;
	   }
	}

