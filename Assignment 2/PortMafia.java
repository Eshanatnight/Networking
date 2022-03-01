import java.net.*;

public class PortMafia
{
    Socket Socket = new Socket();
    boolean isOpened;

    public void run(int startPortIndex, int endPortIndex)
    {
      	//	Loop through all the 65536 ports
        for (int port = startPortIndex; port < endPortIndex; ++port)
        {
          	//	We will assume the current port is opened unless an exception occurs
            isOpened = true;

            try
            {
                Socket = new Socket("127.0.0.1", port);
            }
            catch (Exception e)
            {
                isOpened = false;
            }

          	//	If port is opened log to the console
            if (isOpened)
            {
                System.out.printf("Port %d is open\n", port);
            }
        }
    }


    public static void main(String[] args)
    {
        try
        {
            if(args.length != 2)
            {
                System.out.println("Usage: java PortMafia <portIndexStart> <portIndexEnd>");
                System.exit(1);
            }

            PortMafia portMafia = new PortMafia();
            portMafia.run(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }

        catch(Exception e)
        {
            System.err.println(e.getLocalizedMessage());
        }

    }
}
