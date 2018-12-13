/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.hmf.test;

import java.util.Date;

/**
 *
 * @author A121916
 */
public class HeartBeat {
    
    private Date time;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    

}
