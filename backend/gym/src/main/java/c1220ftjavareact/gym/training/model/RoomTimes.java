package c1220ftjavareact.gym.training.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

@Setter
@Getter
public class RoomTimes {
    private Long roomId;
    private String roomName;
    HashMap<Enum<DayOfWeek>, ArrayList<AuxTimes>> timesArray;

    public RoomTimes() {

        this.timesArray = new HashMap<>();
    }

    public void add(DayOfWeek day, AuxTimes times) {
        /// obtengo los datos del arraylist
        ArrayList<AuxTimes> aux = timesArray.get(day);
        /// inicializar en caso de ser nulo
        if(aux == null) {
            aux = new ArrayList<>();
        }
        /// agrego un time nuevo
        aux.add(times);
        /// actualizo datos en hashmap
        timesArray.put(day, aux);
    }
}
