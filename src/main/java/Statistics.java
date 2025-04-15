import java.time.LocalDateTime;
import java.time.Duration;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {
        LocalDateTime dateTime = logEntry.getDateTime();
        int responseSize = logEntry.getResponseSize();

        if (dateTime != null) {
            if (minTime == null || dateTime.isBefore(minTime)) {
                minTime = dateTime;
            }
            if (maxTime == null || dateTime.isAfter(maxTime)) {
                maxTime = dateTime;
            }
        }

        totalTraffic += responseSize;

    }

    public long getTrafficRate() {
        if (minTime == null || maxTime == null || Duration.between(maxTime,minTime).toHours() == 0) {
            return 0;
        }

        long hours = Duration.between(minTime,maxTime).toHours();
        return totalTraffic / hours;
    }
}