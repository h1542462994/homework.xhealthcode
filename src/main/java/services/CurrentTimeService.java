package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.sql.Date;

/**
 * 提供系统级的修改当前时间的服务。默认为0的偏移量
 */
public class CurrentTimeService {
    // 与实际时间的偏差值
    private long offsetMilliSeconds;

    public LocalDateTime realCurrent() {
        return LocalDateTime.now();
    }

    public long instantMilli(LocalDateTime dateTime) {
        return dateTime.getLong(ChronoField.EPOCH_DAY) * (24 * 3600 * 1000L)
                + dateTime.get(ChronoField.SECOND_OF_DAY) * (1000L)
                + dateTime.get(ChronoField.MILLI_OF_SECOND);
    }

    public void setOffsetMilliSeconds(long offsetMilliSeconds) {
        this.offsetMilliSeconds = offsetMilliSeconds;
    }

    public long getOffsetMilliSeconds() {
        return offsetMilliSeconds;
    }

    public void clear() {
        offsetMilliSeconds = 0;
    }

    public void plus(long amountToAdd, TemporalUnit temporalUnit) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(1970,1,1), LocalTime.MIN);
        localDateTime = localDateTime.plus(amountToAdd, temporalUnit);
        offsetMilliSeconds += instantMilli(localDateTime);
    }

    public void setCurrentTime(LocalDateTime dataTime) {
        offsetMilliSeconds = instantMilli(dataTime) - instantMilli(realCurrent());
    }

    public LocalDateTime getCurrentTime() {
        LocalDateTime localDateTime = realCurrent();
        localDateTime = localDateTime.plus(offsetMilliSeconds, ChronoUnit.MILLIS);
        return localDateTime;
    }

    public Date getCurrentDateOld() {
        return new Date(instantMilli(getCurrentTime()));
    }

}
