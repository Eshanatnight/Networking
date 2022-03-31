package com.kells.StopAndWait;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    Socket socket;
    Socket socket_stopAndwait;
    DataInputStream istream_counter;
    DataInputStream data_istream;
    DataOutputStream ostream;
    String data;
    int m_port;
    int counter;


    Server(int _port)
    {
        m_port = _port;
    }


    // Initializes the socket and istream
    private void initComponents()
    {
        try
        {
            socket = new ServerSocket(m_port).accept();
            istream_counter = new DataInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.err.println("Exception in initComponents method" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    // gets the counter variable
    private void getDataFromStream()
    {
        try
        {
            counter = istream_counter.read();
        }

        catch (IOException e)
        {
            System.err.println("Exception in getDataFromStream method" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    // Prints out the acknowledgement
    private void Acknowledgement(int i)
    {
        System.out.println("Acknowledgement sent for " + i);
    }


    // the actual logic behind the stop and wait
    private void stopAndwait()
    {
        for(int i = 1; counter >= i; ++i)
        {
            try
            {
                socket_stopAndwait = new ServerSocket(9000 + i).accept();
                data_istream = new DataInputStream(socket_stopAndwait.getInputStream());
                data = data_istream.readUTF();
                System.out.println("Transmitted Data: " + data);
                System.out.printf("Frame %d received", i);

                ostream = new DataOutputStream(socket_stopAndwait.getOutputStream());

                Thread.sleep(1000);

                ostream.write(i);

                Acknowledgement(i);
            }

            catch (IOException | InterruptedException e)
            {
                System.err.println("Exception in stopAndwait method" + e.getLocalizedMessage());
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



    // closes the streams
    private void closeStreams()
    {
        try
        {
            istream_counter.close();
        }

        catch (IOException e)
        {
            System.err.println("Exception in closeStreams method" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    // All in one run function
    private void run()
    {
        initComponents();
        getDataFromStream();
        stopAndwait();
        closeStreams();
    }

    public static void main(String[] args)
    {
        var server = new Server(8000);
        server.run();
    }
}
