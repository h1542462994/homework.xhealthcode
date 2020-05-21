package ext.declare;

public class DbSettings {
    private String driver;
    private String url;
    private String user;
    private String passport;

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
