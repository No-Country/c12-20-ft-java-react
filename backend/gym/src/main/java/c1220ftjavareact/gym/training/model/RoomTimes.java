package c1220ftjavareact.gym.training.model;

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
    HashMap<Enum<DayOfWeek>, ArrayList<String[]>> timesArray;

    public RoomTimes() {

        this.timesArray = new HashMap<>();
    }

    public void add(DayOfWeek day, String[] times) {
        /// obtengo los datos del arraylist
        ArrayList<String[]> aux = timesArray.get(day);
        /// inicializar en caso de ser nulo
        if (aux == null) {
            aux = new ArrayList<>();
        }
        /// agrego un time nuevo
        String[] auxTimes = new String[2];
        auxTimes[0] = times[0];
        auxTimes[1] = times[1];
        aux.add(auxTimes);

        /// actualizo datos en hashmap
        timesArray.put(day, aux);
    }
}
