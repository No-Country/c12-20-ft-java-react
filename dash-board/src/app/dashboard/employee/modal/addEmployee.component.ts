import { Component } from '@angular/core';
import { EmployeesAdminService } from 'src/app/dashboard/employeeAdminManager.service';

@Component({
  selector: 'employee-modal',
  templateUrl: './addEmployee.component.html',
  styleUrls: ['./../../../common/sharedStyle.css', './addEmployee.component.css']
})
export class EmployeeModalComponent {

  constructor(protected manager: EmployeesAdminService){ }



}