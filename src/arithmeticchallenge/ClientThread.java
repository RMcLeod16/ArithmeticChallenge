package arithmeticchallenge;

import java.io.*;
import java.net.*;

/**
 * Class to build ClientThreads for Client Frames to interact with a server.
 * 
 * @author Russell McLeod
 */
public class ClientThread extends Thread
{
	/**
	 * The Socket to connect through.
	 */
	private Socket socket = null;
	
	/**
	 * The NetFrame corresponding to this ClientThread.
	 */
	private NetFrame client = null;
	
	/**
	 * The Input Stream to receive data from.
	 */
    private ObjectInputStream streamIn = null;
    
    /**
     * Default Constructor for ClientThread.
     * 
     * @param _client
     * The Client NetFrame for this Thread.
     * 
     * @param _socket
     * The Socket for this Thread to communicata over.
     */
    public ClientThread(NetFrame _client, Socket _socket)
    {
    	client = _client;
        socket = _socket;
        open();
        start();
    }
    
    /**
     * Method to open the ClientThread's Input Stream.
     */
    public void open()
    {
    	try
    	{
    		streamIn = new ObjectInputStream(socket.getInputStream());
    	}
    	catch (IOException ioe)
    	{
    		System.err.println("Error getting the input stream: " + ioe.getMessage());
    	}
    }
    
    /**
     * Method to close the ClientThread's Input Stream.
     */
    public void close()
    {
    	try
    	{
    		if (streamIn != null)
    			streamIn.close();
    	}
    	catch (IOException ioe)
    	{
    		System.err.println("Error closing input stream: " + ioe.getMessage());
    	}
    }
    
    
    /*while (true)
	try to
		thread.handle the Utf read stream data
	but print error and close thread if fail
	*/
    /**
     * Method to run the ClientThread.
     */
    public void run()
    {
    	while (true)
    	{
    		
    		try
    		{
    			client.handle((Question)streamIn.readObject());
    		}
    		catch (IOException ioe)
    		{
    			System.err.println("Listening error: " + ioe.getMessage());
    			client.close();
    		}
    		catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
    	}
    }
}
