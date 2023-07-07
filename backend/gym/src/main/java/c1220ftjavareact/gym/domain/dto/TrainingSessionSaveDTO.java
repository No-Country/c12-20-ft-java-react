package c1220ftjavareact.gym.domain.dto;

import lombok.Data;

/**
 *  Formato de horarios: "hh:mm" para transferencia de datos
 *  Los ids de activity y room en este caso se necesitan para crear las relaciones
 */
@Data
public class TrainingSessionSaveDTO {

    private Long activityId;
    private Long roomId;
    private int capacity;
    private String timeStart;
    private String timeEnd;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
}

