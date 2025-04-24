import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int totalVisit;
    private int errorRequest;
    private Set<String> uniqueIP;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.totalVisit = 0;
        this.errorRequest = 0;
        this.uniqueIP = new HashSet<>();
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
        if (logEntry.getUserAgentString() != null && !logEntry.getUserAgentString().toLowerCase().contains("bot")) {
            totalVisit++;
            uniqueIP.add(logEntry.getIpAddress());
        }

        if (logEntry.getResponseCode() >= 400) {
            errorRequest++;
        }
    }

    public long getTrafficRate() {
        if (minTime == null || maxTime == null || Duration.between(maxTime,minTime).toHours() == 0) {
            return 0;
        }

        long hours = Duration.between(minTime,maxTime).toHours();
        return totalTraffic / hours;
    }
    public double countVisitsPerHour() {
        if (minTime == null || maxTime == null || Duration.between(minTime, maxTime).toHours() == 0) {
            return 0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        return (double) totalVisit / hours;
    }
    public double countErrorRequestsPerHour() {
        if (minTime == null || maxTime == null || Duration.between(minTime, maxTime).toHours() == 0) {
            return 0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        return (double) errorRequest / hours;
    }
    public double countVisitsPerUser() {
        if (uniqueIP.isEmpty()) {
            return 0;
        }

        return (double) totalVisit / uniqueIP.size();
    }

}