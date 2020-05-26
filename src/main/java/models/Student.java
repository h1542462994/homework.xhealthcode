package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "student")
public class Student implements IStudentTeacherUnion {
    @Primary
    private long studentId;
    private long userId;
    private String name;
    private String idCard;
    private String number;
    private Long xClassId;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public long getId() {
        return studentId;
    }

    @Override
    public void setId(long id) {
        this.studentId = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getXClassId() {
        return xClassId;
    }

    public void setXClassId(Long xClassId) {
        this.xClassId = xClassId;
    }
}
