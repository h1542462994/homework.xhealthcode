package requests;

import ext.validation.Reg;
import ext.validation.Region;
import ext.validation.Required;

public class UserLogin {
    @Required
    @Region(min = 2, max = 20)
    public String name;
    @Required
    public String passport;
    public boolean isTeacher;
}
