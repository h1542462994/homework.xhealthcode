package requests;

import ext.annotation.Required;

public class AdminLogin {
    @Required
    public String number;
    @Required
    public String passport;
}
