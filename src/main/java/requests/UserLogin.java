package requests;

import ext.annotation.Region;
import ext.annotation.Required;

public class UserLogin {
    @Required
    @Region(min = 2, max = 20)
    public String name;
    @Required
    public String passport;
}
