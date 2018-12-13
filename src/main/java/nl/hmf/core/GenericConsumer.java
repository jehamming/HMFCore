/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.core;

import com.thoughtworks.xstream.XStream;
import nl.hmf.util.Log;

/**
 *
 * @author Jan-Egbert
 */
public abstract class GenericConsumer extends GenericXMLConsumer {

    private XStream xstream = new XStream();

    public void xmlRecieved(String xml) {
        Object o = xstream.fromXML(xml);
        if (o == null) {
            Log.error("Received a message that could not be deserialized! XML is :\n" + xml + "\n");
            return;
        }
        if (o instanceof Message) {
            Message m = (Message) o;
            messageRecieved(m);
        } else {
            Log.error("Received a message that was not a Message! XML is :\n" + xml + "\n");
        }
    }

    public abstract void messageRecieved(Message m);
}
