package nl.hmf.test;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConsumerFrame extends JFrame {

    JTextArea ta;
    JScrollPane scrollPane;
    TestBeanConsumer consumer;

    public ConsumerFrame() {
        Container c = getContentPane();
        this.setTitle("Consumer");
        ta = new JTextArea(10,30);
        scrollPane = new JScrollPane(ta);
        c.add(scrollPane);

        addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }
        });

        pack();
    }

    public void startConsuming() {
        consumer = new TestBeanConsumer(this);
    }

    public void notify(String inbound) {
        String txt = ta.getText();
        txt = txt.concat("\n" + inbound);
        ta.setText(txt);
    }

    public static void main(String[] args) {
        ConsumerFrame f = new ConsumerFrame();
        f.setVisible(true);
        f.startConsuming();
    }
}
