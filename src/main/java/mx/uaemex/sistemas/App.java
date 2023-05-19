package mx.uaemex.sistemas;

import com.formdev.flatlaf.FlatLightLaf;
import mx.uaemex.sistemas.gui.MainWindow;

import javax.swing.*;

public class App 
{
    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        MainWindow frame = new MainWindow();
        frame.setVisible(true);
    }
}
