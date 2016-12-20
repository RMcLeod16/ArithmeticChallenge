package arithmeticchallenge;

import java.io.*;
import java.net.*;

/**
 * Class to build a NetServer to send and receive Questions between clients.
 * 
 * @author Russell McLeod
 */
public class NetServer implements Runnable
{
	/**
	 * Array of Server Threads to accomodate Client.
	 */
	private NetServerThread clients[] = new NetServerThread[50];
	
	/**
	 * The Socket for the server to use to communicate.
	 */
	private ServerSocket server = null;
	
	/**
	 * Main Thread for the Server.
	 */
	private Thread thread = null;
	
	/**
	 * The count of connected clients.
	 */
	private int clientCount = 0;

	/**
	 * Default Constructor for NetServer.
	 * 
	 * @param port
	 * The network port to connect over.
	 */
	public NetServer(int port)
	{
		try
		{
			System.out.println("Binding to port " + port + ", Please wait...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start();
			
		}
		catch (IOException ioe)
		{
			System.err.println("Could not bind to the port " + port + ": " + ioe.getMessage());
		}
	}
	
	/**
	 * Method to get the Server running..
	 */
	public void run()
	{
		while (thread != null)
		{
			try
			{
				System.out.println("Waiting for client...");
				addThread(server.accept());
			}
			catch (IOException ioe)
			{
				System.err.println("Server accept error: " + ioe.getMessage());
				stop();
			}
		}
	}

	/**
	 * Method to start the thread.
	 */
	public void start()
	{
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * Method to stop the thread if it is running.
	 */
	public void stop()
	{
		if (thread != null)
		{
			thread.stop();
			thread = null;
		}
	}
	
	/**
	 * Method to find the index of a client matching a specified ID.
	 * 
	 * @param ID
	 * The ID of the client to search for.
	 * @return
	 * The clients array index holding that client if found, otherwise return -1 if not found.
	 */
	public int findClient(int ID)
	{
		for (int i = 0; i < clientCount; i++)
			if (clients[i].GetID() == ID)
				return i;
		return -1;
	}
	
	/**
	 * Method to handle incoming data from clients.
	 * 
	 * @param ID
	 * The ID of the Thread sending the data.
	 * 
	 * @param _question
	 * The Question object being received by client.
	 */
	public synchronized void handle(int ID, Question _question)
	{
		for (int i = 0; i < clientCount; i++)
			clients[i].send(_question);
	}
	
	/*
	 * 	private synchronized void remover(int ID)

		position = find the client with specified id
		if that position is greater or equal to 0
			the serverthread we want to terminate is the thread at specified positioon in array
			print message saying that this thread is being removed
		
			if the position is not the last one in array
				move every positing after current one forward by one
		
			decrement the count by one
		
			then try to close the thread we are trying to terminate
			but print error message if that fails
		
			then stop that thread
	 */
	/**
	 * Method to remove a client thread.
	 * 
	 * @param ID
	 * ID of the client to remove.
	 */
	public synchronized void remove(int ID)
	{
		int pos = findClient(ID);
		
		if (!(pos < 0))
		{
			NetServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at position " + pos);
			
			if (pos < clientCount - 1)
				for (int i = pos + 1; i < clientCount; i++)
					clients[i - 1] = clients[i];
			
			clientCount--;
			
			try
			{
				toTerminate.close();
			}
			catch (IOException ioe)
			{
				System.err.println("Error closing thread: " + ioe.getMessage());
			}
			
			toTerminate.stop();
		}
	}
	
	/*
	 * 	private void addThread(Socket _socket)

		if the client count is smaller than the length of array
			print message asying a client is accepted
			last client thread in array = new serverthread(this, _socket)
		
			then try to
			open and start this thread
			but print error message about open ing it if thjat fails
		else
			print message sayign that it's ben refused since max amount has been reached
	
	 */
	
	/**
	 * Method to add a new Server thread for a client.
	 * 
	 * @param _socket
	 * The socket to communicate over.
	 */
	public void addThread(Socket _socket)
	{
		if (clientCount < clients.length)
		{
			System.out.println("Client accepted: " + _socket);
			clients[clientCount] = new NetServerThread(this, _socket);
			
			try
			{
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			}
			catch (IOException ioe)
			{
				System.err.println("Error opening thread: " + ioe.getMessage());
			}
		}
		else
		{
			System.out.println("Client refused, max amount of clients (" + clients.length + ") reached.");
		}
	}
	
	/**
	 * Main Method to construct the server and start it up.
	 * 
	 * @param args
	 * Default args, not used.
	 */
	public static void main(String args[])
	{
		NetServer _server;
		
		if (args.length != 1)
			_server = new NetServer(4444);
		else
			_server = new NetServer(Integer.parseInt(args[0]));
	}
}
