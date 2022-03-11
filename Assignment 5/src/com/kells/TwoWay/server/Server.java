package com.kells.TwoWay.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.kells.math.MathStruct;

public class Server
{
    int port;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream istream;
    DataOutputStream ostream;
    String input;
    MathStruct math;
    int result;

    Server(int _port)
    {
        port = _port;
    }

    public void initServer()
    {
        System.out.println("Server Starting...");
        try
        {
            serverSocket = new ServerSocket(this.port);

            // Accept the connection of the client
            socket = serverSocket.accept();
            System.out.println("Client connected" + socket.getInetAddress());

            istream = new DataInputStream(socket.getInputStream());
            ostream = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getLocalizedMessage());
        }
    }

    private void readFromClient()
    {
        try
        {
            this.input = this.istream.readUTF();
            System.out.println("Client: " + this.input);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getLocalizedMessage());
        }
    }

    private void calculate()
    {
        this.math = new MathStruct(this.input);
        this.result = this.math.calculate();
    }

    private void sendToClient()
    {
        try
        {
            this.ostream.writeUTF(this.result + "");
            //this.ostream.writeUTF(Integer.toString(this.result));
            this.ostream.flush();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getLocalizedMessage());
        }
    }

    private void closeConnection()
    {
        try
        {
            this.istream.close();
            this.ostream.close();
            this.socket.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getLocalizedMessage());
        }
    }

    public void start()
    {
        this.initServer();
        while (true)
        {
            this.readFromClient();
            this.calculate();
            this.sendToClient();

            try
            {
                this.ostream.writeUTF("\n Do you want to continue? (y/n)");
                this.ostream.flush();
                String choice = this.istream.readUTF();

                if (choice.equals("n") || choice.equals("N"))
                {
                    this.closeConnection();
                    break;
                }

            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getLocalizedMessage());
            }

        }
    }

    // Difference between run and start method
    // start would activate the server and run until a breakpoint is reached
    // run would only run the server once
    public void run()
    {
        initServer();
        readFromClient();
        calculate();
        sendToClient();
        closeConnection();
    }

    public static void main(String[] args)
    {
        Server server = new Server(5050);
        server.run();
    }
}
