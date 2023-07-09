package c1220ftjavareact.gym.util;

import java.time.*;

public class TimeUtils {

    public Long getDateMillis(){
        return System.currentTimeMillis();
    }
    public ZoneId getZoneId(){
        return ZoneId.systemDefault();
    }
    public Clock getCLock(){
        return Clock.system(this.getZoneId());
    }

    public Instant getInstant(){
        return Instant.now(this.getCLock());
    }

    public LocalTime getLocalTime(){
        return LocalTime.now( this.getCLock() );
    }

    public LocalDate getLocalDate(){
        return LocalDate.now();
    }

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now( this.getCLock() );
    }

    public LocalDateTime getLocalDateTimeSpecified(){
        var dateTime = this.getLocalDateTime();
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
    }
}
