package nl.hmf.net;

import nl.hmf.interfaces.IXMLConsumer;
import nl.hmf.util.Log;

/*
 * A Message Dispatcher
 */
public class MessageReciever implements Runnable {

    private boolean running = false;
    private boolean recieving = false;
    private Thread recieverThread;
    private MultiCastMessageClient jvmClient = null;
    private IXMLConsumer consumer;

    public MessageReciever(IXMLConsumer consumer) {
        Log.debug("MessageReciever start!");
        recieverThread = new Thread(this);
        recieverThread.setDaemon(true);
        recieverThread.start();
        this.consumer = consumer;

    }

    public void initialize() {

        // startup JVMServer in advance
        if (jvmClient == null) {
            if (jvmClient == null) {
                jvmClient = new MultiCastMessageClient(this);
            }
        }

        try {
            while (!jvmClient.isRunning()) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Log.error(e);
            }
        }
    }

    public void startRecieving() {
        recieving = true;
        Log.debug("started recieving");
    }

    public void stopRecieving() {
        recieving = false;
        Log.debug("stopped recieving");
    }

    public void recieveMessage(String xml) {
        if ( recieving ) consumer.recieveMessage(xml);
    }
}
