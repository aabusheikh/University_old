import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayArea extends JPanel implements ActionListener {
	
	private Point center;
	
	private int x,y;
	private String color;
	private String shape;
	
	public DisplayArea() {
		setSize(500,500);
		x=250;
		y=250;
		center = new Point(x,y);
		color = "red";
		shape = "Circle";
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		switch(color) {
			case "red": g.setColor(Color.RED);
						break;
			case "black": g.setColor(Color.BLACK);
						break;
			case "blue": g.setColor(Color.BLUE);
						break;
			case "green": g.setColor(Color.GREEN);
						break;
			case "yellow": g.setColor(Color.YELLOW);
						break;
		}
		
		switch(shape) {
			case "Circle": g.fillOval(x-5, y-5, 10, 10);
						   break;
			case "Rectangle": g.fillRect(x-5, y-5, 10, 10);
							  break;
		}
		
	}
	
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() instanceof JButton) {
			if (e.getActionCommand().equals("Left")) {
				if (x-10>=0) x-=10;
			} else if (e.getActionCommand().equals("Right")) {
				if (x+10<=500) x+=10;
			}  else if (e.getActionCommand().equals("Up")) {
				if (y-10>=0) y-=10;
			}  else if (e.getActionCommand().equals("Down")) {
				if (y+10<=500) y+=10;
			}
		} else if (e.getSource() instanceof JComboBox) {
			JComboBox cb = (JComboBox) e.getSource();
			color = cb.getSelectedItem().toString();
		} else if (e.getSource() instanceof JRadioButton) {
			shape = e.getActionCommand();
		}
		repaint();
	}
}