import { Injectable } from "@angular/core";
import { Activity } from "src/app/models/activity.model";
import { Room } from "src/app/models/room.model";
import { ApiService } from "src/app/services/api.service";

@Injectable({
    providedIn: 'root'
  })
  export class ActivityRoomManager {
    rooms: Room[] = [];
    activities: Activity[] = [];
  
      constructor(private api: ApiService){ 
       
      }

      loadRooms(rooms: Room[]){
        rooms.forEach(element => {
            this.rooms.push(element);
        });
      }

      loadActivities(activities: Activity[]){
        activities.forEach(element => {
            this.activities.push(element);
        });
      }
  }