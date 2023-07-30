package c1220ftjavareact.gym.training.model;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;

@Getter
@Setter
public class UnAvailableTimes {
    ArrayList<RoomTimes> listRooms;

    public UnAvailableTimes() {
        this.listRooms = new ArrayList<>();
    }

    public void addNewRoom(Long roomId, String roomName) {
        RoomTimes aux = new RoomTimes();
        aux.setRoomName(roomName);
        aux.setRoomId(roomId);
        listRooms.add(aux);

    }

    public void addTime(Long roomId, DayOfWeek day, String[] saveTime) {
        for (RoomTimes item : listRooms) {
            if (item.getRoomId().equals(roomId)) {
                item.add(day, saveTime);
                return;
            }
        }
    }
}
