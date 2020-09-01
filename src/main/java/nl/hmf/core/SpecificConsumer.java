/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.core;

import com.thoughtworks.xstream.XStream;
import nl.hmf.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Jan-Egbert
 */
public abstract class SpecificConsumer<T> extends GenericXMLConsumer {

    private XStream xstream = new XStream();
    /**
     * The real type of the class ('T') that this consumer consumes
     */
    private Class<T> clazz = figureOutPersistentClass();

    public void xmlRecieved(String xml) {
        Object o = xstream.fromXML(xml);
        if (o == null) {
            Log.error("Received a message that could not be deserialized! XML is :\n" + xml + "\n");
            return;
        }
        if (o instanceof Message) {
            Message m = (Message) o;
            if (m.getContent().getClass().getCanonicalName().equals(clazz.getCanonicalName())) {
                consume((T) m.getContent());
            }
        } else {
            Log.error("Received a message that was not a Message! XML is :\n" + xml + "\n");
        }
    }

    public abstract void consume(T object);

    /**
     * Find out the type of 'T' using reflection
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<T> figureOutPersistentClass() {
        Class<T> clazz =
                (Class<T>) ((ParameterizedType) (getClass().getGenericSuperclass()))
                        .getActualTypeArguments()[0];
        return clazz;
    }
}
