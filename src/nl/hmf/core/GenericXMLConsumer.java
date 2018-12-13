/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.core;

import java.util.ArrayList;
import java.util.List;
import nl.hmf.interfaces.IXMLConsumer;
import nl.hmf.net.MessageReciever;
import nl.hmf.util.XMLUtil;

/**
 *
 * @author Jan-Egbert
 */
public abstract class GenericXMLConsumer implements IXMLConsumer {

    private List<String> xpathQueries = new ArrayList<String>();
    private MessageReciever receiver;
    private ConsumerBucketManager bucketManager;

    public GenericXMLConsumer() {
        receiver = new MessageReciever(this);
        receiver.initialize();
        bucketManager = new ConsumerBucketManager(this);
        bucketManager.start();
    }

    public void recieveMessage(String xml) {
        if (checkQueries(xml)) {
            bucketManager.putItemInBucket(xml);
        }
    }

    public abstract void xmlRecieved(String xml);

    public boolean checkQueries(String xml) {
        boolean retval = true;
        if (getXPathQueries().size() == 0) {
            return retval;
        }
        for (String query : getXPathQueries()) {
            String result = XMLUtil.executeXPath(xml, query);
            if (result.equals("false") || result.equals("")) {
                retval = false;
                break;
            }
        }
        return retval;
    }

    public List<String> getXPathQueries() {
        return xpathQueries;
    }

    public void addXPathQuery(String q) {
        xpathQueries.add(q);
    }

    public void startConsuming() {
        receiver.startRecieving();
    }

    public void stopConsuming() {
        receiver.stopRecieving();
    }
}
