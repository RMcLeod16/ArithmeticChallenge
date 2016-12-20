package arithmeticchallenge;

import java.io.*;
import java.net.*;

import javax.swing.*;

/**
 * Class to create a NetFrame, which is a JFrame with added network functionality.
 * @author Russell McLeod
 *
 */
public class NetFrame extends JFrame
{
	// NET STUFF
	/**
	 * The Socket to comminicate with the server over.
	 */
    private Socket socket = null;
    
    /**
     * The Output Stream to send data through.
     */
    private ObjectOutputStream streamOut = null;
    
    /**
     * The Client Thread for this Frame.
     */
    private ClientThread client = null;
    
    /**
     * Method to connect the NetFrame to a server.
     * 
     * @param serverName
     * The Name/IP address of the server.
     * 
     * @param serverPort
     * The Port over which to communicate.
     * 
     * @return
     * True if connection was successful, otherwise false.
     */
    public boolean connect(String serverName, int serverPort)
    {
        System.out.println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            open();
            return true;
        }
        
        catch (UnknownHostException uhe)
        {
        	System.err.println("Host unknown: " + uhe.getMessage());
        	return false;
        }
        
        catch (IOException ioe)
        {
        	System.err.println("Unexpected exception: " + ioe.getMessage());
        	return false;
        }
    }
	
    /**
     * Method to Send Question object to the server.
     * 
     * @param q
     * The Question object to send.
     */
	public void send(Question q)
	{
        try
        {
        	System.out.println("Trying to send data: " + q.toString());
        	streamOut.writeObject(q);
            streamOut.flush();
        }
        catch (IOException ioe)
        {
    		System.err.println("Sending error: " + ioe.getMessage());
        }
	}
    
	/**
	 * default Method to handle receipt of data from server.
	 * 
	 * @param q
	 * The Question object received.
	 */
    public void handle(Question q)
    {
        	System.out.println("DEFAULT FRAME HANDLING: " + q.toString());
    }
	
    /**
     * Method to open the Output stream and thread for communication.
     */
	public void open()
    {
        try
        {
            streamOut = new ObjectOutputStream(socket.getOutputStream());
            client = new ClientThread(this, socket);
        }
        catch (IOException ioe)
        {
            System.err.println("Error opening output stream: " + ioe);
        }
    }
	
	/**
	 * Method to close any open Streams and client Threads.
	 */
	public void close()
	{
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            System.err.println("Error closing ...");
        }
        client.close();
        client.stop();
	}
}