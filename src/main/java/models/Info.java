package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

import java.sql.Date;

@Entity(model = "info")
public class Info {
    @Primary
    private long infoId;
    private long userId;
    private String phone;
    private int result;
    private Date date;
    private Integer continuousClockDays;

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getContinuousClockDays() {
        return continuousClockDays;
    }

    public void setContinuousClockDays(Integer continuousClockDays) {
        this.continuousClockDays = continuousClockDays;
    }
}
