package ext.declare;

public class DbSettings {
    private final String driver;
    private final String url;
    private final String user;
    private final String passport;

    public DbSettings(String driver, String url, String user, String passport){
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.passport = passport;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassport() {
        return passport;
    }
}
