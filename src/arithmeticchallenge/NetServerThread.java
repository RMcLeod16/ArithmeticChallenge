package arithmeticchallenge;

import java.io.*;
import java.net.Socket;

/**
 * Class to build a NetServerThread for the NetServer to communicate with Client Frames.
 * 
 * @author Russell McLeod
 */
public class NetServerThread extends Thread
{
	/**
	 * The NetServer for this NetServerThread
	 */
	private NetServer server = null;
	
	/**
	 * The Socket to communicate over.
	 */
	private Socket socket = null;
	
	/**
	 * The ID of the Thread.
	 */
	private int ID = -1;
	
	/**
	 * The Input Stream for receiving data.
	 */
	private ObjectInputStream streamIn = null;
	
	/**
	 * The Output Stream for sending Data.
	 */
	private ObjectOutputStream streamOut = null;
	
	/**
	 * Default Constructor for NetServerThread.
	 * 
	 * @param _server
	 * The Server for the Thread.
	 * 
	 * @param _socket
	 * The Socket to communicate over.
	 */
	public NetServerThread(NetServer _server, Socket _socket)
	{
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
	}
	
	/**
	 * Getter method to get the ID of the NetServerThread.
	 * 
	 * @return
	 * The ID of the Thread.
	 */
	public int GetID()
	{
		return ID;
	}
	
	/**
	 * Method to send a Question object to Client Threads.
	 * @param _question
	 */
	public void send(Question _question)
	{
		try
		{
			streamOut.writeObject(_question);
			streamOut.flush();
		}
		catch (IOException ioe)
		{
            System.err.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
		}
	}
	
	/**
	 * Method to Run this Thread.
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				System.out.println("Server Thread " + ID + " running.");
				//server.handle(ID, streamIn.readUTF());
				try
				{
					server.handle(ID, (Question)streamIn.readObject());
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			catch (IOException ioe)
			{
				System.err.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				stop();
			}
		}
	}
	
	/**
	 * Method to Open the Input and Output Streams.
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException
	{
		streamIn = new ObjectInputStream(socket.getInputStream());
		streamOut = new ObjectOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Method to Close any open Sockets and Streams.
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		if (socket != null)
			socket.close();
		
		if (streamIn != null)
			streamIn.close();
		
		if (streamOut != null)
			streamOut.close();
	}
}
