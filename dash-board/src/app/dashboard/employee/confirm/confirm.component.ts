import { Component } from '@angular/core';
import { EmployeesAdminService } from 'src/app/dashboard/employeeAdminManager.service';

@Component({
  selector: 'confirm-modal',
  templateUrl: './confirm.component.html',
  styleUrls: ['./../../../common/sharedStyle.css']
})
export class ConfirmModalComponent {

  constructor(protected manager: EmployeesAdminService){ 

  }

}