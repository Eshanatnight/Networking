import java.net.InetSocketAddress;
import java.net.Socket;

class PortMafia
{
    public static void main(String[] args)
    {
        int portIndexStart;
        int portIndexEnd;

        portIndexStart = Integer.parseInt(args[0]);
        portIndexEnd = Integer.parseInt(args[1]);

        System.out.println("PortMafia : " + args[0] + " " + portIndexStart + " " + portIndexEnd);

        for(int i = 1; i < 65535; i++)
        {
            try
            {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", i));
                socket.close();
                System.out.println("Port " + i + " is open");
            }

            catch (Exception e)
            {}
        }
    }
}