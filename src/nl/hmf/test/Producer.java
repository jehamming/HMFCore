package nl.hmf.test;

import java.util.Calendar;
import nl.hmf.core.GenericProducer;

/**
 * Produce continously
 * 
 * @author Janneman
 *
 */
public class Producer extends GenericProducer {


    public Producer() {
       super("A simple producer");
    }

    public void produce(String txt, int s) {
        TestBean bean = new TestBean();
        while (true) {
            System.out.println("Producing: " + txt);
            Calendar c = Calendar.getInstance();
            bean.setMessage( txt);
            produce(bean);
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String txt = "text";
        int s = 200;
        if (args.length != 2) {
            System.out.println("Note, you can add 1 String and 1 int as arg");
        } else {
            txt = args[0];
            s = Integer.valueOf(args[1]).intValue();

        }
        Producer p = new Producer();
        p.produce(txt, s);

    }
}
