package com.kells.Multi;

import java.net.*;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        InetAddress group = InetAddress.getByName("225.6.7.8");
        MulticastSocket socket = new MulticastSocket(3456);
        socket.joinGroup(group);
        byte[] buffer = new byte[100];
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        socket.receive(dp);
        System.out.println(new String(buffer));
        socket.close();
    }
}
