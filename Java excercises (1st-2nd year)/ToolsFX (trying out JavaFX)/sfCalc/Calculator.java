package sfCalc; 

import java.math.BigDecimal;

import org.codehaus.jparsec.OperatorTable;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Binary;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Unary;

/**
 * Parser for arithmetic expressions
 * 
 * @author kyran.park.adams@gmail.com ---
 * @author Javadoc Comments: Ahmad Abu Sheikh
 * @version 1.0
 */
public class Calculator {

	/**
	 * Definitions for arithmetic operators using {@link SignedDecimal} methods
	 */
	enum BinaryOperator implements Binary<SignedDecimal> {
	    PLUS {
	    	public SignedDecimal map(SignedDecimal a, SignedDecimal b) {
	    		return a.add(b);
	    	}
	    },
	    MINUS {
	    	public SignedDecimal map(SignedDecimal a, SignedDecimal b) {
	    		return a.sub(b);
	    	}
	    },
	    MUL {
	    	public SignedDecimal map(SignedDecimal a, SignedDecimal b) {
	    		return a.mul(b);
	    	}
	    },
	    DIV {
	    	public SignedDecimal map(SignedDecimal a, SignedDecimal b) {
	    		return a.div(b);
	    	}
	    }
	}

	/**
	 * Definition for arithmetic "negate" operator using {@link SignedDecimal} method to multiply by -1
	 */
	enum UnaryOperator implements Unary<SignedDecimal> {
	    NEG {
	    	public SignedDecimal map(SignedDecimal n) {
	    		return n.mul(-1);
	    	}
	    }
	}
	
	static final Parser<SignedDecimal> NUMBER = Terminals.DecimalLiteral.PARSER.map(new Map<String, SignedDecimal>() {
		public SignedDecimal map(String s) {
			return new SignedDecimal(new BigDecimal(s), SignedDecimal.significantDigits(s));
	    }
	});
	
	private static final Terminals OPERATORS = Terminals.operators("+", "-", "*", "/", "(", ")");
	
	static final Parser<Void> IGNORED = Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT, Scanners.WHITESPACES).skipMany();
	
	static final Parser<?> TOKENIZER = Parsers.or(Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());
	
	static Parser<?> term(String... names) {
		return OPERATORS.token(names);
	}
		
	static final Parser<BinaryOperator> WHITESPACE_MUL =  term("+", "-", "*", "/").not().retn(BinaryOperator.MUL);
	
	static <T> Parser<T> op(String name, T value) {
		return term(name).retn(value);
	}
	
	static Parser<SignedDecimal> calculator(Parser<SignedDecimal> atom) {
		Parser.Reference<SignedDecimal> ref = Parser.newReference();
		    
		Parser<SignedDecimal> unit = ref.lazy().between(term("("), term(")")).or(atom);
		    
		Parser<SignedDecimal> parser = new OperatorTable<SignedDecimal>()
		    .infixl(op("+", BinaryOperator.PLUS), 10)
		    .infixl(op("-", BinaryOperator.MINUS), 10)
		    .infixl(op("*", BinaryOperator.MUL).or(WHITESPACE_MUL), 20)
		    .infixl(op("/", BinaryOperator.DIV), 20)
		    .prefix(op("-", UnaryOperator.NEG), 30)
		    .build(unit);
		    
		ref.set(parser);
		return parser;
	}
	
	public static final Parser<SignedDecimal> CALCULATOR = calculator(NUMBER).from(TOKENIZER, IGNORED);
	
	public static void main(String[] args){
	    //testing		
		System.out.println(CALCULATOR.parse(args[0]));
	}
	
	/**
	 * Parses and calculates the result of a String representation of an arithmetic expression
	 * 
	 * @param input String input, an arithmetic expression
	 * @return a {@link SignedDecimal} representing the result
	 */
	public static SignedDecimal calculate(String input) {
		return CALCULATOR.parse(input);
	}
}