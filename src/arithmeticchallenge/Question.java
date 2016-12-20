package arithmeticchallenge;

import java.io.Serializable;

/**
 * Class to build a Question object used to represent a basic math problem and answer.
 * 
 * @author Russell McLeod
 */
public class Question implements Serializable
{
	/**
	 * The First Number in the question.
	 */
	private int firstNo;
	
	/**
	 * The operator in the question.
	 */
	private String operator;
	
	/**
	 * The Second Number in the question.
	 */
	private int secondNo;
	
	/**
	 * The Answer to the question (either generated or given by user)
	 */
	private int answer;
	
	/**
	 * Getter Method to obtain First No. of Question.
	 * 
	 * @return
	 * The First No. of the Question
	 */
	public int getFirstNo()
	{
		return firstNo;
	}

	/**
	 * Setter Method to set the First No. of the Question.
	 * 
	 * @param _firstNo
	 * The Integer value to set the First No. to.
	 */
	public void setFirstNo(int _firstNo)
	{
		this.firstNo = _firstNo;
	}

	/**
	 * Getter Method to obtain Operator of Question.
	 * 
	 * @return
	 * The Operator of the Question
	 */
	public String getOperator()
	{
		return operator;
	}
	/**
	 * Setter Method to set the Operator of the Question.
	 * 
	 * @param _operator
	 * The String value to set the Operator to.
	 */
	public void setOperator(String _operator)
	{
		this.operator = _operator;
	}

	/**
	 * Getter Method to obtain Second No. of Question.
	 * 
	 * @return
	 * The Second No. of the Question
	 */
	public int getSecondNo()
	{
		return secondNo;
	}

	/**
	 * Setter Method to set the Second No. of the Question.
	 * 
	 * @param _secondNo
	 * The Integer value to set the Second No. to.
	 */
	public void setSecondNo(int _secondNo)
	{
		this.secondNo = _secondNo;
	}

	/**
	 * Getter Method to obtain Answer of Question.
	 * 
	 * @return
	 * The given Answer of the Question
	 */
	public int getAnswer()
	{
		return answer;
	}

	/**
	 * Setter Method to set the Answer of the Question.
	 * 
	 * @param _answer
	 * The Integer value to set the Answer to.
	 */
	public void setAnswer(int _answer)
	{
		this.answer = _answer;
	}
	
	/**
	 * Default Constructor for Question.
	 */
	public Question()
	{
		setFirstNo(0);
		setOperator("+");
		setSecondNo(0);
		setAnswer(0);
	}
	
	/**
	 * Constructor for Question based on specified values.
	 * 
	 * @param _firstNo
	 * The First No. of the Question.
	 * 
	 * @param _operator
	 * The Operator of the Question.
	 * 
	 * @param _secondNo
	 * The Second No. of the Question.
	 * 
	 * @param _answer
	 * The Answer of the Question.
	 */
	public Question(int _firstNo, String _operator, int _secondNo, int _answer)
	{
		setFirstNo(_firstNo);
		setOperator(_operator);
		setSecondNo(_secondNo);
		setAnswer(_answer);
	}
	
	/**
	 * default toString() method for Question, returns String representation of Question object based on Question data.
	 */
	public String toString()
	{
		return this.firstNo + this.operator + this.secondNo + "=" + this.answer;
	}
	
	/**
	 * Method to check the answer of a given Question to see whether or not it is correct.
	 * 
	 * @return
	 * True if the answer is correct, false if it is not.
	 */
	public boolean HasCorrectAnswer()
	{
		int result = 0;
		switch (this.operator)
		{
		case "+":
			result = this.firstNo + this.secondNo;
			break;
			
		case "-":
			result = this.firstNo - this.secondNo;
			break;
			
		case "x":
			result = this.firstNo * this.secondNo;
			break;
			
		case "÷":
			result = this.firstNo / this.secondNo;
			break;
		}
		
		if (this.answer == result)
			return true;
		else
			return false;
	}
}


