package c1220ftjavareact.gym.util;

import java.time.*;

public class TimeUtils {

    public static Long getDateMillis(){
        return System.currentTimeMillis();
    }
    public static ZoneId getZoneId(){
        return ZoneId.systemDefault();
    }
    public static Clock getCLock(){
        return Clock.system(TimeUtils.getZoneId());
    }

    public static Instant getInstant(){
        return Instant.now(TimeUtils.getCLock());
    }

    public static LocalTime getLocalTime(){
        return LocalTime.now( TimeUtils.getCLock() );
    }

    public static LocalDate getLocalDate(){
        return LocalDate.now();
    }

    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now( TimeUtils.getCLock() );
    }

    public static LocalDateTime getLocalDateTimeSpecified(){
        var dateTime = TimeUtils.getLocalDateTime();
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
    }
}
