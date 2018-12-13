package nl.hmf.net;


/*
 * A Message Dispatcher
 */
public class MessageDispatcher  {

	private static MultiCastMessageServer jvmServer = null;
        
	public MessageDispatcher() {

	}

	public void initialize() {

		// startup JVMServer in advance
		if (jvmServer == null ) {
			if (jvmServer == null) {
				jvmServer = new MultiCastMessageServer();
			}
		}

		try {
			while (!jvmServer.isRunning()) {
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	/**
	 * @param msg
	 * @return CorrelationId
	 */
	public void dispatch(String msg) {
		jvmServer.dispatchMessageToOtherJVMs(msg);
	}


}
