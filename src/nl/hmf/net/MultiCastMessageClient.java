package nl.hmf.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import nl.hmf.interfaces.IConstants;
import nl.hmf.util.Log;

/*
 * For inter JVM communication
 */
public class MultiCastMessageClient implements Runnable {

	boolean running = false;
	private MessageReciever parent;

	private Thread dispatcherThread;

	public MultiCastMessageClient(MessageReciever parent) {
		this.parent = parent;
		dispatcherThread = new Thread(this);
		dispatcherThread.setDaemon(true);
		dispatcherThread.start();
	}

	public void stopServer() {
		if (running) {
			running = false;
		}
	}

	public void run() {
		try {

			MulticastSocket socket;
			DatagramPacket packet;
			InetAddress address;

			address = InetAddress.getByName(IConstants.JVM_MULTICAST);
			socket = new MulticastSocket(IConstants.JVM_PORT);
			socket.setLoopbackMode(false);

			// join a Multicast group and send the group salutations

			socket.joinGroup(address);
			byte[] data = new byte[999];

			packet = new DatagramPacket(data, data.length);
			Log.debug("Inter-JVM MultiCast Messaging Client is alive");

			running = true;
			while (running) {
				// receive the packets
				socket.receive(packet);
				//Log.debug("MultiCast Client: Received data from : "
				//		+ packet.getAddress().getHostAddress());

				String xml = new String(packet.getData());
				xml = xml.substring(0, packet.getLength());

				parent.recieveMessage(xml);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isRunning() {
		return running;
	}

}
