
/**
 * In the application <b>Circle the dot</b>, a <b>DotButton</b> is a specialized type of
 * <b>JButton</b> that represents a dot in the game. It uses different icons to
 * visually reflect its state: a blue icon if the blue dot is currently on this location
 * an orange icon is the dot has been selected and a grey icon otherwise.
 * 
 * The icon images are stored in a subdirectory ``data''. They are:
 * data/ball-0.png => grey icon
 * data/ball-1.png => orange icon
 * data/ball-2.png => blue icon
 */
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class DotButton extends JButton {

	//Instance variables
    private int row;
    private int column;
    
    @SuppressWarnings("unused")
	private int type;
    
    private final Dimension dimension = new Dimension(40,40);
    private Icon icon0 = new ImageIcon("data/ball-0.png");
    private Icon icon1 = new ImageIcon("data/ball-1.png");
    private Icon icon2 = new ImageIcon("data/ball-2.png");

    /**
     * Constructor used for initializing a cell of a specified type.
     * 
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param type
     *            specifies the type of this cell
     */
    public DotButton(int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;
        this.setPreferredSize(dimension);
        this.setBackground(Color.WHITE);
        setType(type);
    }

    /**
     * Changes the cell type of this cell. The image is updated accordingly.
     * 
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;

        switch (type){
	        case 0:
	        this.setIcon(icon0);
	        break;
	
	        case 1:
	        this.setIcon(icon1);
	        break;
	
	        case 2:
	        this.setIcon(icon2);
	        break;
        }
    }

 
    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */
    public int getColumn() {
        return column;
    }
    
}
