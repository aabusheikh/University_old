import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the followiung information:
 * - the current location of the blue dot
 * - the state of all the dots on the board (available, selected or 
 *  occupied by the blue dot
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 */
public class GameModel {

    /**
     * predefined values to capture the state of a point
     */
    public static final int AVAILABLE  = 0;
    public static final int SELECTED  = 1;
    public static final int DOT   = 2;

    //Instance variables
    private int size;
    private int steps;
    private Point blueDot = new Point(0,0);
    private int[][] intArray;
    private Random rand = new Random();

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param size
     *            the size of the board
     */
    public GameModel(int size) {
        this.size = size;
        reset();
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . The blue dot is positioned as per instructions, and each 
     * dot of the board is either AVAILABLE, or SELECTED (with
     * a probability 1/INITIAL_PROBA). The number of steps is reset.
     */
    public void reset(){

        steps = 0;
        intArray = new int[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                intArray[i][j] = 0;
            }
        }
        
        //Set the initial position of Blue Dot
        if (size%2 == 0){
            int x = rand.nextInt(2) + (size/2)-1;
            int y = rand.nextInt(2) + (size/2)-1;
            setCurrentDot(x,y);
        }
        if (size%2 != 0){
            int x = rand.nextInt(3) + (size/2)-1;
            int y = rand.nextInt(3) + (size/2)-1;
            setCurrentDot(x,y);
        }

        //Randomly selecting yellow dots
        for (int k = 0; k<(size*size)/10;k++){
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (intArray[x][y] == 2){
                intArray[x+1][y-1] = 1;
            }
            else{
                intArray[x][y] = 1;
            }
        }  

    }


    /**
     * Getter <b>class</b> method for the size of the game
     * 
     * @return the value of the attribute sizeOfGame
     */   
    public int getSize(){
        return size;
    }

    /**
     * returns the current status (AVAILABLE, SELECTED or DOT) of a given dot in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public int getCurrentStatus(int i, int j){
        return intArray[i][j];
    }

    /**
     * Sets the status of the dot at coordinate (i,j) to SELECTED, and 
     * increases the number of steps by one
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void select(int i, int j){
        intArray[i][j] = 1;
        steps++;
    }

    /**
     * Puts the blue dot at coordinate (i,j). Clears the previous location 
     * of the blue dot. If the i coordinate is "-1", it means that the blue 
     * dot exits the board (the player lost)
     *
     * @param i
     *            the new x coordinate of the blue dot
     * @param j
     *            the new y coordinate of the blue dot
     */   
    public void setCurrentDot(int i, int j){
		for(int k=0; k<size;k++){
            for(int h=0;h<size;h++){
                if (intArray[k][h] == 2){
                    intArray[k][h] = 0;
                }
            }
        }
        intArray[i][j] = 2;
        blueDot.reset(i,j);
    }

    /**
     * Getter method for the current blue dot
     * 
     * @return the location of the curent blue dot
     */   
    public Point getCurrentDot(){ 
        return blueDot;  
    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return steps;  
    }
}
