package ext.validation;

public class ValidateUnit {
    public ValidateUnit(String key, String info){
        this.key = key;
        this.info = info;
    }

    private String key;
    private String info;

    public String getKey() {
        return key;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "ValidateUnit{" +
                "key='" + key + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
