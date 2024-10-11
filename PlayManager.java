package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;

public class PlayManager {
	
	final int WIDTH = 360;
	final int HEIGHT = 600;
	
	public static int left_x;
	public static int right_x;
	public static int bottom_y;
	public static int top_y;
	
	//mino
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y;
	Mino nextMino;
	final int NEXTMINO_X;
	final int NEXTMINO_Y;
	public static ArrayList<Block> staticblocks = new ArrayList<>();
	
	
	
	
	//other
	
	public static int dropInterval = 60; // drops 60 frams
	   boolean gameOver;
//effect
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();
	
   public PlayManager() {
	   
	   //Main Play Area Frame
	   left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
	   right_x = left_x + WIDTH;
	   top_y  = 50;
	   bottom_y = top_y + HEIGHT;
	   
	      MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
		  MINO_START_Y = top_y + Block.SIZE;
		  
		  NEXTMINO_X = right_x + 175;
		  NEXTMINO_Y = top_y + 500;
		 
		  currentMino = pickMino();
		  currentMino.setXY(MINO_START_X, MINO_START_Y);
		  
		  nextMino = pickMino();
		  nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	   
   }
   
  
   private Mino pickMino() {
	   
	   Mino mino = null;
	   
	   int i = new Random().nextInt(7);
	   
	   switch(i) {
	   case 0: mino = new Mino_L1(); break;
	   case 1: mino = new Mino_L2(); break;
	   case 2: mino = new Mino_square() ;break;
	   case 3: mino = new Mino_Bar(); break;
	   case 4: mino = new Mino_T(); break;
	   case 5: mino = new Mino_Z1(); break;
	   case 6: mino = new Mino_Z2(); break;
	   }
	   return mino;
   }

   
   public void update() {
	
	   currentMino.update();
	   // CURRENTMINO TYPE ACTIVE
	   if (currentMino.active == false) {
		   
	
		   
		   // if mino is active 
		   
		   staticblocks.add(currentMino.b[0]);
		   staticblocks.add(currentMino.b[1]);
		   staticblocks.add(currentMino.b[2]);
		   staticblocks.add(currentMino.b[3]);
		   
		   // check if the game is over
		   if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
			   //this means the currentaMino immediately collided a block and could'nt move
			   gameOver = true;
		   }
		   currentMino.dectivating = false;
		// replace the currentMino with the nextMino
		   currentMino = nextMino;
		   currentMino.setXY(MINO_START_X, MINO_START_Y) ;
		   nextMino = pickMino();
		   nextMino.setXY(NEXTMINO_X, NEXTMINO_Y) ;
		   
		   //when A MINO BECOMR INACTIVE
		   checkDelete();
		   
	   }
	   else {
		   currentMino.update();
		  
	   }
	   
   }
   
   public void checkDelete() {
	    int x = left_x;
	    int y = top_y;
	    int blockCount = 0;

	    // Loop through the play area grid
	    while (y < bottom_y) {
	        x = left_x;
	        blockCount = 0;

	        // Check each block in the current row
	        while (x < right_x) {
	            for (int i = 0; i < staticblocks.size(); i++) {
	                if (staticblocks.get(i).x == x && staticblocks.get(i).y == y) {
	                    blockCount++;
	                }
	            }
	            x += Block.SIZE; // Move to the next block horizontally
	        }

	        // If the row is full (12 blocks), delete the row
	        if (blockCount == 12) {
	        	
	        	effectCounterOn = true;
	        	effectY.add(y);
	        	
	            // Remove all blocks in the full row
	            for (int i = staticblocks.size() - 1; i >= 0; i--) {
	                if (staticblocks.get(i).y == y) {
	                    staticblocks.remove(i);
	                }
	            }

	            // Move remaining blocks down by one row
	            for (int i = 0; i < staticblocks.size(); i++) {
	                if (staticblocks.get(i).y < y) {
	                    staticblocks.get(i).y += Block.SIZE;
	                }
	            }
	        }

	        // Move to the next row (downward)
	        y += Block.SIZE;
	    }
	}

			   
   
   public void draw(Graphics2D g2) {
	   
	   //main play area
	g2.setColor(Color.WHITE);
	g2.setStroke(new BasicStroke(4f));
	g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
		
	// mini frame
	
	
	int x = right_x + 100;
	int y = bottom_y - 200;
	g2.drawRect(x, y, 200, 200);
	g2.setFont(new Font("Arial", Font.PLAIN, 30));
	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	g2.drawString("NEXT", x+60 , y+60);
	
	//Draw the current Mino
	if(currentMino !=null) {
		currentMino.draw(g2);
	}
	//next mino
			nextMino.draw(g2);
			   //pause function
			   	
			//Draw Static Blocks
			
			for (int i = 0; i < staticblocks.size(); i++) {
			    staticblocks.get(i).draw(g2);
			
			}
			//draw effect
			if(effectCounterOn) {
				effectCounter++;
				
				g2.setColor(Color.red);
				for(int i = 0; i < effectY.size(); i++) {
					
					g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
					
				}
				
				if(effectCounter == 10) {
					effectCounterOn = false;
					effectCounter = 0;
					effectY.clear();
				}
			}
			
			
			   g2.setColor(Color.RED);
			   g2.setFont(g2.getFont().deriveFont(50f));
		//game over or pause
if(gameOver) {
	x = left_x + 70;
	y = top_y + 320;
	g2.drawString("Game Over", x, y);
	
}
			   
else if(KeyHandler.pausedPressed) {
	x = left_x + 70;
	y = top_y + 320;
	g2.drawString("PAUSED", x, y);
}
		
// draw game title

x = + 35;
y = top_y + 320;
g2.setColor(Color.BLUE);
g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
g2.drawString("Wesley's Tetris", x+20, y);

   }
}
