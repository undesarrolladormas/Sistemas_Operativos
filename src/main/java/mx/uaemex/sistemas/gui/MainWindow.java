package mx.uaemex.sistemas.gui;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super();
        this.setSize(1080,720);
        this.setResizable(false);
        this.setTitle("Proyecto");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.init();
    }

    private void init() {
        JTabbedPane pane = new JTabbedPane();
        pane.add("Inicio", new InfoPanel());
        pane.add("Calendarizacion", new SchedulePanel());
        pane.add("Reemplazo de pagina", new ReplacementPanel());
        pane.add("Archivos",new JPanel());

        this.add(pane);
    }
}
