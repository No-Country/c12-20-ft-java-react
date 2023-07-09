package c1220ftjavareact.gym.domain.dto;

import lombok.Data;

/**
 *  Formato de horarios: "hh:mm" para transferencia de datos
 *  Los ids de las relaciones en este caso se necesitan para actualizar las tablas
 *  El administrador debe poder modificar la sala a la que esta asociada una sesion pero no la actividad a la que pertenece
 */
@Data
public class TrainingSessionDTO {

    private Long id;
    private Long activityId;
    private Long roomId;
    private String roomName;
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

