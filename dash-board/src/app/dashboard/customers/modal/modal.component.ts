import {  Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { ModalService } from 'src/app/services/modalService.service';
import { MethodSelectComponent } from './method-select/method-selectcomponent';
import { CustomerPaymentManager } from 'src/app/dashboard/customers/customerPaymentManager.service';


@Component({
  selector: 'modal-payment',
  templateUrl: './modal.component.html',
  styleUrls: ['./../../../common/sharedStyle.css'],
  providers: []
})
export class ModalPaymentComponent {
  @ViewChild('paymentSelect') paymentSelect!: MethodSelectComponent;

  constructor( protected service: ModalService, protected manager: CustomerPaymentManager){ }

  //Envia los datos del form Data a la tabla
  onSubmit(expiredInput: HTMLInputElement){
    if(this.isInValid( parseInt(expiredInput.value) )){
      return;
    }
   
    const date = this.getDate();
    this.manager.registeredPayment.method = this.paymentSelect.selectedItem;
    this.manager.registeredPayment.paymentAt = date;
    this.manager.registeredPayment.expiredAt = this.addDaysToDate(date, parseInt(expiredInput.value));

    this.manager.recordPayment();
    this.service.closeModal();
  }

  //Verifica la validez de los datos para el formData 
  isInValid(expiredDays: number): boolean{
    if(expiredDays < 0 || expiredDays > 31){
      alert("Numero invalido");
      return true;
    }
    if(expiredDays === 0 || expiredDays === undefined){
      alert("Los dias para expirar no pueden estar vacios");
      return true;
    }
    if(this.paymentSelect.selectedItem === "" || this.paymentSelect.selectedItem === undefined || this.paymentSelect.selectedItem.includes("Pick")){
      alert("Primero debes registrar un metodo de pago");
      return true;
    }

    return false;
  }

  //Recupera la fecha actual
  getDate(): string{
    const currentDate = new Date();
    return this.formatDay(currentDate);
  }

  //Añade X dias extra a una fecha y retorna la nueva fecha
  addDaysToDate(originalDate: string, daysToAdd: number) {
    const updatedDate = new Date(originalDate);
    updatedDate.setDate(updatedDate.getDate() + daysToAdd);
  
    return this.formatDay( updatedDate );
  }

  //Recibe un Date object y lo transforma a un string formato yyy-mm-dd
  formatDay(dateObject: Date){
    const year = dateObject.getFullYear();
    const month = String(dateObject.getMonth() + 1).padStart(2, '0');
    const day = String(dateObject.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  
}