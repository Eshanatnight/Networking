import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Server
{
    ServerSocket m_serverSocket = null;
    Socket m_clientSocket = null;
    BufferedReader m_reader = null;
    PrintWriter m_writer = null;
    Scanner m_scanner = null;

    private String m_address;
    private int m_port;

    String m_inMessage;
    String m_outMessage;


    public Server(String address, int port)
    {
        this.m_address = address;
        this.m_port = port;
    }


    private void initComponents()
    {
        try
        {
            this.m_serverSocket = new ServerSocket(this.m_port);
            this.m_clientSocket = m_serverSocket.accept();
            System.out.println("Connection Established at: " + m_clientSocket.getInetAddress() + ":" + m_clientSocket.getPort());
            this.m_reader = new BufferedReader(new InputStreamReader(m_clientSocket.getInputStream()));
            this.m_writer = new PrintWriter(m_clientSocket.getOutputStream());
            m_scanner = new Scanner(System.in);
        }

        catch(Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void getMessageFromClient()
    {
        try
        {
            this.m_inMessage = m_reader.readLine();
            System.out.println("Client: " + m_inMessage);
        }
        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void sendMessageToClient()
    {
        try
        {
            this.m_outMessage = this.m_scanner.nextLine();
            m_writer.println("Server: " + m_outMessage);
            m_writer.flush();
        }

        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void continousCommunication()
    {
        while(true)
        {
            this.getMessageFromClient();
            if(this.m_inMessage.equals("Over"))
            {
                break;
            }

            this.sendMessageToClient();
        }
    }


    private void closeConnection()
    {
        try
        {
            System.out.println("Closing Connection....");
            m_reader.close();
            m_writer.close();
            m_clientSocket.close();
            m_serverSocket.close();
        }

        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void run()
    {
        this.initComponents();
        this.sendMessageToClient();
        this.getMessageFromClient();
//        this.continousCommunication();
        this.closeConnection();

    }

    public static void main(String[] args) throws Exception
    {
        Server server = new Server("localhost", 5000);
        server.run();
    }

}