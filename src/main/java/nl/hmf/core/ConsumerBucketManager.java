/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.core;

import java.util.LinkedList;
import java.util.Queue;
import nl.hmf.util.Log;

/**
 *
 * @author Jan-Egbert
 */
public class ConsumerBucketManager implements Runnable {

    private boolean running = false;
    private Queue<String> bucket = new LinkedList<String>();
    private Thread processingThread;
    private GenericXMLConsumer consumer;

    public ConsumerBucketManager(GenericXMLConsumer consumer) {
        this.consumer = consumer;
    }

    public void start() {
        running = true;
        processingThread = new Thread(this);
        processingThread.setDaemon(true);
        processingThread.start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            if (bucket.isEmpty()) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    Log.error(e);
                }
            }
            // Do work
            String xml;
            while ((xml = bucket.poll()) != null) {
                consumer.xmlRecieved(xml);
            }
        }
    }

    public void putItemInBucket(String xml) {
        if ( bucket.size() > 50 ) {
            Log.info("Bucket overload (" + bucket.size()+ ") , Dumping message:");
            Log.info(xml);
            return;
        }
        bucket.add(xml);        
        synchronized (this) {
            this.notify();
        }
    }
}
