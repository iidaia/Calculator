package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.PrintStream;
import java.util.HashMap;

/**
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	PrintStream out = new PrintStream(System.out);
	HashMap<String, T> hashMap = new HashMap<String, T>();

	@Override
	public T getMemory(String v) {

		if(hashMap.containsKey(v)) {

			return hashMap.get(v);

		} else return null;
//		} else {
//
//			throw new APException("This identifier is not known");
//		}
	}

	private boolean nextCharIsLetter (Scanner in) {

		return in.hasNext("[a-zA-Z]");
	}

	private boolean nextCharIsDigit (Scanner in) {

		return in.hasNext("[0-9]");
	}

	private boolean nextCharIs(Scanner in, char c) {

		return in.hasNext(Pattern.quote(c + ""));
	}

	private void checkChar(Scanner in, char c) throws APException {

		if (nextCharIs(in, c)) {

			in.next().charAt(0);

		} else {

			throw new APException("Error, character mismatch\n");
		}
	}

	private void deleteSpaces(Scanner in) {

    	while(in.hasNext(" ")) {

    		in.next().charAt(0);
    	}
    }

	private BigInteger toBigInteger(String s){

		return new BigInteger(s);
	}

	@Override
	public T eval(String s) {

		Scanner in = new Scanner(s);

		while(in.hasNextLine()) {

			try {

				Scanner statScanner = new Scanner(in.nextLine());
				statScanner.useDelimiter("");
				return statement(statScanner);

			} catch(APException error) {

				out.printf(error.getMessage());
			}

		}

		try {

			eof(in);

		} catch (APException error) {

			out.printf(error.getMessage());
		}

		return null;
	}

	private T statement(Scanner in) throws APException{

		deleteSpaces(in);

		if(nextCharIsLetter(in)) {

			assignment(in);
			return null;

		} else if(nextCharIs(in, '?')) {

			return print_statement(in);

		} else if(nextCharIs(in, '/')) {

			comment(in);
			return null;
		}

		throw new APException ("Error, this is not a correct statement\n");
	}

	private void assignment(Scanner in) throws APException {


		Identifier id = identifier(in);
		deleteSpaces(in);
		checkChar(in, '=');
		deleteSpaces(in);
		T set = expression(in);
		eol(in);
		hashMap.put(id.value(), set);

	}

	private T print_statement(Scanner in) throws APException {

		checkChar(in, '?');
		deleteSpaces(in);
		T set = expression(in);
		eol(in);
		out.println(set.print());

		return set;

	}

	private void comment(Scanner in) throws APException {

		while(in.hasNext()) {

			in.next();
		}

		eol(in);
	}

	private Identifier identifier(Scanner in) throws APException {

		if(!nextCharIsLetter(in)) {

			throw new APException ("Identifier must start with a letter \n");
		}

		char c = in.next().charAt(0);
		Identifier id = new Identifier(c);

		while(nextCharIsLetter(in) || nextCharIsDigit(in)) {

			id.addChar(in.next().charAt(0));
		}

		return id;
	}

	private T expression(Scanner in) throws APException{

		T result = term(in);

		while(nextCharIs(in, '+') || nextCharIs(in, '|') || nextCharIs(in, '-')) {

			if(nextCharIs(in, '+')) {

				in.next(); // delete plus sign
				deleteSpaces(in);
				T temp = term(in);
				deleteSpaces(in);
				result = (T) result.union(temp);

			} else if(nextCharIs(in, '|')) {

				in.next(); // delete symdiff sign
				deleteSpaces(in);
				T temp = term(in);
				deleteSpaces(in);
				result = (T) result.symdiff(temp);

			} else if(nextCharIs(in, '-')) {

				in.next(); // delete minus sign
				deleteSpaces(in);
				T temp = term(in);
				deleteSpaces(in);
				result = (T) result.complement(temp);
			}

		}

		return result;
	}

	private T term(Scanner in) throws APException{

		T result = factor(in);

		while(nextCharIs(in, '*')) {

			in.next(); // delete factor sign
			deleteSpaces(in);
			T temp = factor(in);
			deleteSpaces(in);
			result = (T) result.intersection(temp);
		}

		return result;
	}

	private T factor(Scanner in) throws APException{

		if(nextCharIsLetter(in)) {

			Identifier id = identifier(in);
			deleteSpaces(in);

			return getMemory(id.value());

		} else if(nextCharIs(in, '(')) {

			return complex_factor(in);

		} else if(nextCharIs(in, '{')) {

			return set(in);
		}

		throw new APException ("This factor is no identifier/complex_factor/set\n");
	}

	private T complex_factor(Scanner in) throws APException{

		in.next(); //delete bracket
		deleteSpaces(in);
		T result = expression(in);
		deleteSpaces(in);
		if (nextCharIs(in, ')')) {

			in.next(); //delete bracket
			deleteSpaces(in);

		}  else {

			throw new APException ("Right curly bracket missing\n");
		}

		return result;
	}

	private T set(Scanner in) throws APException {

		in.next(); //delete curly bracket
		deleteSpaces(in);
		T result = rowOfNaturalNumbers(in);
		deleteSpaces(in);

		if(nextCharIs(in, '}')) {

			in.next(); //delete curly bracket

		} else {

			throw new APException ("Right curly bracket missing\n");
		}

		deleteSpaces(in);

		return result;
	}

	private T rowOfNaturalNumbers(Scanner in) throws APException {

		SetInterface<BigInteger> result = new Set<BigInteger>();

		if(nextCharIsDigit(in)){

			result.add(toBigInteger(naturalNumber(in)));
			deleteSpaces(in);

			if(!nextCharIs(in, ',') && !nextCharIs(in, '}')){

				throw new APException("Error comma or right curly bracket expected");
			}
		}


		while(!nextCharIs(in, '}') && result.size() != 0) {

			checkChar(in, ',');
			deleteSpaces(in);
			if(nextCharIsDigit(in)){
				result.add(toBigInteger(naturalNumber(in)));
				deleteSpaces(in);
			} else {

				throw new APException ("Error, number expected\n");
			}
		}

		return (T) result;
	}

	private String naturalNumber(Scanner in) throws APException {

		StringBuffer s = new StringBuffer();

		while(nextCharIsDigit(in)) {

			s.append(in.next().charAt(0));

		}

		if(s.charAt(0) == '0' && s.length() > 1) { //check if first number is zero

			throw new APException ("Natural number cannot start with zero\n");
		}

		return s.toString();
	}

	private void eof(Scanner in) throws APException {

		if (in.hasNextLine()) {

			throw new APException("Error, no end of file\n");
		}
	}

	private void eol(Scanner in) throws APException {

		if (in.hasNext()) {

			throw new APException("Error, no end of line\n");
		}
	}

}
