package arithmeticchallenge;

/**
 * A Subclass specifying a DLLNode object for use in a Doubly-Linked List.
 * 
 * @author Russell McLeod
 * Adapted from code by Toshimi Minoura with additions by Matt C.
 */
class DLLNode
{
	/**
	 * Reference to the previous DLLNode before this one in the Linked List.
	 */
	DLLNode prev;
	
	/**
	 * Reference to the next DLLNode after this one in the Linked List.
	 */
	DLLNode next;
	
	/**
	 * The Question object to add to the DLLNode.
	 */
	Question question;
	
	/**
	 * Default Constructor for DLLNode.
	 */
	DLLNode()
	{
		prev = this;
		next = this;
		question = new Question();
	}
	
	/**
	 * Constructor for DLLNode adding specified Question to the DLLNode.
	 * 
	 * @param _question
	 */
	DLLNode(Question _question)
	{
		prev = null;
		next = null;
		question = _question;
	}
	
	/**
	 * Method to append a DLLNode after an existing DLLNode in the Doubly-Linked List.
	 * 
	 * @param newDLLNode
	 * The new node to append after this DLLNode.
	 */
    public void append(DLLNode newDLLNode)
    {
    	// attach newDLLNode after this DLLNode
        newDLLNode.prev = this;
        newDLLNode.next = next;
        
        if (next != null)
            next.prev = newDLLNode;

        next = newDLLNode;
        System.out.println("DLLNode with data " + newDLLNode.toString()
                + " appended after DLLNode with data " + this.toString());
    }

    /**
     * Method to insert a new DLLNode before an existing DLLNode in the Doubly-Linked List.
     * 
     * @param newDLLNode
     * The new node to insert before this DLLNode.
     */
    public void insert(DLLNode newDLLNode)
    {
    	// attach newDLLNode before this DLLNode
        newDLLNode.prev = prev;
        newDLLNode.next = this;
        prev.next = newDLLNode;;
        prev = newDLLNode;
        System.out.println("DLLNode with data " + newDLLNode.toString()
                + " inserted before DLLNode with data " + this.toString());
    }

    /**
     * Method to remove a DLLNode from the Doubly-Linked List.
     */
    public void remove()
    {              
    	next.prev = prev;   // remove this DLLNode        
        prev.next = next;	// bypass this DLLNode
        System.out.println("DLLNode with data " + this.toString() + " removed");
    }
    
    /**
     * Default toString method to return a String representation of the DLLNode data.
     */
    public String toString()
    {
    	return this.question.toString();
    }
}

/**
 * Class to build a Doubly-Linked List.
 * 
 * @author Russell McLeod
 */
public class DLList
{
	/**
	 * The Head Node of the Doublyh-Linked List.
	 */
	DLLNode head;
	
	/**
	 * The Tail Node of the Doubly-Linked List.
	 */
	DLLNode tail;
	
	/**
	 * Default Constructor for the Doubly-Linked List.
	 */
	public DLList()
	{
		head = new DLLNode();
		tail = new DLLNode();
		head.append(tail);
	}
	
	/*
	 * 	for (current DLLNode = next one after the head DLLNode; current is not the head; current DLLNode is next DLLNode)
			if current DLLNode's answer == _answer
				print "(answer) is found"
				return current
		print "data not found"
		return null
	 */
	/**
	 * Method to find a specific DLLNode with a Question depending on the Question Answer specified.
	 * 
	 * @param _answer
	 * The Answer value to use as a Key to find the DLLNode containing the Question that holds this answer value.
	 * 
	 * @return
	 * The first found DLLNode with the Question containing the specified answer value, else null if not found.
	 */
    public DLLNode find(int _answer)
    {          
    	// find DLLNode containing x
        for (DLLNode current = head.next; current != head; current = current.next)
        {
            if (current.question.getAnswer() == _answer)		// If the current DLLNode contains the answer...
            {
            	System.out.print("Data with answer " + _answer + " found in question => " + current.question.toString());
            	return current;			// Return the DLLNode containing the found answer
            }
        }
        System.out.println("Data " + _answer + " not found");
        return null;
    }
    
    //This Get method Added by Matt C
    /**
     * Method to get a the DLLNode at a specified "index" in the Doubly-Linked List.
     * 
     * @param i
     * The "index" of the Doubly-Linked List containing the wanted DLLNode./
     *  
     * @return
     * The DLLNode found at that specified position in the Doubly-Linked List.
     */
    public DLLNode get(int i)
    {
        DLLNode current = this.head;
        if (i < 0 || current == null)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        while (i > 0)
        {
            i--;
            current = current.next;
            if (current == null)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current;
    }

    /**
     * default toString() method to build a String representation of the Doubly-Linked List.
     */
    public String toString()
    {
        String str = "";
        DLLNode current;
        if (head.next == head)
            return "List Empty";
        
        str = "HEAD <-> ";
        
        for (current = head.next; current != head && current != null; current = current.next)
        {
        	if (current != tail)
        		str = str + current.question.toString() + " <-> ";
        	else if (current == tail)
        		str += "TAIL" + System.lineSeparator();
        }
        return str;
    }

    /**
     * A Basic Method to print the Doubly-Linked List's data to Console.
     */
    public void print()
    {                  // print content of list
        if (head.next == head)
        {             // list is empty, only header DLLNode
            System.out.println("list empty");
            return;
        }
        System.out.print("list content = ");
        
        DLLNode current = head;

        do
        {
        	System.out.print(current.question.toString() + " ");
        	current = current.next;
        }
        while (current != head);
        
        System.out.println("");
    }
    
    /**
     * Method to append a new DLLNode to the end of a Doubly-Linked List.
     * 
     * @param newDLLNode
     * The new DLLNode to add to the Doubly-Linked List.
     */
    public void appendToList(DLLNode newDLLNode)
    {
    	DLLNode current = head;
    	
    	while (current.next != null && current.next != head && current.next != tail)
    		current = current.next;
    	
    	current.append(newDLLNode);
    }
}
