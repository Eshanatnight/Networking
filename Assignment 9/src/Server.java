import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ClientHandler implements Runnable
{
	private Socket m_client;
	PrintWriter out;
	BufferedReader in;

	ClientHandler(Socket client)
	{
		this.m_client = client;
	}

	private void initComponents()
	{
		try
		{
			out = new PrintWriter(this.m_client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(this.m_client.getInputStream()));
		}

		catch (IOException e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	private void deinitComponents()
	{
		try
		{
			if (out != null)
			{
				out.close();
			}

			if (in != null)
			{
				in.close();
			}
		}

		catch (IOException e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void run()
	{
		initComponents();

		try
		{
			String line;
			while ((line = in.readLine()) != null)
			{
				// writing the received message from client
				System.out.printf("Sent from the client: %s\n", line);
				out.println(line);
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			deinitComponents();
		}
	}
}

public class Server
{
	ServerSocket m_socket;
	int m_port;

	Server(int _port)
	{
		System.out.println("Server started...");
		this.m_port = _port;
	}

	void initComponents()
	{
		try
		{
			m_socket = new ServerSocket(m_port);
			m_socket.setReuseAddress(true);
		}
		catch (IOException e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void main(String[] args)
	{
		Server server = new Server(5150);
		server.initComponents();

		try
		{
			// running infinite loop for getting client request
			while (true)
			{
				Socket client = server.m_socket.accept();

				// Displaying that new client is connected to server
				System.out.println("New client connected" + client.getInetAddress().getHostAddress());

				// create a new thread
				ClientHandler clientSock = new ClientHandler(client);
				new Thread(clientSock).start();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (server != null)
			{
				try
				{
					server.m_socket.close();
				}

				catch (IOException e)
				{
					System.out.println(e.getLocalizedMessage());
				}
			}
		}
	}
}