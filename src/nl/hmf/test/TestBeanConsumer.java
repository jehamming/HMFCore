package nl.hmf.test;

import java.util.Date;

import nl.hmf.core.GenericConsumer;
import nl.hmf.util.Log;
import nl.hmf.core.Message;

public class TestBeanConsumer extends GenericConsumer {
	private ConsumerFrame frame;
	private int total = 0;
	
	public TestBeanConsumer( ConsumerFrame frame ) {
		this.frame = frame;
	}

    @Override
    public void messageRecieved(Message m) {
		if ( m.getContent() instanceof TestBean ) {
			TestBean bean = (TestBean ) m.getContent();
			String txt = bean.getMessage();
			total++;
			Log.info(new Date().toString() + " : received a TestBean, total " + total);
			frame.notify(m.getHeader().getTimestamp() + ":" + txt);
		}
    }

}
