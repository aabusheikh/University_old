package sfCalc; 

import java.math.BigDecimal;

/**
 * Signed Decimal class
 * 
 * @version 1.0
 */
public class SignedDecimal {

	/**
	 * The signed decimal number value, represented by a {@link BigDecimal}
	 */
    public final BigDecimal num;
    
    /**
     * The significant digits of the number
     */
    public final int sigFigs;

    /**
     * Constructor that also creates a {@link BigDecimal} from a String then assigns the created 
     * {@link BigDecimal} to the num field of this instance of SignedDecimal after rounding off using the method implemented below  
     * 
     * @param str String input
     * @param sigFigs Significant Digits
     */
    public SignedDecimal(String str, int sigFigs){
        num = SignedDecimal.roundToSignificantFigures(new BigDecimal(str), sigFigs);
        this.sigFigs = sigFigs;
    }

    /**
     * Constructor that also creates a {@link BigDecimal} from a Double then assigns the created 
     * {@link BigDecimal} to the num field of this instance of SignedDecimal after rounding off using the method implemented below
     * 
     * @param doub Double input
     * @param sigFigs Significant Digits
     */
    public SignedDecimal(double doub, int sigFigs){
        num = new BigDecimal(SignedDecimal.roundToSignificantFigures(doub, sigFigs));
        this.sigFigs = sigFigs;
    }
    
    /**
     * Constructor that also assigns a given {@link BigDecimal} to the num field of this instance of SignedDecimal
     * after rounding off using the method implemented below
     * 
     * @param bd given {@link BigDecimal}
     * @param sigFigs Significant Digits
     */
    public SignedDecimal(BigDecimal bd, int sigFigs){
        num = SignedDecimal.roundToSignificantFigures(bd, sigFigs);
        this.sigFigs = sigFigs;
    }
    
    /**
     * Multiplies this instance of SignedDecimal with the given other instance using {@link BigDecimal}.multiply()
     * 
     * @param other other instance of SignedDecimal to be multiplied by the one that called the method
     * @return a new SignedDecimal object that has: 
     * 										num = the multiple of this.num and other.num
     * 										sigFigs = the smaller value of this.sigFigs and other.sigFigs
     */
    public SignedDecimal mul(SignedDecimal other) {
        return new SignedDecimal(num.multiply(other.num), Math.min(other.sigFigs, this.sigFigs));
    }
    
    /**
     * Multiplies this instance of SignedDecimal with an integer. 
     * Creates a {@link BigDecimal}, "bd", to use for the multiplication that has the same value as the integer i.
     * 
     * @param i given integer
     * @return a new SignedDecimal with num=this.num*bd, sigFigs=minimum of (this.sigFigs, significant figures of bd)
     */
    public SignedDecimal mul(int i) {
        BigDecimal bd = new BigDecimal(i);
        return new SignedDecimal(num.multiply(bd), Math.min(this.sigFigs, getSignificantDigits(bd)));
    }
    
    /**
     * Divides this by given other.
     * 
     * @param other given other SignedDecimal
     * @return a new SignedDecimal whose num=this.num/other.num, sigFigs=minimum of (this.sigFigs, other.sigFigs)
     */
    public SignedDecimal div(SignedDecimal other) {
        return new SignedDecimal(num.divide(other.num), Math.min(other.sigFigs, this.sigFigs));
    }
    
    /**
     * Adds two SignedDecimal numbers
     * 
     * @param other SignedDecimal to add to this
     * @return a new SignedDecimal whose num=this.num+other.num, sigFigs=minimum of (this.sigFigs, other.sigFigs)
     */
    public SignedDecimal add(SignedDecimal other) {
        return new SignedDecimal(num.add(other.num), Math.min(other.sigFigs, this.sigFigs));
    }
    
    /**
     * Subtract other from this SignedDecimal
     * 
     * @param other given SignedDecimal to subtract from this instance of SignedDecimal
     * @return a new SignedDecimal whose num=this.num-other.num, sigFigs=minimum of (this.sigFigs, other.sigFigs)
     */
    public SignedDecimal sub(SignedDecimal other) {
        return new SignedDecimal(num.subtract(other.num), Math.min(other.sigFigs, this.sigFigs));
    }

    @Override
    /**
     * Provides a String representation of the SignedDecimal number
     * 
     * @return a String object representing this instance of SignedDecimal
     */
    public String toString(){
        return num.toString();
    }
    
    /**
     * Counts the significant digits in a String representation of a number.
     * Uses the method getSignificantDigits() implemented below by creating a temporary {@link BigDecimal} using the String input
     * 
     * @param s String input
     * @return number of significant digits
     */
    public static int significantDigits(String s) {
        //counts trailing zeroes
        if(s.contains(".")){
            int zeroes = 0;
            for(int i = s.length()-1; i >= 0; i--){
                if(s.charAt(i) != '0'){
                    break;
                }
                zeroes++;
            }
            //will this be tail call optimized?
            return zeroes + getSignificantDigits(new BigDecimal(s));
        } else {
            return getSignificantDigits(new BigDecimal(s));
        }
    }
    
    /**
     * Counts the number of significant digits in a {@link BigDecimal} representation of a number.
     * 
     * @param input given {@link BigDecimal} input
     * @return number of significant digits
     */
    public static int getSignificantDigits(BigDecimal input) {
            input = input.stripTrailingZeros();
            return input.scale() < 0
                ? input.precision() - input.scale()
                : input.precision(); 
    }

    /**
     * Rounds the number represented by a {@link BigDecimal} to the number of significant digits given by sigFigs
     * 
     * @param bd given {@link BigDecimal} input
     * @param sigFigs significant digits
     * @return a {@link BigDecimal} number rounded to the given number of significant figures.
     */
    public static BigDecimal roundToSignificantFigures(BigDecimal bd, int sigFigs) {

        if(bd.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        final double d = Math.ceil(Math.log10(bd.compareTo(BigDecimal.ZERO) < 0 ? bd.negate().doubleValue(): bd.doubleValue()));
        final int power = sigFigs - (int) d;

        final double magnitude = Math.pow(10, power);
        final long shifted = Math.round(bd.doubleValue() * magnitude);

        return BigDecimal.valueOf(shifted/magnitude);
    }
    
    /**
     * Rounds a double number to the given number of significant digits.
     * 
     * @param num given double input
     * @param sigFigs significant digits
     * @return a double rounded to the given number of significant digits
     */
    public static double roundToSignificantFigures(double num, int sigFigs) {

        if(num == 0) {
            return 0;
        }

        final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
        final int power = sigFigs - (int) d;

        final double magnitude = Math.pow(10, power);
        final long shifted = Math.round(num*magnitude);

        return shifted/magnitude;
    }
}