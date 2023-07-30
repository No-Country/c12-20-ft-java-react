import { Component } from '@angular/core';
import { ModalService } from '../../../services/modalService.service';
import { CustomerPaymentManager } from 'src/app/dashboard/customers/customerPaymentManager.service';
import { ActivityRoomManager } from '../activityRoomManager.service';

@Component({
  selector: 'activity-table',
  templateUrl: './activityTable.component.html',
  styleUrls: ['./../../../common/sharedStyle.css']
})
export class ActivityTableComponent {


  constructor(protected manager: ActivityRoomManager){ 

  }
}