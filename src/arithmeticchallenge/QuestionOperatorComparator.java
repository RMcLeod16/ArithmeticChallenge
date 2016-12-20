package arithmeticchallenge;

import java.util.Comparator;

import javax.swing.SortOrder;

// http://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
// http://stackoverflow.com/questions/1946668/sorting-using-comparator-descending-order-user-defined-classes
/**
 * A Comparator class to compare Question objects depending on Operation,<br>
 * in the order of Plus (+), Minus (-), Multiply (x), then Divide (÷)
 * 
 * @author Russell McLeod
 *
 */
public class QuestionOperatorComparator implements Comparator<Question>
{
	/**
	 * SortOrder of type javax.swing.SortOrder to specify which order the comparator will come and sort in.
	 */
	private SortOrder sortOrder;
	
	/**
	 * Default constructor for QuestionOperatorComparator using SortOrder.ASCENDING as default SortOrder.
	 */
	public QuestionOperatorComparator()
	{
		this.sortOrder = SortOrder.ASCENDING;			// Default is ascending order
	}
	
	/**
	 * Constructor for QuestionOperatorComparator specifying the SortOrder
	 * 
	 * @param _sortOrder
	 * The SortOrder in which to sort the Questions by.
	 */
	public QuestionOperatorComparator(SortOrder _sortOrder)
	{
		if (_sortOrder == SortOrder.UNSORTED)			// We want it sorted, so we do not want SortOrder.UNSORTED to be used
			this.sortOrder = SortOrder.ASCENDING;		// So if someone tries using that, use default ASCENDING instead
		else
			this.sortOrder = _sortOrder;				// Otherwise, set the sort order to the one specified
	}
	
	/**
	 * Method to translate Question Operator to an integer for comparison purposes
	 * 
	 * @param _operator
	 * The Operator to translate to an integer
	 * 
	 * @return
	 * The integer representing the Operator
	 */
	int GetIndexOfOperator(String _operator)
	{
		int index = 0;
		
		switch (_operator)
		{
			case "+":
				index = 1;
				break;
				
			case "-":
				index = 2;
				break;
				
			case "x":
				index = 3;
				break;
				
			case "÷":
				index = 4;
				break;
		}
		
		return index;
	}
	
	/**
	 * Comparator's compare method. Gets int representations of the Operations of the two Question objects, gets the differences between the two depending on order, and returns the result.
	 */
    public int compare(Question question1, Question question2)
    {	
    	int indexOfQuestionOp1 = GetIndexOfOperator(question1.getOperator());
    	int indexOfQuestionOp2 = GetIndexOfOperator(question2.getOperator());
    	int result;
    	
    	switch (sortOrder)
    	{
    	case ASCENDING:
    		result = indexOfQuestionOp1 - indexOfQuestionOp2;
    		break;
    		
    	case DESCENDING:
    		result = indexOfQuestionOp2 - indexOfQuestionOp1;
    		break;
    		
		default:
			result = 0;
			break;
    	}
        return result;
    }
}
