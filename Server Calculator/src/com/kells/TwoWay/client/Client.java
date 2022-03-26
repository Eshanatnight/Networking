package com.kells.TwoWay.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    InetAddress IPAddress;
    int port;
    Scanner scanner;
    Socket socket;
    DataInputStream istream;
    DataOutputStream ostream;

    public Client(InetAddress IPAddress, int port)
    {
        this.IPAddress = IPAddress;
        this.port = port;
    }

    private void initServer()
    {
        try
        {
            scanner = new Scanner(System.in);
            socket = new Socket(IPAddress, port);
            istream = new DataInputStream(socket.getInputStream());
            ostream = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e)
        {
            System.err.println("Error:" + e.getLocalizedMessage());
        }
    }

    private void sendToServer()
    {
        System.out.println("Enter Operands and Operator in the form: ");
        System.out.println("'operand operator operand' and Operators available='+','-','/','*'");
        String input = scanner.nextLine();

        try
        {
            ostream.writeUTF(input);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void receiveFromServer()
    {
        try
        {
            String input = istream.readUTF();
            System.out.println("Calculated Result from Server: " + input);
        }
        catch (Exception e)
        {
            System.err.println("Error:" + e.getLocalizedMessage());
        }
    }

    private void closeConnection()
    {
        try
        {
            socket.close();
            scanner.close();
            istream.close();
            ostream.close();
        }
        catch (Exception e)
        {
            System.err.println("Error:" + e.getLocalizedMessage());
        }
    }

    public void run()
    {
        initServer();
        sendToServer();
        receiveFromServer();
    }

    public static void main(String[] args)
    {
        try
        {
            var client = new Client(InetAddress.getLocalHost(), 5050);
            client.run();
        }
        catch (Exception e)
        {
            System.err.println("Error:" + e.getLocalizedMessage());
        }
    }
}
