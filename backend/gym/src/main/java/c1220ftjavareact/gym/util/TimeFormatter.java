package c1220ftjavareact.gym.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase tiene por objetivo parsear los datos de los dtos de training_session
 * En los dtos de training_session se recibe el LocalTime en formato String "hh:mm"
 */
public class TimeFormatter {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Funcion encargada de convertir horario en formato "hh:mm" a LocalTime
     *
     * @param timeString formato "HH:mm"
     * @return LocalTime Localtime "HH:mm"
     */
    public static LocalTime fromString(String timeString) {
        return LocalTime.parse(timeString, TIME_FORMATTER);
    }

    /**
     * Funcion encargada de convertir horario en formato LocalTime a String "HH:mm"
     *
     * @param time Localtime "HH:mm"
     * @return String formato "HH:mm"
     */
    public static String toString(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }


}
