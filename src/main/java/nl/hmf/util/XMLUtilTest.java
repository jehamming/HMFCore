/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.hmf.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import static org.junit.Assert.*;

/**
 *
 * @author A121916
 */
public class XMLUtilTest {

    public XMLUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of executeXPath method, of class XMLUtil.
     */
    @Test
    public void executeXPath() {
        System.out.println("executeXPath");
        String xml = "<aap><noot>12</noot></aap>";
        String expression = "/aap/noot";
        String expResult = "12";
        String result = XMLUtil.executeXPath(xml, expression);
        System.out.println(result);
        assertEquals(expResult, result);
    }

}