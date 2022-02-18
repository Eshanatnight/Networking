import java.net.InetAddress;
// Ping Program for Java
public class ping
{
    public static void pingIP(String IP)
    {
        try
        {
            InetAddress address = InetAddress.getByName(IP);
            System.out.println("Pinging " + IP + "...");
            if (address.isReachable(5000))
            {
                System.out.println(IP + " is reachable");
            }
            else
            {
                System.out.println(IP + " is not reachable");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public static void main(String[] args) throws Exception
    {
        try
        {
            String address = "127.0.0.1";
            pingIP(address);
        }

        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
