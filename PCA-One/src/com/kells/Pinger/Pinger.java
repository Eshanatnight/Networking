package com.kells.Pinger;

import java.net.InetAddress;

public class Pinger
{
    private InetAddress address;
    private InetAddress selfAddress;

    private void initiate(String IP)
    {
        try
        {
            address = InetAddress.getByName(IP);
            selfAddress = InetAddress.getLocalHost();
        }
        catch (Exception e)
        {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        System.out.println("Pinging " + IP + "...");
    }

    public boolean isReachable(int timeout)
    {
        try
        {
            return address.isReachable(timeout);
        }
        catch (Exception r)
        {
            System.err.println(r.getLocalizedMessage());
            r.printStackTrace();
        }

        return false;
    }

    public void pingIP(String IP)
    {
        initiate(IP);

        if (isReachable(1000))
        {
            System.out.println("Host is reachable");
        }
        else
        {
            System.out.println("Host is not reachable");
        }
    }

    private void printIP()
    {
        // Print the IP Of the Host
        System.out.println("\nHost: " + address.getHostAddress());

        // Print The IP of the Remote
        System.out.println("Self: " + selfAddress.toString());
    }

    public static void main(String[] args)
    {
        String address = "127.0.0.1";
        Pinger pingThis = new Pinger();
        pingThis.pingIP(address);
        pingThis.printIP();
    }

}


