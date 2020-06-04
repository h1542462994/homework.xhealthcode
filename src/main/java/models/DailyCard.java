package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

import java.sql.Date;

@Entity(model = "dailycard")
public class DailyCard {
    @Primary
    private long dailyCardId;
    private long userId;
    private String answer;
    private int result;
    private Date date;

    public long getDailyCardId() {
        return dailyCardId;
    }

    public void setDailyCardId(long dailyCardId) {
        this.dailyCardId = dailyCardId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getResult() {
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
}
