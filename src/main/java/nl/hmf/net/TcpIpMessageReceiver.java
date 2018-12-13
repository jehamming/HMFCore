package nl.hmf.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import nl.hmf.interfaces.IConstants;
import nl.hmf.util.Log;


/*
 * For TCP/IP based communication
 */
public class TcpIpMessageReceiver implements Runnable {
    boolean running = false;

    private Thread dispatcherThread;
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private MessageReciever reciever;

    public TcpIpMessageReceiver( MessageReciever _reciever ) {
        dispatcherThread = new Thread(this);
        dispatcherThread.setDaemon(true);
        dispatcherThread.start();
        reciever = _reciever;
    }

    public void initializeBot() {
    }

    public void stopServer() {
        if (running) {
            running = false;
        }
    }

    public void run() {
        try {

            serverSocket = new ServerSocket(IConstants.TCP_SERVER_PORT);
            Log.debug("TCP/IP Message Receiver Nanobot is alive");
            running = true;

            BufferedReader in = null;

            while (running) {

                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String xml = null;
                while ((xml = in.readLine()) != null) {
                	reciever.recieveMessage(xml);
                }
            }
            in.close();

        } catch (Exception e) {
            Log.error("Exception : " + e);
            e.printStackTrace();
        }

    }

    public boolean isRunning() {
        return running;
    }

}
