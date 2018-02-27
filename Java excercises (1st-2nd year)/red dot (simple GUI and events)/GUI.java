import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
	
	private JButton leftButton, rightButton, upButton, downButton;
	private JComboBox coloredList;
	private DisplayArea displayArea;
	private ButtonGroup shapeOptions;
	private JRadioButton circleOption, rectangleOption;
	
	public GUI() {
		super("GUI");
		
		leftButton = new JButton("Left");
		rightButton = new JButton("Right");
		upButton = new JButton("Up");
		downButton = new JButton("Down");
		displayArea = new DisplayArea();

		shapeOptions = new ButtonGroup();
		circleOption = new JRadioButton("Circle");
		rectangleOption = new JRadioButton("Rectangle");
		circleOption.setSelected(true);
		rectangleOption.setSelected(false);
				
		String[] colors = {"red","black","blue","green","yellow"};
		coloredList = new JComboBox(colors);
		
		JPanel controlBar = new JPanel();
		
		add(displayArea, BorderLayout.CENTER);

		leftButton.addActionListener(displayArea);
		rightButton.addActionListener(displayArea);
		upButton.addActionListener(displayArea);
		downButton.addActionListener(displayArea);
		coloredList.addActionListener(displayArea);
		circleOption.addActionListener(displayArea);
		rectangleOption.addActionListener(displayArea);
		
		shapeOptions.add(circleOption);
		shapeOptions.add(rectangleOption);
		
		controlBar.add(leftButton);
		controlBar.add(rightButton);
		controlBar.add(upButton);
		controlBar.add(downButton);
		controlBar.add(circleOption);
		controlBar.add(rectangleOption);
		controlBar.add(coloredList);
		add(controlBar, BorderLayout.SOUTH);
		
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}