package mx.uaemex.sistemas.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import mx.uaemex.sistemas.replacement.Clairvoyant;
import mx.uaemex.sistemas.replacement.Clock;
import mx.uaemex.sistemas.replacement.Fifo;
import mx.uaemex.sistemas.replacement.Second_Chance;

public class ReplacementPanel extends JPanel {

    private final JTextField referenceTextField;
    private final JSpinner framesSpinner;
    private final JTable resultsTable;

    public ReplacementPanel() {
        super();
        this.setSize(800,650);
        JPanel frame = new JPanel(new BorderLayout());

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);

        JLabel referenceLabel = new JLabel("Reference string (comma separated):");
        controlPanel.add(referenceLabel);

        this.referenceTextField = new JTextField(20);
        controlPanel.add(referenceTextField);

        JLabel framesLabel = new JLabel("Frames Number:");
        controlPanel.add(framesLabel);

        this.framesSpinner = new JSpinner();
        framesSpinner.setValue(4);
        controlPanel.add(framesSpinner);

        JComboBox<String> seleccionAlgoritmo = new JComboBox<>(new String[] {"Clairvoyant", "FIFO", "Clock", "Second Chance"});
        controlPanel.add(seleccionAlgoritmo);

        JButton calcular = new JButton("Compute");
        controlPanel.add(calcular);
        controlPanel.add(calcular);

        resultsTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        resultsTable.setRowHeight(30);

        frame.add(new JScrollPane(resultsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.SOUTH);

        calcular.addActionListener(e -> {
            String selectedOption = (String) seleccionAlgoritmo.getSelectedItem();
            switch (Objects.requireNonNull(selectedOption)) {
                case "Clairvoyant" -> this.simulatePagination(Algorithm.Clairvoyant);
                case "FIFO" -> this.simulatePagination(Algorithm.FIFO);
                case "Clock" -> this.simulatePagination(Algorithm.Clock);
                case "Second Chance" -> this.simulatePagination(Algorithm.Second_Chance);
            }
        });

        this.add(frame);
    }

    private void simulatePagination(Algorithm algorithm){
        String referenceString = referenceTextField.getText();
        String[] reference = referenceString.trim().split(",");
        int frames = (int) framesSpinner.getValue();
        switch (algorithm) {
            case FIFO -> new Fifo(reference,frames,resultsTable);
            case Clairvoyant -> new Clairvoyant(reference,frames,resultsTable);
            case Clock -> new Clock(reference,frames,resultsTable);
            case Second_Chance -> new Second_Chance(reference,frames,resultsTable);
        }

    }

    enum Algorithm {
        FIFO,
        Clock,
        Clairvoyant,
        Second_Chance
    }
}


