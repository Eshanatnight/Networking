import java.net.InetAddress;

public class IPAddress
{

    static void argsCheck(int argsCount)
    {
        if (argsCount != 1)
        {
            System.out.println("Usage: java IPAddress <IP Address>");
            System.exit(0);
        }
    }

    static void printLocalIP(String name)
    {
        try
        {
            System.out.println("Local IP Address: " + InetAddress.getByName(name));
        }

        catch (Exception e)
        {
            System.err.println(e.getLocalizedMessage());
        }
    }

    static void printHostIP()
    {
        try
        {
            InetAddress hostAddress = InetAddress.getLocalHost();

            System.out.println("Hostname: " + hostAddress.getHostName());
            System.out.println("Host Address: " + hostAddress.getHostAddress());
        }

        catch (Exception e)
        {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args)
    {
        argsCheck(args.length);

        printLocalIP(args[0]);

        printHostIP();
    }
}
