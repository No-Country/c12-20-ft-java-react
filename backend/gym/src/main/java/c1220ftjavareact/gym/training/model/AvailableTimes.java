package c1220ftjavareact.gym.training.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Los horarios podrian gestionarse en intervalos de 30m para tener la mayor personalizacion posible
 */
@Getter
@Setter
public class AvailableTimes {
    ArrayList<RoomTimes> listRooms;

    public AvailableTimes() {
        this.listRooms = new ArrayList<>();
    }

    public void addNewRoom(Long roomId, String roomName) {
        RoomTimes aux = new RoomTimes();
        aux.setRoomName(roomName);
        aux.setRoomId(roomId);
        listRooms.add(aux);

    }

    public boolean addTime(Long roomId, DayOfWeek day, AuxTimes saveTime) {
        for(RoomTimes item : listRooms) {
             if(item.getRoomId() == roomId) {
                 item.add(day, saveTime);
                 return true;
             }
        }
        return false;
    }
}
