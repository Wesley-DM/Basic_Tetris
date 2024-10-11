package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
public static boolean upPressed,downPressed,leftPressed,rightPressed, pausedPressed;
	
@Override	

public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP) {
			upPressed = true;
		}
        if(code == KeyEvent.VK_DOWN) {
			downPressed = true; 
        	
		}if(code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		
		
		}if(code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
			}
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
        if(code == KeyEvent.VK_S) {
			downPressed = true; 
        	
		}if(code == KeyEvent.VK_A) {
			leftPressed = true;
		
		
		}if(code == KeyEvent.VK_D) {
			rightPressed = true;
			}


       if(code == KeyEvent.VK_SPACE) {
    	   if(pausedPressed) {
    		   
    		   
    		   
	pausedPressed = false;
	}
    	   else {
    		   pausedPressed = true;
    	   }
		
}
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

}



