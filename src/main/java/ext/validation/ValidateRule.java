package ext.validation;

import ext.exception.ValidateFailedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 表示一个动态的验证规则
 */
public class ValidateRule {
    private final List<ValidateRuleUnit> units;
    public ValidateRule(List<ValidateRuleUnit> units){
        this.units = units;
    }
    public ValidateRule(ValidateRuleUnit... units){
        this.units = Arrays.asList(units);
    }

    /**
     * 进行验证
     */
    public <T> void validate(T element) throws IllegalAccessException, ValidateFailedException {

        HashMap<String, String> msg = new HashMap<>();
        for (ValidateRuleUnit unit: this.units){
            String u = unit.validate(element);
            if(u != null){
                msg.put(unit.getField(), u);
            }
        }

        if(msg.size() > 0){
            throw new ValidateFailedException(msg);
        }
    }

    /**
     * 在已有的规则上附加规则
     */
    public ValidateRule concat(ValidateRuleUnit ... units) {
        ArrayList<ValidateRuleUnit> units1 = new ArrayList<>();
        units1.addAll(this.units);
        units1.addAll(Arrays.asList(units));
        return new ValidateRule(units1);
    }

}
