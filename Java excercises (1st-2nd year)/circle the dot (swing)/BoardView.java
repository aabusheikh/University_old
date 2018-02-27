/**
 * The class <b>BoardView</b> provides the current view of the board. It extends
 * <b>JPanel</b> and lays out a two dimensional array of <b>DotButton</b> instances.
 */
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BoardView extends JPanel {
	
	//Instance variables
    private GameModel model;
    private GameController controller;
    private DotButton[][] buttonArray;
    private JPanel panel;

	/**
     * Constructor used for initializing the board. The action listener for
     * the board's DotButton is the game controller
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
    public BoardView(GameModel gameModel, GameController gameController) {
        this.model = gameModel;
        this.controller = gameController;
        buttonArray = new DotButton[model.getSize()][model.getSize()];
        for(int i=0; i<model.getSize();i++){
            panel = new JPanel();
			panel.setLayout(new FlowLayout());
            panel.setBackground(Color.WHITE);
            if (i%2 == 0){
                panel.setBorder(BorderFactory.createMatteBorder(0,0,0,20,Color.WHITE));
            }
            else{
                panel.setBorder(BorderFactory.createMatteBorder(0,20,0,0,Color.WHITE));
            }
            for(int j=0; j<model.getSize();j++){
                int state = model.getCurrentStatus(i,j);
                buttonArray[i][j] = new DotButton(i,j,state);
                buttonArray[i][j].setName(i+","+j);
                buttonArray[i][j].setBorderPainted(false);
                buttonArray[i][j].addActionListener(controller);
                panel.add(buttonArray[i][j]);
            }
            this.setLayout(new GridLayout(model.getSize(),1));
            this.add(panel,BorderLayout.CENTER);
        }
    }

 	/**
	 * update the status of the board's DotButton instances based on the current game model
	 */
    public void update(){
        for(int i=0; i<model.getSize();i++){
            for(int j=0; j<model.getSize();j++){
                buttonArray[i][j].setType(model.getCurrentStatus(i,j));
            }
        } 
        panel.repaint();
	}

}
