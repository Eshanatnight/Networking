import java.util.*;
import java.io.*;
import java.net.*;

public class Client
{
    private Socket m_socket = null;
    private Scanner m_scanner = null;
    private PrintWriter m_writer = null;

    InputStreamReader m_istream = null;
    BufferedReader m_reader = null;

    private String m_address;
    private int m_port;

    // outMessage refers to the message that is sent to the server
    private String m_outMessage;

    // inMessage refers to the message that is sent to the client
    private String m_inMessage;


    public Client(String address, int port)
    {
        this.m_address = address;
        this.m_port = port;
    }


    private void initComponents()
    {
        try
        {
            this.m_socket = new Socket(this.m_address, this.m_port);
            this.m_scanner = new Scanner(System.in);
            this.m_writer = new PrintWriter(this.m_socket.getOutputStream());
            this.m_istream = new InputStreamReader(this.m_socket.getInputStream());
            this.m_reader = new BufferedReader(this.m_istream);
        }

        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void sendMessageFromConsole()
    {
        this.m_outMessage = this.m_scanner.nextLine();
        this.m_writer.println(m_outMessage);
        this.m_writer.flush();
    }


    private void receiveMessageFromServer()
    {
        try
        {
            this.m_inMessage = this.m_reader.readLine();
            System.out.println(this.m_inMessage);
        }
        catch (IOException e)
        {
            System.out.println("Exception Occured: " + e.getLocalizedMessage());
        }
    }


    private void continousCommunication()
    {
        while (true)
        {
            this.sendMessageFromConsole();
            if(this.m_outMessage.equals("Over"))
            {
                break;
            }

            this.receiveMessageFromServer();
            if(this.m_inMessage.equals("Over"))
            {
                break;
            }
        }
    }


    private void closeConnection()
    {
        try
        {
            this.m_socket.close();
            this.m_scanner.close();
            this.m_writer.close();
            this.m_istream.close();
            this.m_reader.close();
        }
        catch (Exception e)
        {
            System.err.println("Exception Occured: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }


    public void run()
    {
        this.initComponents();
        this.sendMessageFromConsole();
        this.receiveMessageFromServer();
        // this.continousCommunication();
        this.closeConnection();
    }


    public static void main(String[] args)
    {
        Client client = new Client("localhost", 5000);
        client.run();
    }
}