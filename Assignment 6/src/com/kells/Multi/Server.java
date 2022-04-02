package com.kells.Multi;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server
{
    public static void main(String[] args) throws Exception
    {
        InetAddress group = InetAddress.getByName("225.6.7.8");
        MulticastSocket socket = new MulticastSocket();
        String msg = "Hello World - Kells";
        DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, 3456);
        socket.send(dp);
        socket.close();
    }
}
