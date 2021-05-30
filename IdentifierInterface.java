package nl.vu.labs.phoenix.ap;

/* [1] Identifier Specification
 * 	   -- Complete the specification for an Identifier interface.
 * 		  See the List interface for inspiration
 */

/*	Elements: String of type StringBuffer
*	Structure: linear
*	Domain: All the chars must either be a letter or a digit.
*	The first chars has to be a letter and the identifier must be at least have length 1.
*
*	Constructors: Identifier(Char c);
*  			      PRE  - Character c must be a letter
*  	              POST - A Identifier object is created with char c as value
*
*  				  Identifier( Identifier OldId);
*  				  PRE  -
*  	              POST - A new Identifier object is created with the values of Identifier OldId.
 */
public interface IdentifierInterface {
	/*
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */

	/** @precondition
     *    --
     *  @postcondition
     *    Returns the value of the identifier as a String.
     **/
	String value();

	/*
	 * [3] Add anything else you think belongs to this interface
	 */

	// your code here

	 /** @precondition
     *    Each character c that is added must either be a letter or a digit
     *  @postcondition
     *    Character c has been added to the identifier.
     **/
	public void addChar (char c);


	/** @precondition
     *    --
     *  @postcondition
     *    Returns the length of the identifier.
     **/
	public int getlength();

}
