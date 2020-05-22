package ext.validation;

import java.util.ArrayList;
import java.util.HashMap;

public class ValidateMsg {
    private Iterable<ValidateUnit> units;

    public ValidateMsg(Iterable<ValidateUnit> unit){
        this.units = unit;
    }


    public Iterable<ValidateUnit> getUnits() {
        return units;
    }

    public static ValidateMsg uknown(){
        ArrayList<ValidateUnit> units = new ArrayList<>();
        ValidateMsg msg = new ValidateMsg(null);
        units.add(new ValidateUnit("null", "未知的异常"));
        msg.units = units;
        return msg;
    }

    @Override
    public String toString() {
        return "ValidateMsg{" +
                "units=" + units +
                '}';
    }
}
