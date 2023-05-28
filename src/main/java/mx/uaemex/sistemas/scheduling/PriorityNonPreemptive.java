package mx.uaemex.sistemas.scheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityNonPreemptive extends CPUScheduler {
    @Override
    public void process() {
        this.getRows().sort(Comparator.comparingInt((Object o) -> ((Row) o).getArrivalTime()));

        List<Row> rows = Utility.deepCopy(this.getRows());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty()) {
            List<Row> availableRows = new ArrayList<>();

            for (Row row : rows) {
                if (row.getArrivalTime() <= time) {
                    availableRows.add(row);
                }
            }

            availableRows.sort(Comparator.comparingInt((Object o) -> ((Row) o).getPriorityLevel()));

            Row row = availableRows.get(0);
            this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime()));
            time += row.getBurstTime();

            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getProcessName().equals(row.getProcessName())) {
                    rows.remove(i);
                    break;
                }
            }
        }

        for (Row row : this.getRows()) {
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
