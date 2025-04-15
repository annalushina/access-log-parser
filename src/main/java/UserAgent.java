
public class UserAgent {
    private final String osType;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.osType = extractOsType(userAgentString);
        this.browser = extractBrowser(userAgentString);
    }

    private String extractOsType(String userAgentString) {
        if (userAgentString.contains("Windows")) {
            return "Windows";
        } else if (userAgentString.contains("Mac OS X") || userAgentString.contains("macOS")) {
            return "macOS";
        } else if (userAgentString.contains("Linux")) {
            return "Linux";
        } else {
            return "Иная";
        }
    }

    private String extractBrowser(String userAgentString) {
        if (userAgentString.contains("Mozilla")) {
            return "Mozilla";
        }
        if (userAgentString.contains("Edg")) {
            return "Edge";
        } else if (userAgentString.contains("Firefox")) {
            return "Firefox";
        } else if (userAgentString.contains("Chrome")) {
            return "Chrome";
        } else if (userAgentString.contains("OPR") || userAgentString.contains("Opera")) {
            return "Opera";
        } else {
            return "Иной";
        }
    }

    public String getOsType() {;
        return osType;
    }

    public String getBrowser() {
        return browser;
    }
}