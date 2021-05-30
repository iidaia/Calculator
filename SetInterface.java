package nl.vu.labs.phoenix.ap;

/* [1] Set Specification
 * 	   -- Complete the specification for a set interface.
 * 		  See the List interface for inspiration
 */

/*
*  Elements: identifiers of type Identifier
*  Structure:
*  Domain:  Sets can contain a minimum of 0 and a maximum of 20 identifiers.
*  			Only identifiers, following the restrictions used in this assignments, are
*			allowed as elements of a set.
*  Constructor: Set();
*  				PRE  -
*  				POST - A new Set object has been created
*
*     			Set(Set OldSet);
*  	        	PRE  -
*  	        	POST - A new Set object has been made with the values of Set OldSet
*
*
*/
public interface SetInterface<T extends Comparable<T>> {

	/*
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */

	/**
	 * Hint:
	 * @return
	 * 	true  - element was inserted
	 * 	false - element was already present
	 */
	boolean add(T t);

	/** @precondition
     *    The set is not empty.
     *  @postcondition
     *    The value of the current element has been returned.
     */
	T get();

	/** @precondition
     *    --
     *  @postcondition
     *    TRUE: The set contains the element t.
     *      The current element of set-PRE is not present in set-POST.
     *    	current-POST points to:
     *    		- if set-POST is empty
     *        	null
     *    		- if set-POST is not empty
     *        	if current-PRE was the last element of set-PRE
     *          the last element of set-POST
     *        	else
     *          the element after current-PRE
     *    FALSE: The set does not contain the element t.
     *    	current-POST points to current-PRE.
     **/
	boolean remove(T t);


    /** @precondition
     *    --
     *  @postcondition
     *    The number of elements in the set has been returned.
     **/
	int size();


	/** @precondition
     *    --
     *  @postcondition
     *    A copy of the set has been returned.
     */
	SetInterface<T> copy();

	/** @precondition
     *    --
     *  @postcondition
     *    A string of the elements in the set has been returned.
     */
	String print();

	/*
	 * [3] Methods for set operations
	 * 	   -- Add methods to perform the 4 basic set operations
	 * 		  (union, intersection, difference, symmetric difference)
	 */

	/** @precondition
     *    --
     *  @postcondition
     *    The complement of the set and setB has been returned.
     */
	public SetInterface<T> complement(SetInterface<T> setB);

	/** @precondition
     *    --
     *  @postcondition
     *    The intersection of the set and setB has been returned.
     */
	public SetInterface<T> intersection(SetInterface<T> setB);

	/** @precondition
     *    --
     *  @postcondition
     *    The union of the set and setB has been returned.
     */
	public SetInterface<T> union(SetInterface<T> setB);

	/** @precondition
     *    --
     *  @postcondition
     *    The symmetric difference of the set and setB has been returned.
     */
	public SetInterface<T> symdiff(SetInterface<T> setB);



	/*
	 * [4] Add anything else you think belongs to this interface
	 */


	/** @precondition
     *    --
     *  @postcondition
     *    TRUE: The set contains the element t.
     *      current-POST points to the element in the set.
     *    FALSE: The set does not contain the element t.
     *    current-POST points to:
     *      - if list-POST is empty
     *          null
     *      - if the first element in list > t:
     *          the first element in list
     *        else
     *          the last element in list with value < t
     **/
	public boolean contains(T t);

}
