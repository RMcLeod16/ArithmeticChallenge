package arithmeticchallenge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import libgui.libGui;

/**
 * Class to make a Student Frame to answer Questions given to it.
 * 
 * @author Russell McLeod
 *
 */
public class StudentFrame extends NetFrame implements WindowListener, ActionListener
{
	/**
	 * The JLabels on the JFrame.
	 */
	JLabel lblQuestionText, lblAnswerText, lblQuestion;
	
	/**
	 * The JTextField on the JFrame.
	 */
	JTextField txtAnswer;
	
	/**
	 * The JButtons on the frame
	 */
	JButton btnSend, btnExit;
	
	/**
	 * The Question object used by the JFrame.
	 */
	Question question;
	
	/**
	 * The server name/IP address to connect to.
	 */
    private String serverName = "localhost";
    
    /**
     * The port to connect through.
     */
    private int serverPort = 4444;
	
	/**
	 * The Main method that runs when this class is told to execute.
	 * 
	 * @param args
	 * Command-line args for main, not actually used.
	 */
	public static void main(String[] args)
	{
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
		
		JFrame mainFrame = new StudentFrame();
		mainFrame.setSize(170, 120);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Main constructor for this class.
	 */
	public StudentFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Arithmetic Challenge");
		setSize(170, 120);
		setResizable(false);
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		this.addWindowListener(this);
		
		lblQuestionText = libGui.LocateALabel(layout, "Question:", 10, 10, this);
		lblQuestion = libGui.LocateALabel(layout, "placeholder_text", 70, 10, this);

		lblQuestion.setVisible(false);
		lblAnswerText = libGui.LocateALabel(layout, "Answer:", 10, 30, this);
		txtAnswer = libGui.LocateATextField(layout, 7, 70, 30, this);
		
		btnSend = libGui.LocateAButton(layout, "Send", 50, 55, this, this);
		btnSend.setEnabled(connect(serverName, serverPort));
	}

	/**
	 * Method to handle when a Question is received from the Server.
	 */
	@Override
    public void handle(Question q)
    {
		String msg = q.getFirstNo() + q.getOperator() + q.getSecondNo();
		question = q;
        lblQuestion.setText(msg);
        lblQuestion.setVisible(true);
    }
    
	/**
	 * Method to handle the ActionPerformed Events, such as the "Send" button presses.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnSend)
			if (lblQuestion.getText() != "placeholder_text")
			{
				String resultText = "";
				question.setAnswer(Integer.parseInt(txtAnswer.getText()));
				if (question.HasCorrectAnswer())
					resultText = "Correct answer!";
				else
					resultText = "Sorry, wrong answer!";
				
				// http://www.rgagnon.com/javadetails/java-0376.html
				JOptionPane.showConfirmDialog(null, resultText, "Result", JOptionPane.DEFAULT_OPTION);
				send(question);
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

}
