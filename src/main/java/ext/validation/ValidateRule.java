package ext.validation;

import ext.exception.ValidateFailedException;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 表示一个动态的验证规则
 */
public class ValidateRule {
    private Iterable<ValidateRuleUnit> units;
    public ValidateRule(Iterable<ValidateRuleUnit> units){
        this.units = units;
    }
    public ValidateRule(ValidateRuleUnit... units){
        this.units = Arrays.asList(units);
    }

    /**
     * 进行验证
     * @return 验证的消息
     */
    public <T> HashMap<String, String> validate(T element) throws IllegalAccessException, ValidateFailedException {

        HashMap<String, String> msg = new HashMap<>();
        for (ValidateRuleUnit unit: this.units){
            String u = unit.validate(element);
            if(u != null){
                msg.put(unit.getField(), u);
            }
        }

        if(msg.size() > 0){
            throw new ValidateFailedException(msg);
        } else {
            return null;
        }
    }

}
