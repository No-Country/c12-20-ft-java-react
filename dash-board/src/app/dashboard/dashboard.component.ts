import { Component, OnInit } from "@angular/core";

import { ApiService } from "../services/api.service";
import { EmployeesAdminService } from "./employeeAdminManager.service";
import { firstValueFrom } from "rxjs";
import { CustomerPaymentManager } from "./customers/customerPaymentManager.service";
import { ActivatedRoute } from "@angular/router";
import { UserActiveService } from "../common/userActiveService.service";
import { ActivityRoomManager } from "./activityRoom/activityRoomManager.service";

@Component({
    selector: 'dashboard-page',
    templateUrl: './dashboard.component.html',
    styleUrls: []
  })
export class DashBoardPageComponent implements OnInit{
  
  constructor(
      private route: ActivatedRoute,
      private api: ApiService, 
      private employeeManager: EmployeesAdminService, 
      private customerManager: CustomerPaymentManager,
      private activityRoomManager: ActivityRoomManager,
      private userActiveManager: UserActiveService
    ) {   
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log(id);
    this.userActiveManager.retriveUser(id!);

    if(this.customerManager.customers.length === 0){

      firstValueFrom(this.api.httpGet("subscriptions"))
      .then((response: any) => {
        this.customerManager.loadCustomers(response);
      })
      .catch((error: any) => {
        alert("FALLE EN CUSTOMERS");
        console.error('Error al obtener datos del servidor:', error);
      });

    }

    if(this.employeeManager.employees.length === 0){
    
      firstValueFrom(this.api.httpGet("users/employees"))
        .then((response: any) => {
          this.employeeManager.loadEmployees(response);
        })
        .catch((error: any) => {
          alert("FALLE EN EMPLOYEE");
          console.error('Error al obtener datos del servidor:', error);
        });

    }

    if(this.activityRoomManager.rooms.length === 0){
      firstValueFrom(this.api.httpGet("rooms"))
      .then((response: any) => {
        this.activityRoomManager.loadRooms(response);
      })
      .catch((error: any) => {
        alert("FALLE EN EMPLOYEE");
        console.error('Error al obtener datos del servidor:', error);
      });
    }

    if(this.activityRoomManager.activities.length === 0){
      firstValueFrom(this.api.httpGet("activities"))
      .then((response: any) => {
        this.activityRoomManager.loadActivities(response);
      })
      .catch((error: any) => {
        alert("FALLE EN EMPLOYEE");
        console.error('Error al obtener datos del servidor:', error);
      });
    }
  }
  
}