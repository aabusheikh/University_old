package ccValid;

/**
 * This class contains the code used to verify a credit card number according to the Luhn Algorithm
 *
 * @version 2.0
 */
public class CCValid {			

	/**
	 * The main method, which uses the helper methods to verify the card number and get information like the institution and category.
	 * 
	 * @param in the 16-digit input
	 * @return An array of strings representing the results
	 */
	public static String[] validate(String in) {					
		boolean valid=false; //initial assumption is that the card is invalid																				
		
		//If the card number input passes the length and Luhn Algorithm checks then it is valid
		if (checkLength(in) && checkAlg(in)) { 	
			valid = true;
		}
		
		//if the card number is valid, use the helper methods to get its information
		if (valid) {			
			String[] ret = {getOut(in), "Yes", getIns(in), getCat(in)};
			return ret;		
		}		
		//otherwise reset all fields to "" (empty) except for validR which is set to "No"
		else {				
			String[] ret = {"", "No", "", ""};
			return ret;	
		}
	}
	
	/**
	 * Format the credit card number to look better
	 * 
	 * @param s the input provided by the user through the UI
	 * @return the credit card number formatted as such: 1111 2222 3333 4444
	 */
	private static String getOut(String s) {
		String out="";
		for (int i=0; i<s.length(); i++) {
			out += s.charAt(i);
			if (i==3||i==7||i==11) {
				out += " ";
			}
		}
		return out;
	}
	
	/**
	 * check if the length of the input number is 16
	 * 
	 * @param s the input provided by the user through the UI
	 * @return true if the length is 16, false otherwise
	 */
	private static boolean checkLength(String s) {
		if (s.length() >= 13 && s.length() <= 16) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the category of the card depending on the first digit:
	 * 
	 * 0 - Unknown
	 * 1,2 - Airline
	 * 3 - Travel and Entertainment
	 * 4,5 - Banking and Financial
	 * 6 - Merchandising
	 * 7 - Petroleum
	 * 8 - Telecommunications
	 * 9 - National Assignment
	 * 
	 * @param s the input provided by the user through the UI
	 * @return the category, or "Unknown" if none is found
	 */
	private static String getCat(String s) {
		char c = s.charAt(0);
		
		if (c != '0') { //for efficiency, if the first number is 0 skip the subsequent checks and return unknown
			if (c == '1' || c == '2') {
				return "Airline";
			}
			else if (c == '3') {
				return "Travel and Entertainment";
			}
			else if (c == '4' || c == '5') {
				return "Banking and Financial";
			}
			else if (c == '6') {
				return "Merchandising";
			}
			else if (c == '7') {
				return "Petroleum";
			}
			else if (c == '8') {
				return "Telecommunications";
			}
			else if (c == '9') {
				return "National Assignment";
			}			
		}		
		return "Unknown";
	}
	
	/**
	 * Get the institution of the card depending on the first 6 digits:
	 * 
	 * 4xxxxx - Visa
	 * 51xxxx, 55xxxx - MasterCard
	 * 6011xx, 644xxx, 65xxxx - Discover
	 * 34xxxx, 37xxxx - Amex
	 * Other - Unknown
	 * 
	 * @param s the input provided by the user through the UI
	 * @return the Institution's name, or "Unknown" if it's an unknown one
	 */
	private static String getIns(String s) {
		String c = s.substring(0, 5);
		
		if (c.startsWith("4")) {
			return "Visa";
		}
		else if (c.startsWith("51") || c.startsWith("55")) {
			return "MasterCard";
		}
		else if (c.startsWith("6011") || c.startsWith("644") || c.startsWith("65")) {
			return "Discover";
		}
		else if (c.startsWith("34") || c.startsWith("37")) {
			return "American Express";
		}
		return "Unknown";	
	}
	
	/**
	 * Checks if the number passes the Luhn algorithm check:
	 * 
	 * 1- Reverse the order of the digits in the number.
	 * 2- Taking the second, fourth ... and every other even digit in the reversed digits:
	 *     - Multiply each digit by two and sum the digits if the answer is greater than nine to form partial sums for the even digits
	 *     - Sum the partial sums of the even digits to form s1
	 * 3- Take the first, third, ... and every other odd digit in the reversed digits and sum them to form the partial sum s2
	 * 4- If s1 + s2 is divisible by 10 then the original number is in the form of a valid credit card number as verified by the Luhn test.
	 * 
	 * Note: since the actual index of the first digit of the reversed number will be 0, and not 1 s1 will take odd indices and s2 will take even.
	 * 
	 * @param s the input provided by the user through the UI
	 * @return true if the number passes the algorithm check, false otherwise
	 */
	private static boolean checkAlg(String s) {	
		String reverse="";
		int s1=0, s2=0, sum=0;
		
		for (int i=s.length()-1; i>=0; i--) {
			reverse += s.charAt(i);			
		}
		
		for (int i=0; i<reverse.length(); i++) {			
			Character c = reverse.charAt(i);
			int n = Character.getNumericValue(c);			
			if (n != -1) {
				if (i%2 == 0) {
					s2 += n;
				}
				else {		
					n=n*2;
					if (Integer.toString(n).length() == 2) {
						s1 += Integer.parseInt(Integer.toString(n).substring(0, 1)) + Integer.parseInt(Integer.toString(n).substring(1));
					}
					else {
						s1 += n;
					}
				}
			}			
		}	
		
		sum = s1+s2;				
		if (sum%10 == 0) {
			return true;
		}
		return false;
	}		
}


