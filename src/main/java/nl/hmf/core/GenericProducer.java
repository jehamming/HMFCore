/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.core;

import com.thoughtworks.xstream.XStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import nl.hmf.net.MessageDispatcher;
import nl.hmf.util.Log;
import nl.hmf.util.UUID;

/**
 *
 * @author A121916
 */
public class GenericProducer {

    private String id = "No ProducerId Set";
    private XStream xstream = new XStream();
    private MessageDispatcher dispatcher;

    public GenericProducer(String id) {
        this.id = id;
        initialize();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String produce(Object o) {
        Header header = new Header();
        header.setSender(getId());
        String cid = generateCorrelationId();
        header.setCorrelationId(id);
        header.setVersion("1.0");
        header.setProducerId(getId());
        header.setTimestamp("" + Calendar.getInstance().getTimeInMillis());
        // Everything to XML, Use XStream
        Message m = new Message();
        m.setHeader(header);
        m.setContent(o);
        String fullXML = xstream.toXML(m);
        // Send XML to bus
        dispatcher.dispatch(fullXML);
        //Log.info("Produced: \n " + fullXML + "\n");
        return cid;
    }

    public String produceXML(String xml) {
        Header header = new Header();
        header.setSender(getId());
        String cid = generateCorrelationId();
        header.setCorrelationId(id);
        header.setVersion("1.0");
        header.setProducerId(getId());
        header.setTimestamp("" + Calendar.getInstance().getTimeInMillis());
        // Everything to XML, Use XStream
        Message m = new Message();
        m.setHeader(header);
        String messageXML = xstream.toXML(m);
        String fullXML =messageXML.replaceAll("</nl.hmf.core.Message>", xml + "\n</nl.hmf.core.Message>");
        // Send XML to bus
        dispatcher.dispatch(fullXML);
        Log.info("Produced:\n" + fullXML + "\n");
        return cid;
    }

    @Override
    public String toString() {
        return id;
    }

    private String generateCorrelationId() {
        UUID uuid = new UUID();
        return uuid.toString();

    }

    private void initialize() {
        String hostname = "undefined";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Log.error("Could not get hostname!");
        }
        this.id = id + "@" + hostname;

        dispatcher = new MessageDispatcher();
        dispatcher.initialize();

    }
}
