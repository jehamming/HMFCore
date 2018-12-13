package nl.hmf.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

import nl.hmf.interfaces.IConstants;
import nl.hmf.util.Log;


/*
 * For inter JVM communication
 */
public class TcpIpMessageSender implements Runnable {
    boolean running = false;

    private Vector<String> queue = new Vector<String>();
    
    private int enqueued = 0;
    private int dispatched = 0;
    
    private Thread dispatcherThread;
    
    private String destinationHost;
    private Socket sendSocket = null;


    
    public TcpIpMessageSender( String _destinationHost ) {
        dispatcherThread = new Thread(this);
        dispatcherThread.setDaemon(true);
        dispatcherThread.start();
        
        destinationHost = _destinationHost;

    }
    
    public void dispatchMessage(String xml) {
        queue.add( xml );
        enqueued++;
        synchronized (this) {
            this.notify();
        }
        

    }

    public void initializeBot() {
    }
    
    public void stopServer(){
        if (running) {
            running = false;
        }
    }

    public void run() {
        try {
            PrintWriter out = null;
            BufferedReader in = null;

            
            sendSocket = new Socket(destinationHost, IConstants.TCP_SERVER_PORT);
            out = new PrintWriter(sendSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sendSocket.getInputStream()));
            
            running = true;
            
            Log.debug("TCP/IP MessageClient live connection to " + destinationHost);
            while (running) {
                if (queue.isEmpty()) {
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        Log.debug(getClass().getName() + ": Exception : method wait was interrupted!");
                    }
                }
                // Do work
                Enumeration<String> e = queue.elements();
                while (e.hasMoreElements()) {
                    String xml =  e.nextElement();
                    Log.debug("TCP/IP MessageClientNanobot sending -> " + xml);
                    out.println( xml );
                    queue.remove(xml);
                    dispatched++;
                }

            }
            out.close();
            in.close();
            sendSocket.close();

            running = false;
            Log.debug("TCP/IP MessageClient Nanobot deceased");
        } catch (UnknownHostException e) {
            Log.debug("MessageClient: Don't know about host: " + destinationHost);
            System.exit(1);
        } catch (IOException e) {
            Log.debug("MessageClient : Couldn't get I/O for the connection.");
        } 
    }


    public boolean isRunning() {
        return running;
    }


}
