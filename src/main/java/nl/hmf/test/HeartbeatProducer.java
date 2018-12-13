package nl.hmf.test;

import java.util.Calendar;
import javax.swing.text.DateFormatter;
import nl.hmf.core.GenericProducer;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Produce continously
 * 
 * @author Janneman
 *
 */
public class HeartbeatProducer extends GenericProducer {

    public HeartbeatProducer() {
        super("A heartbeat producer");
    }

    public void produce(int s) {
        Calendar c = Calendar.getInstance();
        HeartBeat heartbeat = new HeartBeat();
        while (true) {
            heartbeat.setTime(c.getTime());
            heartbeat.setMessage("Heart");
            produce(heartbeat);
            System.out.println("Produced a heartbeat:" + heartbeat.getTime());
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int s = 2000;
        if (args.length != 1) {
            System.out.println("Note, you can add 1 int (timeout) as arg");
        } else {
            s = Integer.valueOf(args[1]).intValue();

        }
        HeartbeatProducer p = new HeartbeatProducer();
        p.produce(s);

    }
}
