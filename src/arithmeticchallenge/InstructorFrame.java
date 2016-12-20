/****************************************************************
   PROGRAM:   ArithmeticChallenge - Instructor Frame
   AUTHOR:    Russell McLeod

   FUNCTION:  A Frame for the Instructor to create basic math
              Questions, send them to students, and record the
              answers.

   INPUT:     Standard Input from Frame controls, Question
              objects from Server 

   OUTPUT:    Table with created Questions, Question objects sent
              to server, Doubly-Linked List of incorrectly
              answered Questions received from server, Binary Tree
              of used Questions, Text File of Binary Tree Data,
              text file of HashMap of Binary Tree Data

****************************************************************/
package arithmeticchallenge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import arithmeticchallenge.BinaryTree.TreeOrder;
import libgui.libGui;

/**
 * Class to build the Instructor Frame for ArithmeticChallenge
 * 
 * @author Russell McLeod
 */
public class InstructorFrame extends NetFrame implements WindowListener, ActionListener
{	
	/**
	 * The Name/IP address of the server to connect to.
	 */
    private String serverName = "localhost";
    
    /**
     * The network port to communicate with the server over.
     */
    private int serverPort = 4444;
    
    // Various Controls on Frame
	JLabel lblEnterQuestion, lblFirstNo, lblSecondNo, lblOperation, lblAnswer, lblDLList, lblFindAnswer, lblQuestionSearch;
	JTextField txtQFirstNo, txtQSecondNo, txtAnswer, txtFindAnswer;
	JTextArea txaDLList, txaBTree;
	JScrollPane scpDLList, scpBTree;
	JComboBox<Object> cboQOperation;
	JButton btnSend, btnSort1, btnSort2, btnSort3, btnFindAnswer, btnInOrderDisp, btnPreOrderDisp, btnPostOrderDisp, btnInOrderSave, btnPreOrderSave, btnPostOrderSave;
	
	// Data Structure related vars
	ArrayList<Question> alQuestions = new ArrayList<Question>();
    JTable table;
    QuestionTableModel tableModel;
	DLList dllWrongQuestions = new DLList();
	BinaryTree btrAnsweredQuestions = new BinaryTree();
    
	/**
	 * Array of Math Operations supported by program.
	 */
	String[] operations = { "+", "-", "x", "÷" };
	
	/****************************************************************

	   <p>FUNCTION:   Main(String[] args)</p>

	   <p>ARGUMENTS:  Default command-line args, not used.</p>

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Creates the Instructor Frame and connects to the<br>
	   			   Server for immediate use by Instructor to make and<br>
	   			   send Questions via server to Student Frames</p>
	****************************************************************/
	public static void main(String[] args)
	{
		// Set look and feel
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		// make new InstructorFrame and display it
		JFrame mainFrame = new InstructorFrame();
		mainFrame.setVisible(true);
	}
	
	/****************************************************************

	   <p>FUNCTION:   InstructorFrame()</p>

	   <p>ARGUMENTS:  none</p>

	   <p>RETURNS:    none</p>

	   <p>NOTES:      Is Default Constructor for InstructorFrame</p>
	****************************************************************/
	public InstructorFrame()
	{
		// Set basic parameters for Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Arithmetic Challenge");
		setSize(520, 600);
		setResizable(false);
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		this.addWindowListener(this);
		
		// Try to connect to running NetServer
		connect(serverName, serverPort);
		
		// Build Question-making related controls and add to Frame.
		lblEnterQuestion = libGui.LocateALabel(layout, "Enter question, then click Send.", 10, 10, this);
		
		lblFirstNo = libGui.LocateALabel(layout, "First Number:", 10, 35, this);
		lblOperation = libGui.LocateALabel(layout, "Operation:", 10, 60, this);
		lblSecondNo = libGui.LocateALabel(layout, "Second Number:", 10, 85, this);
		lblAnswer = libGui.LocateALabel(layout, "Answer:", 10, 110, this);
		
		txtQFirstNo = libGui.LocateATextField(layout, 7, 110, 35, this);
		txtQSecondNo = libGui.LocateATextField(layout, 7, 110, 85, this);
		txtAnswer = libGui.LocateATextField(layout, 7, 110, 110, this);
		
		cboQOperation = libGui.LocateAComboBox(layout, operations, 110, 60, this);
		
		btnSend = libGui.LocateAButton(layout, "Send", 110, 140, this, this);
		btnSort1 = libGui.LocateAButton(layout, "Sort 1", 170, 140, this, this);
		btnSort2 = libGui.LocateAButton(layout, "Sort 2", 230, 140, this, this);
		btnSort3 = libGui.LocateAButton(layout, "Sort 3", 290, 140, this, this);
		
		// get question panel-related controls to listen to changes
		// http://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
		txtQFirstNo.getDocument().addDocumentListener(new NumberQuestionBoxListener());
		txtQSecondNo.getDocument().addDocumentListener(new NumberQuestionBoxListener());
		
		cboQOperation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				CalculateAnswerInt();
			}});

		// Build Table-related controls and ad to Frame.
		String[] columnNames = { "First No.", "Operator", "Second No.", "Answer" };
		
		tableModel = new QuestionTableModel(alQuestions, columnNames);
		
		MakeQuestionsAskedTable(layout, tableModel);
		
		// Make large text area controls for holding string representations of certain dta structures.
		MakeBigTextAreas(layout);
		
		// Make search-related controls and add to Frame.
		lblQuestionSearch = libGui.LocateALabel(layout, "Find Question based on given wrong answer:", 10, 300 , this);
		txtFindAnswer = libGui.LocateATextField(layout, 5, 10, 320, this);
		btnFindAnswer = libGui.LocateAButton(layout, "Find", 60, 320, this, this);
		lblFindAnswer = libGui.LocateALabel(layout, "Placeholder label for Find Answer Result", 10, 350, this);
		lblFindAnswer.setVisible(false);
		
		// Make Binary Tree Display and save related controls and add to Frame.
		btnInOrderDisp = libGui.LocateAButton(layout, "In Order", 10, 500, this, this);
		btnPreOrderDisp = libGui.LocateAButton(layout, "Pre Order", 85, 500, this, this); 
		btnPostOrderDisp = libGui.LocateAButton(layout, "Post Order", 165, 500, this, this);
		
		btnInOrderSave = libGui.LocateAButton(layout, "Save", 10, 525, this, this);
		btnPreOrderSave = libGui.LocateAButton(layout, "Save", 85, 525, this, this); 
		btnPostOrderSave = libGui.LocateAButton(layout, "Save", 165, 525, this, this);
	}

	/****************************************************************

	   <p>FUNCTION:   MakeQuestionsAskedTable()</p>

	   <p>ARGUMENTS:  SpringLayout layout, the SpringLayout to add the
	   				  Control constraints to.<br>
	   				  QuestionTableModel _tableModel, the TableModel to create the JTabel from.


	   <p>RETURNS:    void</p>

	   <p>NOTES:      Creates the JTable for the created Questions based on a given<br>
	   				  QuestionTableModel</p>
	   
	   @param layout
	   SpringLayout layout, the SpringLayout to add the Control constraints to.
	   
 	   @param _tableModel
 	   QuestionTableModel _tableModel, the TableModel to create the JTabel from.
	****************************************************************/
	public void MakeQuestionsAskedTable(SpringLayout layout, QuestionTableModel _tableModel)
	{
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);
        
        // Create a new table instance
        table = new JTable(_tableModel);

        // Configure some of JTable's parameters
        table.isForegroundSet();
        table.setShowHorizontalLines(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        topPanel.add(table);

        // Change the text and background colours
        table.setSelectionForeground(Color.white);
        table.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = table.createScrollPaneForTable(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(300, 115));
        layout.putConstraint(SpringLayout.WEST, topPanel, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, topPanel, 10, SpringLayout.NORTH, this);
	}
	
	/****************************************************************

	   <p>FUNCTION:   MakeBigTextAreas()</p>

	   <p>ARGUMENTS:  SpringLayout layout, the SpringLayout to add the
	   				  Control constraints to.

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Creates the large JTextAreas to display the Doubly-Linked List and Binary Tree Data.</p>
	   
	   @param _layout
	   SpringLayout layout, the SpringLayout to add the Control constraints to.
	****************************************************************/
	public void MakeBigTextAreas(SpringLayout _layout)
	{
		lblDLList = libGui.LocateALabel(_layout, "List of wrong answers", 10, 180, this);
		txaDLList = new JTextArea();
		scpDLList = new JScrollPane(txaDLList);
		
		this.add(scpDLList);
		scpDLList.setPreferredSize(new Dimension(500, 100));
		_layout.putConstraint(SpringLayout.WEST, scpDLList, 10, SpringLayout.WEST, this);
		_layout.putConstraint(SpringLayout.NORTH, scpDLList, 200, SpringLayout.NORTH, this);
		
		txaBTree = new JTextArea();
		scpBTree = new JScrollPane(txaBTree);
		this.add(scpBTree);
		scpBTree.setPreferredSize(new Dimension(500, 100));
		_layout.putConstraint(SpringLayout.WEST, scpBTree, 10, SpringLayout.WEST, this);
		_layout.putConstraint(SpringLayout.NORTH, scpBTree, 400, SpringLayout.NORTH, this);
	}

	/****************************************************************

	   <p>FUNCTION:   CalculateAnswerInt()</p>

	   <p>ARGUMENTS:  none</p>

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Reads the data in the question creation<br>
	   				  controls and automatically calculates the<br>
	   				  answer and adds it to the Answer Box Control.</p>
	   
	****************************************************************/
	public void CalculateAnswerInt()
	{
		try
		{
			int answer = 0;
			switch (cboQOperation.getSelectedIndex())
			{
				case 0:	// add
					answer = Integer.parseInt(txtQFirstNo.getText()) + Integer.parseInt(txtQSecondNo.getText());
					break;
					
				case 1:	// subtract
					answer = Integer.parseInt(txtQFirstNo.getText()) - Integer.parseInt(txtQSecondNo.getText());
					break;
					
				case 2:	// multiply
					answer = Integer.parseInt(txtQFirstNo.getText()) * Integer.parseInt(txtQSecondNo.getText());
					break;
					
				case 3:	// divide
					answer = Integer.parseInt(txtQFirstNo.getText()) / Integer.parseInt(txtQSecondNo.getText());
					break;
			}
			txtAnswer.setText(Integer.toString(answer));
		}
		catch (NumberFormatException e)
		{
			txtAnswer.setText("");
		}
	}
	
	// Method that gets a string, and writes it to a file
	/****************************************************************

	   <p>FUNCTION:   WriteLinesToFile()</p>

	   <p>ARGUMENTS:  String line, the String to add to the text file<br>
	   				  as a new line.<br>
	   				  String fileName, the File Name of the text file<br>
	   				  to write to.</p>

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Gets a given string and a given file name and<br>
	   				  writes the string to a text file with the<br>
	   				  given file name.</p>
	   
	   @param line
	   The String to add to the text file as a new line.
	   @param fileName
	   The File Name of the text file to write to.
	****************************************************************/
	private void WriteLinesToFile(String line, String fileName)
	{
		try
		{
			// Set up PrintWriter
			PrintWriter out = new PrintWriter(fileName);
			// Print given string on new line
			out.println(line);
			// Close PrintWriter when done with it
			out.close();
		}
		catch (Exception e)
		{
			System.err.println("ERROR!\t" + e.getMessage());
		}
	}
	
	
	/****************************************************************

	   <p>FUNCTION:   handle()</p>

	   <p>ARGUMENTS:  Question q, the Question received from the Server.

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Override for arithmeticchallenge.NetFrame.handle(),<br>
	   				  gets the received Question, and if the answer is<br>
	   				  wrong, add it to the Wrong-Answer Linked List</p>
	   
	   @param q
	   The Question received from the Server.
	****************************************************************/
	@Override
	public void handle(Question q)
	{
		if (!q.HasCorrectAnswer())
		{
			dllWrongQuestions.appendToList(new DLLNode(q));
			String str = dllWrongQuestions.toString();
			txaDLList.setText(str);
			
			System.out.print(str);
			dllWrongQuestions.print();
		}
	}

	/****************************************************************

	   <p>FUNCTION:   actionPerformed()</p>

	   <p>ARGUMENTS:  ActionEvent e, the event triggering the<br>
	   				  actionPerformed event.

	   <p>RETURNS:    void</p>

	   <p>NOTES:      Checks if the event is from certain buttons<br>
	   				  being pressed, and perform certain functions<br>
	   				  depending on the button.</p>
	   
	   @param q
	   The event triggering the actionperformed event.
	****************************************************************/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnSend)
		{
			try
			{
				Question q = new Question(Integer.parseInt(txtQFirstNo.getText()),
										(String)cboQOperation.getSelectedItem(),
										Integer.parseInt(txtQSecondNo.getText()),
										Integer.parseInt(txtAnswer.getText()));
				
				
				tableModel.add(q);
				table.repaint();
				
				send(q);
				
				btrAnsweredQuestions.addBTNode(q);
				txaBTree.setText(btrAnsweredQuestions.toString(TreeOrder.IN_ORDER));
			}
			catch (NumberFormatException ex)
			{
				System.out.println("Is not a valid math problem, ignoring...");
			}
		}
		
		if (e.getSource() == btnSort1)
		{
			QuestionSort.Bubble(alQuestions);
			table.repaint();
		}
		
		if (e.getSource() == btnSort2)
		{
			QuestionSort.Insertion(alQuestions, SortOrder.DESCENDING);
			table.repaint();
		}
		
		if (e.getSource() == btnSort3)
		{
			QuestionSort.Exchange(alQuestions);
			table.repaint();
		}
		
		if (e.getSource() == btnFindAnswer)
		{
			try
			{
				DLLNode result = dllWrongQuestions.find(Integer.parseInt(txtFindAnswer.getText()));
				
				if (result == null)
					lblFindAnswer.setText("Question with specified answer " + txtFindAnswer.getText() + " not found.");
				else
					lblFindAnswer.setText("Question with specified answer " + txtFindAnswer.getText() + " found in question " + result.question.toString());
				
				lblFindAnswer.setVisible(true);
			}
			catch (NumberFormatException ex)
			{
				System.out.println("Find Answer value not valid, ignoring...");
			}
		}
		
		if (e.getSource() == btnInOrderDisp)
		{
			txaBTree.setText(btrAnsweredQuestions.toString(TreeOrder.IN_ORDER));
		}
		
		if (e.getSource() == btnPreOrderDisp)
		{
			txaBTree.setText(btrAnsweredQuestions.toString(TreeOrder.PRE_ORDER));
		}
		
		if (e.getSource() == btnPostOrderDisp)
		{
			txaBTree.setText(btrAnsweredQuestions.toString(TreeOrder.POST_ORDER));
		}
		
		if (e.getSource() == btnInOrderSave)
		{
			WriteLinesToFile(btrAnsweredQuestions.toString(TreeOrder.IN_ORDER), "Binary Tree In-Order.txt");
			WriteLinesToFile(btrAnsweredQuestions.toHashMap(TreeOrder.IN_ORDER).toString(), "Binary Tree In-Order HASHMAP.txt");
		}
		
		if (e.getSource() == btnPreOrderSave)
		{
			WriteLinesToFile(btrAnsweredQuestions.toString(TreeOrder.PRE_ORDER), "Binary Tree Pre-Order.txt");
			WriteLinesToFile(btrAnsweredQuestions.toHashMap(TreeOrder.PRE_ORDER).toString(), "Binary Tree Pre-Order HASHMAP.txt");
		}
		
		if (e.getSource() == btnPostOrderSave)
		{
			WriteLinesToFile(btrAnsweredQuestions.toString(TreeOrder.POST_ORDER), "Binary Tree Post-Order.txt");
			WriteLinesToFile(btrAnsweredQuestions.toHashMap(TreeOrder.POST_ORDER).toString(), "Binary Tree Post-Order HASHMAP.txt");
		}
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) { }

	@Override
	public void windowClosed(WindowEvent arg0) { }

	@Override
	public void windowClosing(WindowEvent arg0) { }

	@Override
	public void windowDeactivated(WindowEvent arg0) { }

	@Override
	public void windowDeiconified(WindowEvent arg0) { }

	@Override
	public void windowIconified(WindowEvent arg0) { }

	@Override
	public void windowOpened(WindowEvent arg0) { }
	
	/****************************************************************

	   <p>CLASS:   	  NumberQuestionBoxListener()</p>

	   <p>ARGUMENTS:  none</p>

	   <p>RETURNS:    none</p>

	   <p>NOTES:      Subclass for a DocumentListener used to<br>
	   				  Automatically calculate answers for questions<br>
	   				  as they are entered into the frame for the first time.</p>
	   
	   @param q
	   The Question received from the Server.
	****************************************************************/
	class NumberQuestionBoxListener implements DocumentListener
	{
		@Override
		public void changedUpdate(DocumentEvent arg0)
		{
			CalculateAnswerInt();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0)
		{
			CalculateAnswerInt();
		}

		@Override
		public void removeUpdate(DocumentEvent arg0)
		{
			CalculateAnswerInt();
		}
	}
}
