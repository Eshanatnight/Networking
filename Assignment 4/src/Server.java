import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server
{
    private int m_port;
    private DatagramSocket m_socket;
    private byte[] m_dataToReceive;
    private DatagramPacket m_recvpacket;
    private int m_data;
    private byte[] m_result;
    private InetAddress m_address;


    private Server(int port)
    {
        m_port = port;
    }


    private void initComponents()
    {
        System.out.println("Initilaizing Network....");
        try
        {
            m_socket = new DatagramSocket(m_port);
            m_dataToReceive = new byte[1024];
            m_recvpacket = new DatagramPacket(m_dataToReceive, m_dataToReceive.length);
        }
        catch(Exception e)
        {
            System.err.println("Exception Occured: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void receiveData()
    {
        try
        {
            m_socket.receive(m_recvpacket);
            m_data = Integer.parseInt(new String(m_recvpacket.getData()).trim());
        }
        catch(Exception e)
        {
            System.err.println("Exception Occured: " + e.getLocalizedMessage());
            e.printStackTrace();

        }
    }


    private void computeCube()
    {
        m_result = String.valueOf(m_data * m_data * m_data).getBytes();
    }


    private void sendData()
    {
        try
        {
            m_address = InetAddress.getLocalHost();
            m_socket.send(new DatagramPacket(m_result, m_result.length, m_address, m_recvpacket.getPort()));
        }
        catch(Exception e)
        {
            System.err.println("Exception Occured: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Server server = new Server(4949);
        server.initComponents();
        server.receiveData();
        server.computeCube();
        server.sendData();

    }
}
