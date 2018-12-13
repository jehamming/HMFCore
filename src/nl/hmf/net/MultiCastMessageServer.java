package nl.hmf.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;
import java.util.Queue;

import nl.hmf.interfaces.IConstants;
import nl.hmf.util.Log;

/*
 * For inter JVM communication
 */
public class MultiCastMessageServer implements Runnable {
	boolean running = false;
	private MulticastSocket socket;
	private DatagramPacket packet;
	private InetAddress address;
	private Queue<String> queue = new LinkedList<String>();

	private int enqueued = 0;
	private int dispatched = 0;

	private Thread dispatcherThread;

	public MultiCastMessageServer() {
		dispatcherThread = new Thread(this);
		dispatcherThread.setDaemon(true);
		dispatcherThread.start();

	}

	public void dispatchMessageToOtherJVMs(String msg) {
		queue.add(msg);
		enqueued++;
		synchronized (this) {
			this.notify();
		}
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
			address = InetAddress.getByName(IConstants.JVM_MULTICAST);
			socket = new MulticastSocket(IConstants.MULTICASTSOCKET);
			socket.setTimeToLive(100);

			socket.joinGroup(address);
			running = true;
			Log.debug("Inter-JVM MultiCast Messaging Server is alive");

			while (running) {
				if (queue.isEmpty()) {
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						Log.error(e);
					}
				}
				// Do work
				String msg;
				while ((msg = queue.poll()) != null) {
					byte[] data = null;
					data = msg.getBytes();
					packet = new DatagramPacket(data, msg.length(), address,
							IConstants.JVM_PORT);

					// Sends the packet
					try {
						socket.send(packet);
					} catch (IOException exc) {
						Log.error(exc);
					}

					queue.remove(msg);
					dispatched++;

				}

			}

			// Stop the server
			socket.leaveGroup(address);
			socket.close();
			running = false;
			Log.debug("Inter-JVM MultiCast Messaging Server deceased");
		} catch (Exception e) {
			Log.error(e);
		}
	}

	public boolean isRunning() {
		return running;
	}

}
