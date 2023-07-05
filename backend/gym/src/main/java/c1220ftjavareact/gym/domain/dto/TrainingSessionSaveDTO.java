package c1220ftjavareact.gym.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

/**
 *  Formato de horarios: "hh:mm" para transferencia de datos
 *  Los ids de las relaciones en este caso se necesitan para crear las relaciones
 */
@Data
public class TrainingSessionSaveDTO {

    private Long activity_id;
    private Long room_id;
    private int capacity;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time_start;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time_finish;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
}
