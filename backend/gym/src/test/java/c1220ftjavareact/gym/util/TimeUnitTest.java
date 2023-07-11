package c1220ftjavareact.gym.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class TimeUnitTest {

    @Test
    public void localDateIsNow(){
        var date = TimeUtils.getLocalDate();
        Assert.assertEquals(LocalDate.now(), date);
        Assert.assertTrue(LocalDate.of(2023, 7, 1).isBefore(ChronoLocalDate.from(date)));
    }

    @Test
    public void localDateTimeIsNow(){
        var dateTime = TimeUtils.getLocalDateTime();
        var specif = TimeUtils.gerFormatedLocalDateTime();
        Assert.assertNotEquals(dateTime, specif);
        var dateFormat = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Assert.assertNotEquals(dateFormat, specif);
        Assert.assertEquals(dateFormat, TimeUtils.formatToString(specif));
    }

}
