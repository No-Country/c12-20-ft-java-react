import { Component } from '@angular/core';
import { EmployeesAdminService } from 'src/app/dashboard/employeeAdminManager.service';

@Component({
  selector: 'employee-table',
  templateUrl: './table.component.html',
  styleUrls: ['./../../../common/sharedStyle.css']
})
export class EmployeeTableComponent {
  searchTerm: string = '';
  disabledButton: boolean = true;

  constructor(protected manager: EmployeesAdminService){ 
    this.manager.disabledButton.subscribe(
      (disabled)=>{
        this.disabledButton = disabled;
      }
    );
  }

  checkedControl(employeeHtml: any) {

    this.manager.employees
    .filter((employee)=>employee.id === employeeHtml.id)
    .map((empoyee)=> empoyee.checked = !empoyee.checked);

    this.disabledButton = this.manager.employees.filter(employee => employee.checked === employee.deleted).length < 1;
  }

}