import { Component } from '@angular/core';
import { ModalService } from '../../../services/modalService.service';
import { CustomerPaymentManager } from 'src/app/dashboard/customers/customerPaymentManager.service';
import { ActivityRoomManager } from '../activityRoomManager.service';

@Component({
  selector: 'room-table',
  templateUrl: './roomTable.component.html',
  styleUrls: ['./../../../common/sharedStyle.css']
})
export class RoomTableComponent {


  constructor(protected manager: ActivityRoomManager){ 

  }


}