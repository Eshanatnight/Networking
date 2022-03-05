import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client
{
    private Scanner m_scanner = null;
    private DatagramSocket m_socket = null;
    private byte[] m_dataToSend = null;
    private InetAddress m_ip = null;
    private DatagramPacket m_Sendpacket = null;
    private DatagramPacket m_Recvpacket = null;
    private int m_port ;

    public Client(int port)
    {
        m_port = port;
    }

    private void initComponents()
    {
        System.out.println("Initializing Network....");
        System.out.println("Connecting to Server....");
        m_scanner = new Scanner(System.in);
        System.out.println("Enter any number : ");
        m_dataToSend = String.valueOf(m_scanner.nextInt()).getBytes();

        try
        {
            m_socket = new DatagramSocket();
            m_ip = InetAddress.getLocalHost();
            m_Sendpacket = new DatagramPacket(m_dataToSend, m_dataToSend.length, m_ip, m_port);
        }
        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void sendData()
    {

        m_Sendpacket.setData(m_dataToSend);

        try
        {
            m_socket.send(m_Sendpacket);
        }
        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void receiveData()
    {
        byte[] data = new byte[1024];
        m_Recvpacket = new DatagramPacket(data, data.length);

        try
        {
            m_socket.receive(m_Recvpacket);
        }
        catch (Exception e)
        {
            System.err.println("Exception Occurred: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        String str = new String(m_Recvpacket.getData());

        System.out.println("The Computed data of cube of the number from the server side is " + str);
    }


    public void run()
    {
        initComponents();
        sendData();
        receiveData();
    }


    public static void main(String[] args)
    {
        Client client = new Client(4949);
        client.run();
    }

}
