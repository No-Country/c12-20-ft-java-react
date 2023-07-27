import { EventEmitter, Injectable, Output } from '@angular/core';
import { Customer } from '../../models/customer.model';
import { Payment } from '../../models/payment.model';
import { ApiService } from 'src/app/services/api.service';
import { Room } from 'src/app/models/room.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerPaymentManager {
 
  customers: Customer[] = [];
  rooms: Room[] = [];
  registeredPayment: Payment;
  @Output() paymentRegistered: EventEmitter<boolean>;

    constructor(private api: ApiService){ 
      this.paymentRegistered = new EventEmitter<boolean>();
      this.registeredPayment = {
        subscriptionId: 0,
        method: "",
        paymentAt: "",
        expiredAt: ""
      };
    }

    loadCustomers(customers: any){
      customers.forEach((element: any)=>{
        this.customers.push({
          subscriptionId: element.id, 
          subscriberName: element.fullName, 
          subscriberEmail: element.email, 
          subscriberPicture: element.picture,
          activity: element.activity,
          roomName: element.roomName,
          state: element.state,
          classTime: element.classTime
        }
          );
        }
      );
    }

    recordPayment(): void{
      this.api.httpPost("payments", this.registeredPayment).subscribe({
        next: (value) => {
          
         },
        error: (err) => {
          console.log(err);
          alert(err);
        },
        complete: () => {
          this.customers.filter((customer)=> customer.subscriptionId === this.registeredPayment.subscriptionId.toString() )
          .forEach((customer)=> customer.state = "ACTIVE");
          this.paymentRegistered.emit(true);
        }
      });
    }

    cancelPayment(): void{
      this.registeredPayment = {
        subscriptionId: 0,
        method: "",
        paymentAt: "",
        expiredAt: ""
      };
    }
}