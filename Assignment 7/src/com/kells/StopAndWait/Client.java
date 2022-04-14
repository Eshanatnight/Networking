package com.kells.StopAndWait;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    Scanner scanner;
    Socket socket;
    Socket socket_stopAndwait;
    DataOutputStream ostream;
    DataOutputStream ostrem_stopAndwait;
    DataInputStream istream_stopAndwait;
    int m_port;
    int frameNumber;
    String message;
    int recivedData;

    Client(int _port)
    {
        System.out.println("Client Initialized...");
        m_port = _port;
    }

    private void initComponents()
    {
        scanner = new Scanner(System.in);
    }


    private void getNumberofFrames()
    {
        initComponents();
        System.out.print("Enter Number of Frames: ");
        frameNumber = scanner.nextInt();
    }


    private void sendData()
    {
        if (frameNumber == 0)
        {
            System.out.println("No frames to send");
            return;
        }

        try
        {
            socket = new Socket("localhost", m_port);
            ostream = new DataOutputStream(socket.getOutputStream());
            ostream.write(frameNumber);
        }

        catch (IOException e)
        {
            System.err.println("Exception in sendData method " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void Acknowledgement(int i)
    {
        System.out.println("Acknowledgement for : " + recivedData + " : " + i + " is  received");
    }

    private void stopAndwait()
    {
        for (int i = 1; i <= frameNumber; ++i)
        {
            System.out.print("Enter message : ");
            message = scanner.next();
            System.out.println("Frame " + i + " is sent");
            try
            {
                socket_stopAndwait = new Socket("localhost", 9000 + i);
                ostrem_stopAndwait = new DataOutputStream(socket_stopAndwait.getOutputStream());
                ostrem_stopAndwait.writeUTF(message);
                istream_stopAndwait = new DataInputStream(socket_stopAndwait.getInputStream());
                recivedData = istream_stopAndwait.read();
                Acknowledgement(i);
            }

            catch (IOException e)
            {
                System.err.println("Exception in stopAndwait method " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        try
        {
            socket_stopAndwait.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception in stopAndwait method " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    private void closeConnections()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception in closeConnections method " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void run()
    {
        getNumberofFrames();
        sendData();
        stopAndwait();
        closeConnections();
    }

    public static void main(String[] args)
    {
        var client = new Client(8000);
        client.run();
    }
}
