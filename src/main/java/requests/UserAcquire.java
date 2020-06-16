package requests;

import ext.annotation.Required;

import java.util.Set;

public class UserAcquire {
    @Required
    public String phone;
    @Required
    public String isArrivedInfectedArea;
    @Required
    public String isBeenAbroad;
    @Required
    public String isContactedPatient;
    @Required
    public String isDefiniteDiagnosis;
    @Required
    public String[] illness;
}
