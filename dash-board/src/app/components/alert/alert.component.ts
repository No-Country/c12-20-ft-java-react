import { Component } from "@angular/core";
import { CustomerPaymentManager } from "src/app/dashboard/customers/customerPaymentManager.service";
import { EmployeesAdminService } from "src/app/dashboard/employeeAdminManager.service";
import { UserPanelManager } from "src/app/userpanel/userpanel.service";


@Component({
    selector: 'success-alert',
    templateUrl: './alert.component.html',
    styleUrls: ['./../../common/sharedStyle.css'],
    providers: []
  })
export class SuccessAlertComponent{

    showAlert: boolean = false;

    message: string = "";
    title: string = "";

    constructor(
        private customerManager: CustomerPaymentManager, 
        private employeeManger: EmployeesAdminService,
        private updateUserManager: UserPanelManager
        ){
        this.customerManager.paymentRegistered.subscribe(
            (registeredPayment: boolean)=>{
                this.title = "Registered Payment";
                this.message = "The subscription payment was registered correctly.";
                this.showAlert = registeredPayment;
                setTimeout(() => {
                    this.showAlert = false;
                  }, 1500);
            }
        );

        this.employeeManger.employeeRegistered.subscribe(
            (employeRegistered: boolean)=>{
                this.title = "Registered Employee";
                this.message = "The employee was successfully registered.";

                this.showAlert = employeRegistered;
                setTimeout(() => {
                    this.showAlert = false;
                  }, 1500);
            }
        );

        this.updateUserManager.userUpdatedEvent.subscribe(
            (isUpdated: boolean)=>{
                this.title = "Update was completed";
                this.message = "Your account information was successfully updated!";

                this.showAlert = isUpdated;
                setTimeout(() => {
                    this.showAlert = false;
                  }, 1500);
            }
        );
    }
}