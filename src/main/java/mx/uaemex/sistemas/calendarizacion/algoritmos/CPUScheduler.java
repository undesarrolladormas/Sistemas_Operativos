package mx.uaemex.sistemas.calendarizacion.algoritmos;

import java.util.ArrayList;

import mx.uaemex.sistemas.calendarizacion.Event;
import mx.uaemex.sistemas.calendarizacion.Row;

public abstract class CPUScheduler {
    private final ArrayList<Row> rows;
    private final ArrayList<Event> timeline;
    private int timeQuantum;

    public CPUScheduler() {
        rows = new ArrayList<>();
        timeline = new ArrayList<>();
        timeQuantum = 1;
    }

    public boolean add(Row row) {
        return rows.add(row);
    }

    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public double getAverageWaitingTime() {
        double avg = 0.0;

        for (Row row : rows) {
            avg += row.getWaitingTime();
        }

        return avg / rows.size();
    }

    public double getAverageTurnAroundTime() {
        double avg = 0.0;

        for (Row row : rows) {
            avg += row.getTurnaroundTime();
        }

        return avg / rows.size();
    }

    public Event getEvent(Row row) {
        for (Event event : timeline) {
            if (row.getProcessName().equals(event.getProcessName())) {
                return event;
            }
        }

        return null;
    }

    public Row getRow(String process) {
        for (Row row : rows) {
            if (row.getProcessName().equals(process)) {
                return row;
            }
        }

        return null;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public ArrayList<Event> getTimeline() {
        return timeline;
    }

    public abstract void process();
}
