import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ModalService{
    showPaymentModal: boolean = false;

    constructor(){ }

    openPaymentModal(): void {
        this.showPaymentModal = true;
    }
    
    closeModal(): void{
        this.showPaymentModal = false;
    }
    
}