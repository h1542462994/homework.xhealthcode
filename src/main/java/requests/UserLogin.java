package requests;

import ext.annotation.Region;
import ext.annotation.Rename;
import ext.annotation.Required;

public class UserLogin {
    public static final int STUDENT = 0;
    public static final int TEACHER = 1;
    public static final int ADMIN = 2;

    @Required
    public String number;
    public String name;
    @Required
    public String passport;
    @Required
    public Integer type;


}
