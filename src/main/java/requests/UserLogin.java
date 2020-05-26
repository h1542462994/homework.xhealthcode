package requests;

import ext.annotation.Region;
import ext.annotation.Rename;
import ext.annotation.Required;

public class UserLogin {
    @Required
    public String number;
    public String name;
    @Required
    public String passport;
    @Required
    public Integer type;


}
