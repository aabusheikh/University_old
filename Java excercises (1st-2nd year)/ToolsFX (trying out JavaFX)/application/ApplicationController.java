/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
 */

package application; 

import java.net.URL;
import java.util.ResourceBundle;

import ccValid.CCValid;
import sfCalc.SFCalc;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The Controller class deals with ActionEvents of the UI like key presses and button clicks.
 * It facilitates the interaction between the user and the application by handling receiving 
 * and handling input and sending feedback and results back to the UI.
 * 
 * @version 0.6
 */
public class ApplicationController implements Initializable{
	
	//All ---------
	@FXML
	/**
	 * The root {@link BorderPane}
	 */
	private BorderPane root;
	
	@FXML 
	/**
	 * the {@link Text} of the version number in the tool-bar
	 */
	private Text verT;
	
	//Home ----------
	@FXML 
	/**
	 * Animated {@link Circle} in the home tab
	 */
	private Circle circle;
	
	@FXML 
	/**
	 * the {@link Text} of the version number that triggers the animated circle
	 */
	private Text ver;
	
	/**
	 * boolean to toggle animation between fade in/fade out
	 */
	private boolean animToggle=true;
	
	//CCValid ----------
	@FXML 
	/**
	 * Credit card number output {@link TextField}
	 */
	private TextField outR;
	
	@FXML 
	/**
	 * Valid output {@link TextField}
	 */
	private TextField validR;
	
	@FXML 
	/**
	 * Institution output {@link TextField}
	 */
	private TextField insR;
	
	@FXML 
	/**
	 * Category output {@link TextField}
	 */
	private TextField catR;	
	
	@FXML 
	/**
	 * Invalid input warning {@link Label}
	 */
	private Label invalid;
	
	@FXML 
	/**
	 * input {@link TextField}
	 */
	private TextField input;
	
	//SFCalc ----------
	@FXML 
	/**
	 * input {@link TextField}
	 */
	private TextArea sfInput;
	
	@FXML 
	/**
	 * invalid input warning {@link Label}
	 */
	private Label sfInvalid;
	
	@FXML 
	/**
	 * Result output {@link TextField}
	 */
	private TextField sfResult;
	
	
	//---------- Methods ----------
	
	//All ----------
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		String v = Main.getVer();
		ver.setText(v);
		verT.setText(v);
	}
	
	/**
	 * Used to check if the ESC key was pressed anywhere in the application
	 * 
	 * @param key the {@link KeyEvent} resulting from a key press
	 */
	public void keyCheck(KeyEvent key) {
		if (key.getCode() == KeyCode.ESCAPE) {
			System.exit(0); //terminate
		}
	}
	
	//Home ----------
	
	/**
	 * Creates and plays a very simple animation. 
	 * The animation was created to test JavaFX simple animations ({@link FadeTransition} and {@link ScaleTransition})
	 */
	public void circleAnim() {
		circle.setVisible(true); //the circle is initially invisible
		
		//create the fade animation
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), circle);
		fadeTransition.setCycleCount(1); 
		
		//create the expand/shrink animation
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), circle);
		scaleTransition.setCycleCount(1);
				
		if (animToggle) { //if the toggle is at true (initial value)
			//set the animation to fade in from 0 (invisible) to 1 (regular)
	        fadeTransition.setFromValue(0.0f);
	        fadeTransition.setToValue(1.0f);
	        
	        //set the animation to expand from 0x (nothing) to 1x (regular size)
	        scaleTransition.setFromX(0);
	        scaleTransition.setFromY(0);
	        scaleTransition.setToX(1);
	        scaleTransition.setToY(1); 
		}
		else {
			//set the animation to fade out from 1 (regular) to 0 (invisible) 
			fadeTransition.setFromValue(1.0f);
	        fadeTransition.setToValue(0.0f);	
	        
	        //set the animation to expand from 1x (regular size) to 0x (nothing) 
	        scaleTransition.setFromX(1);
	        scaleTransition.setFromY(1);
	        scaleTransition.setToX(0);
	        scaleTransition.setToY(0); 
		}
		
		//play the two animation simultaneously
        scaleTransition.play();
        fadeTransition.play();  
                
        animToggle = !animToggle; //invert the toggle (true --> false or false --> true)
	}
	
	
	//CCValid ----------
	
	/**
	 * Checks if the input is valid (13-16 digits and numbers only) and uses the class {@link CCValid} to validate the input
	 */
	public void validate() {		
		//if input is 13-16 characters long and contains only numbers (0-9)
		if ((input.getText().length() >= 13 && input.getText().length() <= 16) && input.getText().matches("[0-9]+")) { 			
    		String[] result = CCValid.validate(input.getText()); //validate using CCValid class    		
    		outR.setText(result[0]);
    		validR.setText(result[1]);
    		insR.setText(result[2]);
    		catR.setText(result[3]);
    		invalid.setVisible(false); //hide the invalid input warning
    	}  
    	else {    		
    		String[] result = CCValid.validate(input.getText());    		
    		outR.setText(result[0]);
    		validR.setText(result[1]);
    		insR.setText(result[2]);
    		catR.setText(result[3]);
    		invalid.setVisible(true); //show the invalid input warning
    	}    	
	}
	
	/**
	 * Used to check if the ENTER key was pressed in the CCValid tab
	 * 
	 * @param key the {@link KeyEvent} resulting from a key press
	 */
	public void internalKeyCheck(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER) {
			validate();
		}
	}
	
	//SFCalc ----------
	
	/**
	 * Uses the {@link SFCalc} class to process the input and get the result
	 */
	public void calculate() {
		try {	
			sfResult.setText(SFCalc.calculate(sfInput.getText()));
			sfInvalid.setVisible(false);			
		}
		catch (Exception e) {
			sfResult.setText("");
			sfInvalid.setVisible(true);
		}		
	}
	
	/**
	 * Used to check if the ENTER key was pressed in the SFCalc tab
	 * 
	 * @param key the {@link KeyEvent} resulting from a key press
	 */
	public void sfInternalKeyCheck(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER) {
			calculate();
		}
	}	
}
