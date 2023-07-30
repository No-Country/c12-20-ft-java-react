import { Component } from '@angular/core';
import { ModalService } from '../../../services/modalService.service';
import { CustomerPaymentManager } from 'src/app/dashboard/customers/customerPaymentManager.service';

@Component({
  selector: 'customer-table',
  templateUrl: './table.component.html',
  styleUrls: ['./../../../common/sharedStyle.css']
})
export class CustomerTableComponent {
  searchTerm: string = '';

  constructor(protected service: ModalService, protected manager: CustomerPaymentManager){ 
    this.manager.paymentRegistered.subscribe(
      (registeredPayment: boolean)=>{
          if(registeredPayment){
            this.manager.customers.forEach(element => {
              if(parseInt(element.subscriptionId) === this.manager.registeredPayment.subscriptionId){
                element.state = "ACTIVE";
              }
            });
          }
      }
  );

  }

  paymentEvent(id: string){
    this.manager.registeredPayment.subscriptionId = parseInt(id);
    this.service.openPaymentModal();
  }
}