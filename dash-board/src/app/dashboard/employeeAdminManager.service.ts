import { EventEmitter, Injectable, OnInit, Output } from '@angular/core';
import { Employees } from '../models/employee.model';
import { RegisterEmployee } from '../models/registerEmployee.model';
import { ApiService } from '../services/api.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeesAdminService {
  employees: Employees[] = [];

  registerEmployee: RegisterEmployee = {
    name: "",
    lastname: "",
    email: "",
    picture: ""
  };

  showEmployeeModal: boolean = false;
  showConfirmModal: boolean = false;
  @Output() employeeRegistered: EventEmitter<boolean>;
  @Output() disabledButton: EventEmitter<boolean>;

  constructor(private api: ApiService){ 
    this.employeeRegistered = new EventEmitter<boolean>();
    this.disabledButton = new EventEmitter<boolean>();
  }

  loadEmployees(employees: any){
      employees.forEach((element: any)=>{
        this.employees.push({
          id: element.id, 
          fullname: element.fullName, 
          email: element.email, 
          role: element.role,
          picture: element.picture,
          checked: !element.deleted,
          deleted: element.deleted
        });
      });
  }

  updateStateEvent(){
    this.showConfirmModal = true;
  }

  cancelUpdateState(){
    this.employees.forEach(
      (employee)=>{
        if(employee.checked === employee.deleted){
          employee.checked = !employee.checked;
        }
      }
    );
    this.disabledButton.emit(true);
    this.showConfirmModal = false;
  }

  sendUpdateStates(){
    let updateEmployees: number[] = []
    this.employees
    .filter((employee)=>employee.checked === employee.deleted)
    .forEach(
      (employee)=>{
        employee.deleted = !employee.deleted;
        updateEmployees.push(employee.id);
      }
    );

    this.api.httpPut("users/employees/state", updateEmployees).subscribe({
      next: (value) => { },
      error: (err) => {
        this.employees
        .filter((employee)=>updateEmployees.includes(employee.id))
        .map((employee)=> {
          employee.deleted=!employee.deleted;
          employee.checked=!employee.checked;
        });
        this.disabledButton.emit(true);
        this.showConfirmModal = false;
      },
      complete: () => {
        this.employees
        .filter((employee)=>updateEmployees.includes(employee.id))
        .forEach((employee)=>{
          console.log("CHECKED STATE: "+employee.checked + " AND DELETED STATE: " + employee.deleted);
        });
        this.disabledButton.emit(true);
        this.showConfirmModal = false;
      }
    });

    
  }

  sendRegisterEmployee(){

    if(this.isInValid()){
      alert("Es Invalido");
      return;
    }
    console.log(this.registerEmployee);
    this.api.httpPost("users/employees", this.registerEmployee).subscribe({
      next: (value) => { 
        this.employees.push({
          id: value, 
          fullname: this.registerEmployee.name+' '+this.registerEmployee.lastname, 
          email: this.registerEmployee.email, 
          role: 'EMPLOYEE',
          picture: this.registerEmployee.picture,
          checked: true,
          deleted: false
        });
      },
      error: (err) => {
        alert("ERROR");
      },
      complete: () => {
        this.employeeRegistered.emit(true);
        this.finishEmployeEvent();
      }
    });
    
  } 

  isInValid(){
    if(this.registerEmployee.name === '' || this.registerEmployee.name.length < 3){
      return true;
    }
    if(this.registerEmployee.lastname === '' || this.registerEmployee.lastname.length < 3){
      return true;
    }
    const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(this.registerEmployee.email === '' ||  !emailRegex.test( this.registerEmployee.email )){
      return true;
    }
    if(this.registerEmployee.picture === ''){
      return true;
    }

    return false;
  }

  finishEmployeEvent(){
    this.showEmployeeModal = false;
    this.registerEmployee.name = "";
    this.registerEmployee.lastname = "";
    this.registerEmployee.email = "";
    this.registerEmployee.picture = "";
  }

  addEmployeeEvent(){
    this.showEmployeeModal = true;
  }
}