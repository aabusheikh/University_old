package sfCalc; 

import javafx.scene.control.TextField;

/**
 * This class contains the code used to calculate the result of an arithmetic calculation with the right number of significant figures
 * It uses classes: {@link Calculator} and {@link SignedDecimal} to parse and process input
 * 
 * @author Ahmad Abu Sheikh
 * @version 2.0
 */
public class SFCalc {
	
	/**
	 * Uses the {@link Calculator} and {@link SignedDecimal} classes to parse the input and calculate the result.
	 * Then passes the result as a string to the output {@link TextField}.
	 * 
	 * Due to the nature of {@link SignedDecimal}.num, the result is always in the form "#.#", 
	 * i.e. there's always at-least one digit to the right side of the decimal point.
	 * 
	 * @param in input String
	 * @return a String representation of the result
	 */
	public static String calculate(String in) {		
		return Calculator.calculate(in).toString();
	}		
}
