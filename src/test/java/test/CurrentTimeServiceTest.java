package test;

import org.junit.jupiter.api.Test;
import services.CurrentTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试当前时间服务的正确性
 */
public class CurrentTimeServiceTest {
    @Test
    public void test() {
        CurrentTimeService currentTimeService = new CurrentTimeService();
        // 设置当前的时间为2020年5月1日
        currentTimeService.setCurrentTime(LocalDateTime.of(LocalDate.of(2020, 5, 1), LocalTime.MIN));
        assertEquals("2020-05-01", currentTimeService.getCurrentTime().toLocalDate().toString());
        // 添加一天的时间
        currentTimeService.plus(1, ChronoUnit.DAYS);
        assertEquals("2020-05-02", currentTimeService.getCurrentTime().toLocalDate().toString());
    }
}
