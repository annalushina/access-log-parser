
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String requestPath;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;
    private final String userAgentString;

    public enum HttpMethod {
        GET, POST, PUT,
    }

    public LogEntry(String line) {
        String[] parts = line.split(" ");
        if (parts.length > 0) {
            this.ipAddress = parts[0];
        } else {
            this.ipAddress = null;
        }
        if (parts.length > 3) {
            this.dateTime = LocalDateTime.parse(parts[3].substring(1), DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH));
        } else dateTime = null;
        if (parts.length > 5) {
            this.method = HttpMethod.valueOf(parts[5].replace("\"", ""));
        } else method = null;
        if (parts.length > 6) {
            this.requestPath = parts[6];
        } else requestPath = null;
        if (parts.length > 8) {
            this.responseCode = Integer.parseInt(parts[8]);
        } else responseCode = 0;
        if (parts.length > 9) {
            this.responseSize = Integer.parseInt(parts[9]);
        } else responseSize = 0;
        if (parts.length > 10) {
            this.referer = parts[10].replace("\"", "");
        } else referer = null;
        if (parts.length > 11) {
            String userAgentStringConcate="";
            for(int i=11;i<parts.length;i++){
                userAgentStringConcate+=parts[i];
            }
            this.userAgentString=userAgentStringConcate;

            this.userAgent = new UserAgent(this.userAgentString);

        } else {
            userAgentString = null;
            userAgent = null;
        }


//        System.out.println("ip адрес: " + ipAddress + " дата и время запроса: " + dateTime + " метод " + method +
//                " путь запроса " + requestPath + " размер ответа: " + responseSize + " рефер " +
//                referer  + " тип ОС: "+ userAgent.getOsType()+" браузер "+ userAgent.getBrowser() );
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "userAgent=" + userAgent +
                '}';
    }

    public String getUserAgentString() {
        return userAgentString;
    }
}