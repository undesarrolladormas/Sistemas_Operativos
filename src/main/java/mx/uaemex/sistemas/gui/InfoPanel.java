package mx.uaemex.sistemas.gui;


import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel() {
        super();
        this.setLayout(new GridLayout(3,3));
        this.add(new JLabel());
        this.add(new JLabel("Proyecto Sistemas Operativos"));
    }
}
