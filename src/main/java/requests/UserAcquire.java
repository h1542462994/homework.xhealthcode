package requests;

import ext.annotation.Required;
import ext.validation.ValidateRule;
import ext.validation.ValidateRuleUnit;
import ext.validation.Validator;
import ext.validation.unit.IValidate;
import ext.validation.unit.ValidateOption;
import ext.validation.unit.ValidateReg;
import ext.validation.unit.ValidateRequired;

import java.util.Arrays;
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

    public static ValidateRule getClockValidateRule() {
        return new ValidateRule(
                new ValidateRuleUnit("isArrivedInfectedArea",
                        new ValidateRequired(),
                        new ValidateOption<>("y", "n")
                ),
                new ValidateRuleUnit("isBeenAbroad",
                        new ValidateRequired(),
                        new ValidateOption<>("y", "n")
                ),
                new ValidateRuleUnit("isContactedPatient",
                        new ValidateRequired(),
                        new ValidateOption<>("y", "n")),
                new ValidateRuleUnit("isDefiniteDiagnosis",
                        new ValidateRequired(),
                        new ValidateOption<>("y", "n")),
                new ValidateRuleUnit("illness",
                        (field, value) -> {
                            String[] illness1 = (String[]) value;
                            if (illness1 == null) return null;
                            if (Arrays.stream(illness1).allMatch((v) -> v.matches("[1-7]"))) {
                               return null;
                            } else {
                               return "字段错误";
                            }
                        })
        );

    }
}
