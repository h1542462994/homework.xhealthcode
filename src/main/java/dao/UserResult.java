package dao;

public class UserResult {
    /**
     * 用户的id
     */
    private long id;
    /**
     * 表示用户的类别，为
     * {@link util.TypeType}中的值
     */
    private int type;
    /**
     * 表示当前健康码的结果，为
     * {@link dao.Result}中的值。
     *
     */
    private int result;
    /**
     * 如果当前为学生，表示所在xclass的Id，如果当前是老师，表示所在college的Id
     */
    private long fieldId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }
}
