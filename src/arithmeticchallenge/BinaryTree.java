package arithmeticchallenge;

import java.util.HashMap;

/**
 * Class to create and manipulate a Binary Tree.
 * @author Russell McLeod
 * Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
 * New Think Tank
 */
public class BinaryTree
{
	/**
	 * An Enum for Binary Tree to determine how it is traversed.
	 * @author Russell McLeod
	 */
	public enum TreeOrder
	{
		IN_ORDER,
		PRE_ORDER,
		POST_ORDER
	}
	
	/**
	 * The root node for the Binary Tree.
	 */
	BTNode root;
	
	/**
	 * A StringBuilder used when traversing a Binary Tree, to record and print out data.
	 */
	private StringBuilder sb;
	
	/**
	 * A counter to count how many nodes are in a Binary Tree.
	 */
	private int counter;
	
	/**
	 * A HashMap of the Question objects in a Binary Tree, along with their Answers as their Keys.
	 */
	private HashMap<Integer, Question> hm = new HashMap<Integer, Question>();

	/**
	 * Method to add a Question as a node in the Binary Tree.
	 * 
	 * @param question
	 * The Question object to add to the Binary Tree.
	 */
	public void addBTNode(Question question)
	{
		// Create a new BTNode and initialize it
		BTNode newBTNode = new BTNode(question);

		// If there is no root this becomes root

		if (root == null)
			root = newBTNode;
		else
		{
			// Set root as the BTNode we will start
			// with as we traverse the tree
			BTNode focusBTNode = root;

			// Future parent for our new BTNode
			BTNode parent;

			while (true)
			{
				// root is the top parent so we start there
				parent = focusBTNode;

				// Check if the new BTNode should go on
				// the left side of the parent BTNode

				// if (key < focusBTNode.key)
				if (question.getAnswer() < focusBTNode.question.getAnswer())
				{
					// Switch focus to the left child
					focusBTNode = focusBTNode.leftChild;

					// If the left child has no children
					if (focusBTNode == null)
					{
						// then place the new BTNode on the left of it
						parent.leftChild = newBTNode;
						return;
					}
				}
				else
				{ // If we get here put the BTNode on the right
					focusBTNode = focusBTNode.rightChild;

					// If the right child has no children
					if (focusBTNode == null)
					{
						// then place the new BTNode on the right of it
						parent.rightChild = newBTNode;
						return;
					}
				}
			}
		}
	}

	// All BTNodes are visited in ascending order
	// Recursion is used to go to one BTNode and
	// then go to its child BTNodes and so forth
	/**
	 * Method to build a string of data from the nodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with an In Order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void inOrderBuildTreeString(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			// Traverse the left BTNode
			inOrderBuildTreeString(focusBTNode.leftChild);

			// Visit the currently focused on BTNode and add data to StringBuilder
			System.out.println("DEBUG:" + System.lineSeparator() +
								"Question\t->\t" + focusBTNode.toString() + System.lineSeparator() + 
								"leftChild\t->\t" + focusBTNode.leftChild + System.lineSeparator() + 
								"rightChild\t->\t" + focusBTNode.rightChild + System.lineSeparator());
			sb.append(focusBTNode);
			sb.append(", ");

			// Traverse the right BTNode
			inOrderBuildTreeString(focusBTNode.rightChild);
		}
	}
	
	/**
	 * Method to build a string of data from the nodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with a Pre order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void preOrderBuildTreeString(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			// Visit the currently focused on BTNode and add data to StringBuilder
			System.out.println("DEBUG:" + System.lineSeparator() +
								"Question\t->\t" + focusBTNode.toString() + System.lineSeparator() + 
								"leftChild\t->\t" + focusBTNode.leftChild + System.lineSeparator() + 
								"rightChild\t->\t" + focusBTNode.rightChild + System.lineSeparator());
			sb.append(focusBTNode);
			sb.append(", ");

			// Traverse the Left BTNode
			preOrderBuildTreeString(focusBTNode.leftChild);
			
			// Traverse the Right BTNode
			preOrderBuildTreeString(focusBTNode.rightChild);
		}
	}

	/**
	 * Method to build a string of data from the nodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with a Post Order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void postOrderBuildTreeString(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			// Traverse the Left BTNode
			postOrderBuildTreeString(focusBTNode.leftChild);
			
			// Traverse the Right BTNode
			postOrderBuildTreeString(focusBTNode.rightChild);

			// Visit the currently focused on BTNode and add data to StringBuilder
			System.out.println("DEBUG:" + System.lineSeparator() +
								"Question\t->\t" + focusBTNode.toString() + System.lineSeparator() + 
								"leftChild\t->\t" + focusBTNode.leftChild + System.lineSeparator() + 
								"rightChild\t->\t" + focusBTNode.rightChild + System.lineSeparator());
			sb.append(focusBTNode);
			sb.append(", ");
		}
	}
	
	/**
	 * Default toString() Method to represent the Binary Tree as a String.
	 */
	public String toString()
	{
		sb = new StringBuilder();
		sb.append("IN ORDER: ");
		inOrderBuildTreeString(this.root);			// Use inOrder Traversal as default
		sb.delete(sb.length()-2, sb.length());		// delete extra comma and space after end
		return sb.toString();
	}
	
	/**
	 * Another toString() Method to represent the Binary Tree as a String, depending on the specified traversal order.
	 * 
	 * @param _order
	 * the TreeOrder in which the Binary Tree should be traversed and the String built.
	 * 
	 * @return
	 * The String representation of the Binary Tree traversed in a specified Order.
	 */
	public String toString(TreeOrder _order)
	{
		sb = new StringBuilder();
		switch (_order)
		{
		case IN_ORDER:
			sb.append("IN ORDER: ");
			inOrderBuildTreeString(this.root);
			break;
			
		case PRE_ORDER:
			sb.append("PRE ORDER: ");
			preOrderBuildTreeString(this.root);
			break;
			
		case POST_ORDER:
			sb.append("POST ORDER: ");
			postOrderBuildTreeString(this.root);
			break;
		}
		
		sb.delete(sb.length()-2, sb.length());		// delete extra comma and space after end
		return sb.toString();
	}
	
	/**
	 * Method to build a HashMap from the BTNodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with an In Order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void inOrderBuildHashMap(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			inOrderBuildHashMap(focusBTNode.leftChild);
			hm.put(focusBTNode.question.getAnswer(), focusBTNode.question);
			inOrderBuildHashMap(focusBTNode.rightChild);
		}
	}
	
	/**
	 * Method to build a HashMap from the BTNodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with a Pre Order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void preOrderBuildHashMap(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			hm.put(focusBTNode.question.getAnswer(), focusBTNode.question);
			inOrderBuildHashMap(focusBTNode.leftChild);
			inOrderBuildHashMap(focusBTNode.rightChild);
		}
	}
	
	/**
	 * Method to build a HashMap from the BTNodes of a Binary Tree or Subtree,<br>
	 * starting from the specified BTNode with a Post Order traversal.
	 * 
	 * @param focusBTNode
	 * The BTNode to start the traversal and build from.
	 */
	private void postOrderBuildHashMap(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			inOrderBuildHashMap(focusBTNode.leftChild);
			inOrderBuildHashMap(focusBTNode.rightChild);
			hm.put(focusBTNode.question.getAnswer(), focusBTNode.question);
		}
	}
	
	/**
	 * Method to build and return a HashMap of the Binary Tree based on a specified TreeOrder.
	 * 
	 * @param _order
	 * The TreeOrder in which to traverse and build the HashMap.
	 * 
	 * @return
	 * The generated HashMap of the Binary Tree.
	 */
	public HashMap<Integer, Question> toHashMap(TreeOrder _order)
	{
		hm = new HashMap<Integer, Question>();
		switch (_order)
		{
			case IN_ORDER:
				inOrderBuildHashMap(this.root);
				break;
				
			case PRE_ORDER:
				preOrderBuildHashMap(this.root);
				break;
				
			case POST_ORDER:
				postOrderBuildHashMap(this.root);
				break;
		}
		return hm;
	}

	/**
	 * Method to find a BTNlode with a Question based on the answer of said question.
	 * 
	 * @param answer
	 * The answer value in which to search the Question and nodes for.
	 * @return
	 * The BTNode containing the Question with the specified answer value.
	 */
	public BTNode findBTNode(int answer)
	{
		// Start at the top of the tree
		BTNode focusBTNode = root;

		// While we haven't found the BTNode
		// keep looking

		// while (focusBTNode.key != key) {
		while (focusBTNode.question.getAnswer() != answer)
		{
			// If we should search to the left
			if (answer < focusBTNode.question.getAnswer())
				focusBTNode = focusBTNode.leftChild;	// Shift the focus BTNode to the left child
			else
				focusBTNode = focusBTNode.rightChild;	// Shift the focus BTNode to the right child

			// The BTNode wasn't found
			if (focusBTNode == null)
				return null;
		}
		return focusBTNode;
	}

	/**
	 * Method to count the amount of nodes in a Binary Tree or Subtree.
	 * 
	 * @return
	 * The amount of nodes counted.
	 */
	public int getNodeCount()
	{
		counter = 0;
		countTreeNodes(this.root);
		return counter;
	}
	
	/**
	 * Method that recursively traverses a Binary Tree or Subtree to count the nodes in said Tree.
	 *  
	 * @param focusBTNode
	 * The BTNode to start the traversal from.
	 */
	private void countTreeNodes(BTNode focusBTNode)
	{
		if (focusBTNode != null)
		{
			countTreeNodes(focusBTNode.leftChild);
			counter++;
			countTreeNodes(focusBTNode.rightChild);
		}
	}
}

/**
 * Subclass to build a BTNode that is part of a Binary Tree.
 * 
 * @author Russell McLeod
 */
class BTNode
{
	/**
	 * The Question object to add to the BTNode.
	 */
	Question question;

	/**
	 * The Left Child BTNode belonging to a BTNode.
	 */
	BTNode leftChild;
	
	/**
	 * The Right Child BTNode belonging to a BTNode.
	 */
	BTNode rightChild;
	
	/**
	 * Constructor for BTNode, adds the specified Question to the BTNode.
	 * 
	 * @param _question
	 * The Question to add to the BTNode.
	 */
	BTNode(Question _question)
	{
		this.question = _question;
	}

	/**
	 * Default toString() method for BTNode, returns a String representation of the Question data inside the BTNode.
	 */
	public String toString()
	{
		return question.getAnswer() + "(" + question.getFirstNo() + " " + question.getOperator() + " " + question.getSecondNo() + ")";
	}

}
