import ocsf.client.AbstractClient;

import java.io.*;

public class myClient extends AbstractClient{

    public myClient(String host, int port) {
        super(host, port);
        try {
            this.openConnection();
        } catch (IOException e) {
            System.out.println("Error: Failed to connect to server. E: "+e.getMessage());
        }
    }

    private void acceptInput() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String msg;

            System.out.println("Commands (case-sensitive): whoAMi, startEcho, stopEcho, bye.");

            while (true) {
                System.out.print(">");
                msg = fromConsole.readLine();
                this.handleMessageFromConsole(msg);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
    }

    private void handleMessageFromConsole(String msg) {
        if (this.isConnected()) {
            try {
                this.sendToServer(msg);
            } catch(IOException e) {
                System.out.println("Error: Could not send message to server. E: "+e.getMessage());
            }
        } else {
            System.out.println("Not connected to server");
        }
    }

    public void handleMessageFromServer(Object msg) {

        System.out.println("Server> "+msg.toString());

        if (msg.toString().equals("bye")) {
            try {
                this.quit();
            } catch (IOException e) {
                System.out.println("Error: Failed to disconnect. E: "+e.getMessage());
            }
        }

        System.out.print(">");
    }

    public void quit() throws IOException {
        System.out.println("Disconnecting...");
        this.closeConnection();
        System.out.println("Disconnected successfully, terminating...");
        System.exit(0);
    }

    public static void main(String [] args) {
        String host = "localhost";
        int port = 5555;

        try {
            host = args[0];
            System.out.println("Setting host as "+host);
        } catch (ArrayIndexOutOfBoundsException ie) {
            System.out.println("Failed to read host, setting default host: "+host);
        }

        try {
            port = Integer.parseInt(args[1]);
            System.out.println("Setting port as "+port);
        } catch (ArrayIndexOutOfBoundsException ie) {
            System.out.println("Failed to read port, setting default port: "+port);
        }

        myClient client = new myClient(host, port);
        client.acceptInput();
    }

}
