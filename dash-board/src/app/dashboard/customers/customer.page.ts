import { Component } from "@angular/core";
import { CustomerPaymentManager } from "./customerPaymentManager.service";

@Component({
    selector: 'customer-page',
    templateUrl: './customer.page.html',
    styleUrls: []
  })
export class CustomerPageComponent {
  viewTable: boolean;

  constructor(protected customerManager: CustomerPaymentManager){
    this.viewTable = customerManager.customers.length !== 0;
  }
}