import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int totalVisit;
    private int errorRequest;
    private Set<String> uniqueIP;
    private Set<String> existingPages;
    private Map<String, Integer> osCount;
    private Set<String> nonExistingPages;
    private Map<String, Integer> browserCount;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.totalVisit = 0;
        this.errorRequest = 0;
        this.uniqueIP = new HashSet<>();
        this.existingPages = new HashSet<>();
        this.osCount = new HashMap<>();
        this.nonExistingPages = new HashSet<>();
        this.browserCount = new HashMap<>();
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
        if (logEntry.getResponseCode() == 200) {
            existingPages.add(logEntry.getRequestPath());
        }
        if (logEntry.getResponseCode() == 404) {
            nonExistingPages.add(logEntry.getRequestPath());
        }
        String osType = logEntry.getUserAgent().getOsType();
        osCount.put(osType, osCount.getOrDefault(osType, 0) + 1);
        String browserType = logEntry.getUserAgent().getBrowser();
        browserCount.put(browserType, browserCount.getOrDefault(browserType, 0) + 1);
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
    public Set<String> countExistingPages() {
        return existingPages;
    }
    public Set<String> getNonExistingPages() {
        return nonExistingPages;
    }
    public Map<String, Double> getOsStatistics() {
        Map<String, Double> osPercentage = new HashMap<>();
        int totalOsCount = osCount.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : osCount.entrySet()) {
            osPercentage.put(entry.getKey(), (double) entry.getValue() / totalOsCount);
        }

        return osPercentage;
    }
    public Map<String, Double> getBrowserStatistics() {
        Map<String, Double> browserPercentage = new HashMap<>();
        int totalBrowserCount = browserCount.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : browserCount.entrySet()) {
            browserPercentage.put(entry.getKey(), (double) entry.getValue() / totalBrowserCount);
        }

        return browserPercentage;
    }

}