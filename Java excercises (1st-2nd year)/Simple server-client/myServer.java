/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
 */

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.io.*;

public class myServer extends AbstractServer{

    boolean echoMode = false;
    PrintWriter log;

    public myServer(int port) {
        super(port);
        try {
            this.log = new PrintWriter("server-log.txt", "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void serverStarted() {
        System.out.println("Server listening for connections on port "+getPort());
        this.log.println("Server listening for connections on port "+getPort());
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
        this.log.println("Server has stopped listening for connections.");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.println("Client connected: "+client);
        this.log.println("Client connected: "+client);
    }

    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        System.out.println("Client Disconnected: "+client);
        this.log.println("Client Disconnected: "+client);
    }

    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (this.echoMode) {
            if (msg.toString().equals("stopEcho")) {
                this.echoMode = false;
                System.out.println("Echo mode is off.");
                this.log.println("Echo mode is off.");
            } else {
                try {
                    client.sendToClient(msg);
                    System.out.println("Echoed message '"+msg+"' to client: "+client+".");
                    this.log.println("Echoed message '"+msg+"' to client: "+client+".");
                } catch (IOException e) {
                    System.out.println("Error: Failed to echo message to client. E: "+e.getMessage());
                    this.log.println("Error: Failed to echo message to client. E: "+e.getMessage());
                }
            }
        } else {
            if (msg.toString().equals("startEcho")) {
                this.echoMode = true;
                System.out.println("Echo mode is on.");
                this.log.println("Echo mode is on.");
            } else if (msg.toString().equals("whoAMi")) {
                try {
                    client.sendToClient(client.getInetAddress());
                    System.out.println("Echoed client IP to client: "+client+".");
                    this.log.println("Echoed client IP to client: "+client+".");
                } catch (IOException e) {
                    System.out.println("Error: Failed to send message to client. E: "+e.getMessage());
                }
            } else if (msg.toString().equals("stopEcho")) {
                System.out.println("Received stopEcho command when echo mode is off.");
                this.log.println("Received stopEcho command when echo mode is off.");
            } else if (msg.toString().equals("bye")) {
                try {
                    client.sendToClient(msg);
                    System.out.println("Echoed 'bye' message to client: "+client+".");
                    this.log.println("Echoed 'bye' message to client: "+client+".");
                } catch (IOException e) {
                    System.out.println("Error: Failed to send message to client. E: "+e.getMessage());
                    this.log.println("Error: Failed to send message to client. E: "+e.getMessage());
                }
            } else {
                System.out.println("Received invalid command: "+msg+", from client: "+client+".");
                this.log.println("Received invalid command: "+msg+", from client: "+client+".");
            }
        }
    }

    public static void main(String[] args) {
        int port = 5555;

        try {
            port = Integer.parseInt(args[0]);
            System.out.println("Setting port as "+port);
        } catch (ArrayIndexOutOfBoundsException ie) {
            System.out.println("Failed to read port, setting default port: "+port);
        }

        myServer server = new myServer(port);
        server.log.println("Server port is "+port);

        try {
            server.listen();
        } catch (IOException e) {
            System.out.println("Error: Failed to listen for clients. E: "+e.getMessage());
            server.log.println("Error: Failed to listen for clients. E: "+e.getMessage());
        }
    }

}
