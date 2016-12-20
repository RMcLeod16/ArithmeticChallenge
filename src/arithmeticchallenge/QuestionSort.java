package arithmeticchallenge;

import java.util.ArrayList;

import javax.swing.SortOrder;

/**
 * A class demonstrating various Sort methods for sorting Question objects by Operation inside of ArithmeticChallenge.
 * 
 * @author Russell McLeod
 */
public class QuestionSort
{
	/* http://mathbits.com/MathBits/Java/arrays/Bubble.htm
	 * 
	 * Bubble:
	 * 
	 * declare counter var
	 * declare temp question var
	 * declare bool var to track unfinished status (to true, to start process)
	 * 
	 * while unfinished
	 * 	temp set unfinished to false, if a swap happens, unfinished will be true again
	 * 	for each element
	 * 		if current element comes after next element
	 * 			copy current element to temp var
	 * 			overwrite current element with next one
	 * 			overwrite next element with temp one to complete swao
	 * 			set unfinished to true
	 */
	/**
	 * A Question sorting method demonstrating the Bubble Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 */
	public static void Bubble(ArrayList<Question> al)
	{
		int i;
		Question temp;
		boolean unfinished = true;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator();
		
		while (unfinished)
		{
			unfinished = false;
			for (i = 0; i < al.size() - 1; i++)
				if (comparator.compare(al.get(i), al.get(i+1)) > 0)
				{
					temp = al.get(i);
					al.set(i, al.get(i+1));
					al.set(i+1, temp);
					unfinished = true;
				}
		}
	}
	
	/**
	 * A Question sorting method demonstrating the Bubble Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 * 
	 * @param _sortOrder
	 * The order of the sort, either SortOrder.ASCENDING or SortOrder.DESCENDING.
	 */
	public static void Bubble(ArrayList<Question> al, SortOrder _sortOrder)
	{
		int i;
		Question temp;
		boolean unfinished = true;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator(_sortOrder);
		
		while (unfinished)
		{
			unfinished = false;
			for (i = 0; i < al.size() - 1; i++)
				if (comparator.compare(al.get(i), al.get(i+1)) > 0)
				{
					temp = al.get(i);
					al.set(i, al.get(i+1));
					al.set(i+1, temp);
					unfinished = true;
				}
		}
	}
	
	
	/* http://mathbits.com/MathBits/Java/arrays/InsertionSort.htm
	 * 
	 * Insertion:
	 * 
	 * declare counter vars i, j
	 * declare temp object var
	 * construct new operation comparator
	 * 
	 * for each SORTED element (j, start at 1)
	 * 	store sorted element into temp
	 * 	for (counter = previous element, counter is greater than or equal to 0 AND comes after key, decrement counter)
	 * 		next counted one overwritten by current one
	 * 	next one is put into key
	 */
	/**
	 * A Question sorting method demonstrating the Insertion Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 */
	public static void Insertion(ArrayList<Question> al)
	{
		int i, j;
		Question key;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator();
		
		for (j = 1; j < al.size(); j++)
		{
			key = al.get(j);
			
			for (i = j - 1; (i >= 0) && (comparator.compare(al.get(i), key) > 0); i--)
				al.set(i+1, al.get(i));

			al.set(i+1, key);
		}
	}
	
	/**
	 * A Question sorting method demonstrating the Insertion Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 * 
	 * @param _sortOrder
	 * The order of the sort, either SortOrder.ASCENDING or SortOrder.DESCENDING.
	 */
	public static void Insertion(ArrayList<Question> al, SortOrder _sortOrder)
	{
		int i, j;
		Question key;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator(_sortOrder);
		
		for (j = 1; j < al.size(); j++)
		{
			key = al.get(j);
			
			for (i = j - 1; (i >= 0) && (comparator.compare(al.get(i), key) > 0); i--)
				al.set(i+1, al.get(i));
			
			al.set(i+1, key);
		}
	}
	
	/*
	 * http://mathbits.com/MathBits/Java/arrays/Exchange.htm
	 * 
	 * Exchange:
	 * 
	 * declare counter vars i, j
	 * declare temp question var
	 * construct new operation comparator
	 * 
	 * for each element (i)
	 * 	for each next one (j)
	 * 		if first element comes after next element
	 * 		put first element (i) in temp var
	 * 		overwrite first element (i) with next element (j)
	 * 		then overwrite next element (j) with temp var, completing switch process	
	 */
	/**
	 * A Question sorting method demonstrating the Exchange Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 */
	public static void Exchange(ArrayList<Question> al)
	{
		int i, j;
		Question temp;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator();
		
		for (i = 0; i < al.size() - 1; i++)
			for (j = i + 1; j < al.size(); j++)
				if (comparator.compare(al.get(i), al.get(j)) > 0)
				{
					temp = al.get(i);
					al.set(i, al.get(j));
					al.set(j, temp);
				}
	}
	
	/**
	 * A Question sorting method demonstrating the Exchange Sort algorithm.
	 * 
	 * @param al
	 * The ArrayList of Question objects to sort.
	 * 
	 * @param _sortOrder
	 * The order of the sort, either SortOrder.ASCENDING or SortOrder.DESCENDING.
	 */
	public static void Exchange(ArrayList<Question> al, SortOrder _sortOrder)
	{
		int i, j;
		Question temp;
		QuestionOperatorComparator comparator = new QuestionOperatorComparator(_sortOrder);
		
		for (i = 0; i < al.size() - 1; i++)
			for (j = i + 1; j < al.size(); j++)
				if (comparator.compare(al.get(i), al.get(j)) > 0)
				{
					temp = al.get(i);
					al.set(i, al.get(j));
					al.set(j, temp);
				}
	}
}
