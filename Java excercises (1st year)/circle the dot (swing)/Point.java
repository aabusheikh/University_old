
/**
 * The class <b>Point</b> is a simple helper class that stares a 2 dimentional element on a grid
 */
//TODO removed imports

public class Point {

    private int x;
    private int y;

    /**
     * Constructor 
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;

    }

    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return x;
    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return y;
    }
    
    /**
     * Setter for x and y.
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public void reset(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /** //TODO added javadoc comment 
     * determine if two Points are equal
     * 
     * @param p 
     * 			the Point to be compared with the calling instance
     * @return true if p is not null and the two points are equal, false otherwise
     */
    public boolean equals(Point p) {
    	return (p != null && this.x == p.x && this.y == p.y);
    }
 }
