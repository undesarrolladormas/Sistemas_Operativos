package mx.uaemex.sistemas.replacement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public abstract class AbstractReplacementAlgorithm {
    protected JTable table;
    protected DefaultTableModel model;
    protected int ref_len;
    protected int pointer, hit, fault;
    protected String[] buffer;
    protected String[][] mem_layout;
    protected Vector<String> matches;
    protected int frames;
    protected String[] reference;

    public AbstractReplacementAlgorithm(String[] reference, int frames, JTable table)
    {
        this.model = new DefaultTableModel();
        this.ref_len = reference.length;
        this.pointer = 0;
        this.hit = 0;
        this.fault = 0;
        this.frames = frames;
        this.reference = reference;
        this.table = table;

        // Basically, this is the table :)
        mem_layout = new String[ref_len][frames];
        // This is the column and the actual values in the frames
        buffer = new String[frames];
        matches = new Vector<>();
        matches.add("Faults");
        for (int i = 0; i < frames; i++) buffer[i] = "-";

        model.addColumn("Request");
        for (var s: reference) model.addColumn(s);

        solve();

        for (int i = 0; i < frames; i++) {
            Vector<String> aux = new Vector<>();
            aux.add("Frame " + i);
            for (int j = 0; j < ref_len; j++) {
                aux.add(mem_layout[j][i]);
            }
            model.addRow(aux);
        }
        model.addRow(matches);

        table.setModel(model);
    }

    protected abstract void solve();

}
