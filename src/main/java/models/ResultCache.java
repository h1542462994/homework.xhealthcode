package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(model = "resultcache")
public class ResultCache {
    @Primary
    private long resultCacheId;
    private long userId;
    private int result;
    private String codeKey;
    private Date date;
    private Timestamp expiredAt;

    public long getResultCacheId() {
        return resultCacheId;
    }

    public void setResultCacheId(long resultCacheId) {
        this.resultCacheId = resultCacheId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }
}
