/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hmf.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import org.xml.sax.InputSource;

/**
 *
 * @author A121916
 */
public class XMLUtil {

    private static HashMap<String, XPathExpression> compiledExpressions = new HashMap<String, XPathExpression>();

    public static synchronized String executeXPath(String xml, String expression) {
        String retVal = "";
        try {
            XPathExpression xpathExpression = compiledExpressions.get(expression);
            if (xpathExpression == null) {
                XPath xpath = XPathFactory.newInstance().newXPath();
                xpathExpression = xpath.compile(expression);
                compiledExpressions.put(expression, xpathExpression);
            }
            InputSource source = new InputSource(new StringReader(xml));
            retVal = xpathExpression.evaluate(source);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
}
