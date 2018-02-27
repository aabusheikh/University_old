
/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 */
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameView extends JFrame {

    private GameController controller;
    private JButton reset;
    private JButton quit;
    private JPanel panel;
    private BoardView view;
 
    /**
     * Constructor used for initializing the Frame
     * 
     * @param model
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
    public GameView(GameModel model, GameController gameController) {
        super("GameView");
        this.controller = gameController;
        view = new BoardView(model,controller);
        panel = new JPanel();
        reset = new JButton("Reset");
        reset.setName("Reset");
        quit = new JButton("Quit");
        quit.setName("Clear");

        // Define the main frame
        this.add(view,BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        view.setBackground(Color.WHITE);

        //Adding Actions
        reset.addActionListener(controller);
        quit.addActionListener(controller);

        //Add everything to the view
        panel.add(reset);
        panel.add(quit);
        add(panel,BorderLayout.SOUTH);
		pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

   /**
     * Getter method for the attribute board.
     * 
     * @return a reference to the BoardView instance
     */
    public BoardView getBoardView(){
        return view;
    }

}
