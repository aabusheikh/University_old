
/**
 * The class <b>GameController</b> is the controller of the game. It implements 
 * the interface ActionListener to be called back when the player makes a move. It computes
 * the next step of the game, and then updates model and view.
 */

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {

	//Instance variables
    private int size;
    private GameModel model; 
    private GameView view;
    private Point[] targets, blocked; 
    private boolean gameWon, gameLost;

     /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) {
        this.size = size;
        model = new GameModel(size);
        view = new GameView(model,this);
        
        // get the targets
        targets = new Point[(4*size)-4];
        
        Point[] top = getTargets(0,0);
        Point[] bot = getTargets(0,size-1); 
        Point[] left = getTargets(1,0);
        Point[] right = getTargets(1,size-1);
        
        targets = joinArrays(joinArrays(top,bot),joinArrays(left,right));
        
        //get blocked
        blocked = new Point[(size*size)/10];
        
        int c=0;
        for (int i=0; i<size; i++) {
        	for (int j=0; j<size; j++) {
        		if (model.getCurrentStatus(i, j) == 1) {
        			blocked[c] = new Point(i,j); 
        			c++;
        		}
        	}
        }
        
        blocked = stripArray(blocked);
        
        gameWon = false;
        gameLost = false;
    }

    /**
     * Starts the game
     */
    public void start(){
        view.setVisible(true);
    }

    /**
     * resets the game
     */
    public void reset(){
        view.setVisible(false);
        GameController game = new GameController(size);
        game.start();
    }

    /**
     * Callback used when the user clicks a button or one of the dots. 
     * Implements the logic of the game
     *
     * @param e
     *            the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if ((((JButton) e.getSource()).getName()).equals("Reset")){
            reset();
        } else if ((((JButton) e.getSource()).getName()).equals("Clear")){
            System.exit(0);
        } else {
        	if (e.getSource() instanceof JButton) {
				String button = ((JButton) e.getSource()).getName();

				int n = button.indexOf(',');
				int i = Integer.parseInt(button.substring(0,n));
				int j = Integer.parseInt(button.substring(n+1));

            	if (model.getCurrentStatus(i, j) == 0) {
            		model.select(i,j);
            		Point[] temp = {new Point(i,j)};
            		blocked = stripArray(joinArrays(blocked, temp));

            		Point[] path = breadthFirstSearch(model.getCurrentDot(), targets, blocked);
            		
            		try {
            			Point next = path[1];   
            			
            			model.setCurrentDot(next.getX(), next.getY());
                		
                		for (int k=0; k<targets.length; k++) {
                			if (model.getCurrentDot().equals(targets[k])) {
                				gameLost = true;
                			}
                		}
            		} catch (NullPointerException exception) {
            			gameWon = true;
            		}
            	}
        	}
            view.getBoardView().update(); 
            
            if (gameWon) {
            	int n = JOptionPane.showOptionDialog(null, "Congratulations, you won in "+model.getNumberOfSteps()+" steps! \n Would you like to play again?", 
            			"Won", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Quit", "Play Again"}, "Play Again");
            	if (n == 0) {
            		System.exit(0);
            	} else {
            		reset();
            	}
            }
            
            if (gameLost) {
            	int n = JOptionPane.showOptionDialog(null, "You lost! Would you like to play again?", 
						"Lost", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Quit", "Play Again"}, "Play Again");
            	if (n == 0) {
            		System.exit(0);
            	} else {
            		reset();
            	}
            }
        }
    }
    
    /**
     * Java implementation of the provided Breadth-first-search algorithm
     * which finds the shortest path for the dot to reach the edge of the
     * board.
     * 
     * @param start
     * 				the current location of the blue dot, also the location where path-making starts
     * @param targets
     * 				the edges of the board, also the targets for path making
     * @param blocked
     * 				pre-selected, or user selected locations that path making avoids
     * 
     * @return Point[] path -> an array of points/locations representing the shortest 
     * 						   path from the blue dot's current position to a target
     */
    private Point[] breadthFirstSearch (Point start, Point[] targets, Point[] blocked) {
    	LinkedList<Point[]> queue = new LinkedList<Point[]>();
    	Point[] startA = {start};
    	queue.addLast(startA);

    	while (!(queue.isEmpty())) {
    		
    		Point[] q = queue.removeFirst();
    		Point c = q[q.length-1];
    		Point[] p = neighboringDots(c);
  
    		
    		for (int i=0; i<p.length; i++) {
    			boolean pBlocked = false;
    			for (int j=0; j<blocked.length; j++) {
    				if (p[i].equals(blocked[j])) {
    					pBlocked = true;
    					break;
    				}
    			}
    			
    			if (!pBlocked) {
    				boolean pTarget = false;
    				for (int k=0; k<targets.length; k++) {
    					if (p[i].equals(targets[k])) {
    						pTarget = true;
    						break;
    					}
    				}
    				Point[] pA = {p[i]};
    				Point[] path = joinArrays(q,pA);
    				if (pTarget) {
						return path;
					} else {
						queue.addLast(path);
						blocked = joinArrays(blocked,pA);
					}
    			}
    		}
    	}
    	
    	return null;
    }
    
    /** 
     * Find the neighboring/surrounding dots
     * 
     * @param dot 
     * 			the given location
     * @return neighbors -> an array of Points representing all surrounding dots
     */
    private Point[] neighboringDots(Point dot) {
    	Point[] neighbors = new Point[6];
    	
    	if (dot.getX() %2 == 0) {
    		neighbors[0] = new Point(dot.getX()-1,dot.getY()-1);
	    	neighbors[1] = new Point(dot.getX()-1,dot.getY());
	    	neighbors[2] = new Point(dot.getX(),dot.getY()+1);
	    	neighbors[3] = new Point(dot.getX()+1,dot.getY());
	    	neighbors[4] = new Point(dot.getX()+1,dot.getY()-1);
	    	neighbors[5] = new Point(dot.getX(),dot.getY()-1);
    	} else {
    		neighbors[0] = new Point(dot.getX()-1,dot.getY());
	    	neighbors[1] = new Point(dot.getX()-1,dot.getY()+1);
	    	neighbors[2] = new Point(dot.getX(),dot.getY()+1);
	    	neighbors[3] = new Point(dot.getX()+1,dot.getY()+1);
	    	neighbors[4] = new Point(dot.getX()+1,dot.getY());
	    	neighbors[5] = new Point(dot.getX(),dot.getY()-1);
    	}
    	
    	boolean invalid;
    	do {
    		invalid = false;
	    	for (int i=0;i<neighbors.length; i++) {
	    		if (neighbors[i].getX() < 0 || neighbors[i].getX() > size-1 ||
	    		    neighbors[i].getY() < 0 || neighbors[i].getY() > size-1) {
	    			
	    			neighbors[i] = neighbors[neighbors.length-1];
	    			neighbors[neighbors.length-1] = null;
	    			neighbors = stripArray(neighbors);
	    			
	    			invalid = true;
	    		}
	    	}
    	} while (invalid);
    	
    	return neighbors;
    }
    
    /**
     * creates a new array c from appending b to a
     * 
     * @param a 
     * 			first array
     * @param b
     * 			second array, appended to the first array
     * @return c -> a new array that is a+b
     */
    @SuppressWarnings("all")
    private Point[] joinArrays(Point[] a, Point[] b) {
    	Point[] c = new Point[a.length+b.length];
    	
    	int i=0;
		for (i=i; i<a.length; i++) {
			c[i] = a[i];
		}
		for (i=i; i<c.length; i++) {
			c[i] = b[i-a.length];
		}
		
    	return c;
    }
    
    /** 
     * strip trailing "nulls" from an array of Points
     * Example: {x,y,z,null,null} --> {x,y,z}
     * 
     * @param a 
     * 			given array
     * @return b -> the given array stripped of trailing nulls
     */
    private Point[] stripArray(Point[] a) {
    	int n=0;
    	for (int i=a.length-1; i>=0; i--) {
    		if (a[i] == null) n++;
    	}
    	
    	Point[] b = new Point[a.length-n];
    	
    	for (int i=0; i<b.length; i++) b[i] = a[i];
    	
    	return b;
    }
    
    /**
     * helper method to determine the targets of one edge (top, bottom, left, right). 
     * i.e. the locations on the 4 edges which are not pre-selected or blocked.
     * 
     * @param m 
     * 			used to adjust algorithm for horizontal/vertical target lists
     * @param n
     * 			used to adjust algorithm for top/bottom and left/right lists
     * @return stripped array of targets for one edge
     */
    private Point[] getTargets(int m, int n) {
    	Point[] targets = new Point[size-m];
    	int c=0;
    	
    	if (m == 0) {
	    	for (int i=0; i<targets.length; i++) {
	        	if (model.getCurrentStatus(n,i) == 0) {
	        		targets[c] = new Point(n,i);
	        		c++;
	        	}
	        }
    	} else {
    		for (int i=1; i<targets.length; i++) {
	        	if (model.getCurrentStatus(i,n) == 0) {
	        		targets[c] = new Point(i,n);
	        		c++;
	        	}
	        }
    	}
    	
    	targets = stripArray(targets);
    	
    	return targets;
    }
 
}
