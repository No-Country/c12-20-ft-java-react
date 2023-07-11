package c1220ftjavareact.gym.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Clase de utilidad principalmente encargada de crear Fechas y hora con la actual del sistema
 */
public class TimeUtils {

    /**
     * Rcupera el tiempo actual en Millisegundos, sirve para crear un Date
     *
     * @return Tiempo actual en milisegundos
     */
    public static Long getDateMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Rcupera la Zona del sistema por default
     *
     * @return ZonaID del sistema
     */
    public static ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    /**
     * Rcupera el reloj del sistema por defecto en base a la Zona por defecto
     *
     * @return Clock / Reloj del sistema
     */
    public static Clock getCLock() {
        return Clock.system(TimeUtils.getZoneId());
    }

    /**
     * Rcupera el Instante actual del Reloj del sistema
     *
     * @return Instant El instante actual del reloj del sistema
     */
    public static Instant getInstant() {
        return Instant.now(TimeUtils.getCLock());
    }

    /**
     * Rcupera un LocalTime, basado en el reloj del sistema
     *
     * @return LocalTime Actual del sistema
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now(TimeUtils.getCLock());
    }

    /**
     * Rcupera un LocalDate Contiene la fecha actual
     *
     * @return LocalDate Fecha Actual
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * Rcupera la fecha y hora actual basado en el reloj del sistema
     *
     * @return LocalDateTime Fecha y hora actual
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now(TimeUtils.getCLock());
    }

    /**
     * Recupera la fecha y hora actual del sistema sin Segundos
     *
     * @return LocalDateTime Fecha y hora actual
     */
    public static LocalDateTime gerFormatedLocalDateTime() {
        var dateTime = TimeUtils.getLocalDateTime();
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
    }

    /**
     * Recupera la fecha y hora en el formato "yyyy-MM-dd HH:mm" en String
     *
     * @return String En formato "yyyy-MM-dd HH:mm"
     */
    public static String formatToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
