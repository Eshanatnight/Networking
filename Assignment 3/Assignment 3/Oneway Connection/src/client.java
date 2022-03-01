import java.net.*;
import java.rmi.UnknownHostException;
import java.util.Scanner;
import java.io.*;

    public class client
    {

        private Socket m_socket = null;
        private DataInputStream m_istream = null;
        private DataOutputStream m_ostream = null;
        private PrintWriter m_writer = null;

        private String m_address;
        private int m_port;
        private String m_message;


        private void initConnection()
        {
            try
            {
                this.m_socket = new Socket(m_address, m_port);
                System.out.println("Conected!");

                this.m_istream = new DataInputStream(new BufferedInputStream(this.m_socket.getInputStream()));
                this.m_ostream = new DataOutputStream(m_socket.getOutputStream());
            }

            catch (UnknownHostException e)
            {
                System.err.println("Unknown host: " + this.m_address);
                System.err.println(e.getLocalizedMessage());
            }

            catch (IOException e)
            {
                System.err.println("Couldn't get I/O for the connection to: " + this.m_address);
                System.err.println(e.getLocalizedMessage());
            }
        }


        private void sendMessage()
        {
            m_message = "Hello World";
            m_writer = new PrintWriter(m_ostream, true);
            m_writer.println(m_message);
        }


        private void parseMessage()
        {
            while(true)
            {
                if(m_message.equals("Over"))
                {
                    m_writer.println(m_message);
                }

                else
                {
                    m_writer.println(m_message);
                }
        }
        }


        private void sendMessageFromConsole()
        {
            try (var scanner = new Scanner(System.in))
            {
                m_message = scanner.nextLine();
                m_writer = new PrintWriter(m_ostream, true);
                parseMessage();

            }
        }


        public void run()
        {
            initConnection();
            sendMessage();

            if (false)
            {
                sendMessageFromConsole();
            }

            closeConnection();
        }

        private void closeConnection()
        {
            try
            {
                m_istream.close();
                m_ostream.close();
                m_socket.close();
            }

            catch(Exception i)
            {
                System.out.println(i.getLocalizedMessage());
            }

        }


        public client(String address, int port)
        {
            this.m_address = address;
            this.m_port = port;
        }

        public static void main(String[] args)
        {
            client client = new client("localhost", 5050);
            client.run();
        }
}