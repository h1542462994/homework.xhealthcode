package requests;

import ext.annotation.Reg;
import ext.annotation.Region;
import ext.annotation.Required;

public class UserRequest {
    @Required
    @Region(min = 0, max = 1)
    private Integer type;

    @Required
    @Region(min = 1, max = 20)
    private String name;

    @Required
    @Region(min = 6, max = 20)
    private String number;

    @Required
    @Region(min = 18, max = 18)
    private String idCard;

    @Required
    private String field;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
