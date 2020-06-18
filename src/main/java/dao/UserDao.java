package dao;

import enums.Result;
import enums.TypeType;

import java.sql.Date;
import java.util.ArrayList;

public class UserDao {
    /**
     * 用户的id
     * #SimplePart
     */
    private long id;
    /**
     * 表示用户的类别，为
     * {@link TypeType}中的值
     * # SimplePart
     */
    private int type;
    /**
     * 用户的姓名
     * # SimplePart
     */
    private String name;
    /**
     * 用户的学号/工号
     * # SimplePart
     */
    private String number;
    /**
     * 用户的身份证
     * # SimplePart
     */
    private String idCard;
    /**
     * 如果当前为学生，表示所在xclass的Id，如果当前是老师，表示所在college的Id
     * # SimplePart
     */
    private Long fieldId;
    /**
     * 表示当前用户的路径，如果不存在，则为空。
     * # SimplePart | Transferred
     */
    private PathDao path;
    /**
     * 管理员类别
     * # SimplePart
     */
    private int adminType;
    /**
     * 表示当前健康码的结果，为
     * {@link Result}中的值。
     *
     */
    private int result;
    /**
     * 最后打卡的日期
     */
    private Date date;
    /**
     * 若{@link UserDao#type}为{@link enums.ResultType#RED}或者{@link enums.ResultType#YELLOW}，表示距离下一个阶段需要打卡的天数。
     */
    private int remainDays;

    public boolean isStudentType(){
        return this.type == TypeType.STUDENT;
    }

    public boolean isTeacherType(){
        return this.type == TypeType.TEACHER || this.type == TypeType.ADMIN;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
