package dao;

import enums.Result;
import enums.TypeType;

import java.util.ArrayList;

public class UserResult {
    /**
     * 用户的id
     */
    private long id;
    /**
     * 表示用户的类别，为
     * {@link TypeType}中的值
     */
    private int type;
    private String name;
    private String number;
    private String idCard;
    /**
     * 表示当前健康码的结果，为
     * {@link Result}中的值。
     *
     */
    private int result;
    /**
     * 如果当前为学生，表示所在xclass的Id，如果当前是老师，表示所在college的Id
     */
    private Long fieldId;
    /**
     * 表示当前用户的路径，如果不存在，则为空。
     */
    private PathDao path;
    private int adminType;
    /**
     * 若{@link UserResult#type}为{@link enums.ResultType#RED}或者{@link enums.ResultType#YELLOW}，表示距离下一个阶段需要打卡的天数。
     */
    private int remainDays;
    /**
     * 表示近期的打卡概况，每一项值为{@link TypeType}中的一项。
     */
    private ArrayList<Integer> summary;

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

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public PathDao getPath() {
        return path;
    }

    public void setPath(PathDao path) {
        this.path = path;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public ArrayList<Integer> getSummary() {
        return summary;
    }

    public void setSummary(ArrayList<Integer> summary) {
        this.summary = summary;
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

    public int getAdminType() {
        return adminType;
    }

    public void setAdminType(int adminType) {
        this.adminType = adminType;
    }
}
