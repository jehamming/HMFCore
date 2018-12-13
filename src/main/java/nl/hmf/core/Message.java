/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.hmf.core;

/**
 *
 * @author A121916
 */
public class Message {
    
    private Header header;
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
    
    

}
