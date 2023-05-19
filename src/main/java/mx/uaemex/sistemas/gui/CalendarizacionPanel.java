package mx.uaemex.sistemas.gui;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import mx.uaemex.sistemas.calendarizacion.Event;
import mx.uaemex.sistemas.calendarizacion.Row;
import mx.uaemex.sistemas.calendarizacion.algoritmos.CPUScheduler;
import mx.uaemex.sistemas.calendarizacion.algoritmos.FirstComeFirstServe;
import mx.uaemex.sistemas.calendarizacion.algoritmos.PriorityNonPreemptive;
import mx.uaemex.sistemas.calendarizacion.algoritmos.PriorityPreemptive;
import mx.uaemex.sistemas.calendarizacion.algoritmos.RoundRobin;
import mx.uaemex.sistemas.calendarizacion.algoritmos.ShortestJobFirst;

public class CalendarizacionPanel extends JPanel {
    private final CustomPanel chartPanel;
    private final JTable table;
    private final JLabel wtResultLabel;
    private final JLabel tatResultLabel;
    private final JComboBox<String> option;
    private final DefaultTableModel model;

    public CalendarizacionPanel() {
        super();
        model = new DefaultTableModel(
                new String[] { "Process", "Arrival Time", "Burst Time", "Priority", "Waiting T.", "TA. Time" }, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(25, 25, 450, 250);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(300, 280, 85, 25);
        addBtn.addActionListener(e -> model.addRow(new String[] { "", "", "", "", "", "" }));
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBounds(390, 280, 85, 25);
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row > -1) {
                model.removeRow(row);
            }
        });

        chartPanel = new CustomPanel();
        JScrollPane chartPane = new JScrollPane(chartPanel);
        chartPane.setBounds(25, 310, 450, 100);

        JLabel wtLabel = new JLabel("Average Waiting Time:");
        wtLabel.setBounds(25, 425, 190, 25);
        JLabel tatLabel = new JLabel("Average Turn Around Time:");
        tatLabel.setBounds(25, 450, 190, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setBounds(215, 425, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBounds(215, 450, 180, 25);

        JLabel optionLabel = new JLabel("Select Algorithm:");
        optionLabel.setBounds(25, 520, 180, 25);
        option = new JComboBox<>(new String[] { "First-Come First-Served", "Shortest-Job First", "Priority Scheduling",
                "Priority Scheduling Preemptive", "Round-Robin" });
        option.setBounds(25, 540, 185, 25);

        JButton computeBtn = new JButton("Compute");
        computeBtn.setBounds(370, 540, 90, 35);
        computeBtn.addActionListener(e -> {
            String selected = (String) option.getSelectedItem();
            CPUScheduler scheduler;
            switch (Objects.requireNonNull(selected)) {
                case "First-Come First-Served" -> scheduler = new FirstComeFirstServe();
                case "Shortest-Job First" -> scheduler = new ShortestJobFirst();
                case "Priority Scheduling" -> scheduler = new PriorityNonPreemptive();
                case "Priority Scheduling Preemptive" -> scheduler = new PriorityPreemptive();
                case "Round-Robin" -> {
                    String tq = JOptionPane.showInputDialog("Time Quantum");
                    if (tq == null) {
                        return;
                    }
                    scheduler = new RoundRobin();
                    scheduler.setTimeQuantum(Integer.parseInt(tq));
                }
                default -> {
                    return;
                }
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                String process = (String) model.getValueAt(i, 0);
                int at = Integer.parseInt((String) model.getValueAt(i, 1));
                int bt = Integer.parseInt((String) model.getValueAt(i, 2));
                int pl;

                if (selected.equals("Priority Scheduling") || selected.equals("Priority Scheduling Preemptive")) {
                    if (!model.getValueAt(i, 3).equals("")) {
                        pl = Integer.parseInt((String) model.getValueAt(i, 3));
                    } else {
                        pl = 1;
                    }
                } else {
                    pl = 1;
                }

                scheduler.add(new Row(process, at, bt, pl));
            }

            scheduler.process();

            for (int i = 0; i < model.getRowCount(); i++) {
                String process = (String) model.getValueAt(i, 0);
                Row row = scheduler.getRow(process);
                model.setValueAt(row.getWaitingTime(), i, 4);
                model.setValueAt(row.getTurnaroundTime(), i, 5);
            }

            wtResultLabel.setText(Double.toString(scheduler.getAverageWaitingTime()));
            tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime()));

            chartPanel.setTimeline(scheduler.getTimeline());
        });

        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(500, 600));
        mainPanel.add(tablePane);
        mainPanel.add(addBtn);
        mainPanel.add(removeBtn);
        mainPanel.add(chartPane);
        mainPanel.add(wtLabel);
        mainPanel.add(tatLabel);
        mainPanel.add(wtResultLabel);
        mainPanel.add(tatResultLabel);
        mainPanel.add(optionLabel);
        mainPanel.add(option);
        mainPanel.add(computeBtn);
        this.add(mainPanel);
    }

    class CustomPanel extends JPanel {
        private List<Event> timeline;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (timeline != null) {
                for (int i = 0; i < timeline.size(); i++) {
                    Event event = timeline.get(i);
                    int x = 30 * (i + 1);
                    int y = 20;
                    g.drawRect(x, y, 30, 30);
                    g.drawString(event.getProcessName(), x + 10, y + 20);
                    g.drawString(Integer.toString(event.getStartTime()), x - 5, y + 45);
                    if (i == timeline.size() - 1) {
                        g.drawString(Integer.toString(event.getFinishTime()), x + 27, y + 45);
                    }
                }
            }
        }

        public void setTimeline(List<Event> timeline) {
            this.timeline = timeline;
            repaint();
        }
    }
}
