// One Way Socket Server -- Prints whatever the client sends to the server.
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class server
{
	private int m_port;
	private Socket m_socket = null;
	private ServerSocket m_server = null;
	private BufferedReader m_InputReader = null;


	// Print the message from the client
	private void printData()
	{
		try
		{
			// takes input from the client socket
			m_InputReader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));

			String line = m_InputReader.readLine();
			System.out.println("Client: " + line);
		}

		catch (Exception e)
		{
			System.err.println("Exception Occured in Reading the message from the client: " + e.getLocalizedMessage());
		}
	}


	// Initialize the Components and Initialize the Connection
	private void initConnection(int port)
	{
		try
		{
			System.out.println("Server started" + " on port: " + m_port);
			System.out.println("Waiting for a client ...");

			m_socket = m_server.accept();
			System.out.println("Client accepted");

			this.printData();
		}

		catch(Exception e)
		{
			System.err.println("Error in initializing connection: " + e.getLocalizedMessage());
		}
	}


	// Close the Connections
	private void closeConnection()
	{
		try
		{
			System.out.println("Closing connection....");
			this.m_socket.close();
			this.m_InputReader.close();
			this.m_server.close();
		}

		catch (Exception e)
		{
			System.err.println("Exception Occured: " + e.getLocalizedMessage());
		}
	}

	// run function
	public void run()
	{
		this.initConnection(this.m_port);
		// close connection
		this.closeConnection();
	}


	public server(int port)
	{
		try
		{
			this.m_server = new ServerSocket(port);
		}

		catch (Exception e)
		{
			System.err.println("Exception Occured: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		this.m_port = port;
	}

	public static void main(String args[])
	{
		server server = new server(5050);
		server.run();
	}
}