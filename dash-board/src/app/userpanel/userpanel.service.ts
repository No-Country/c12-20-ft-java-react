import { EventEmitter, Injectable, Output } from '@angular/core';
import { ApiService } from '../services/api.service';
import { UserActive } from '../models/userActive.model';
import { identifierName } from '@angular/compiler';
import { firstValueFrom } from 'rxjs';
import { UserActiveService } from '../common/userActiveService.service';
import { Payment } from '../models/payment.model';

@Injectable({
  providedIn: 'root',
})
export class UserPanelManager{

    showUpdateModal: boolean;
    showHistoryModal: boolean;
    userUpdate: any;
    payments: Payment[] = [];
    countPaying: number = 0;

    @Output() userUpdatedEvent: EventEmitter<boolean>;

    constructor(private api: ApiService, public service: UserActiveService){
      this.showUpdateModal = false;
      this.showHistoryModal = false;
      this.userUpdate = {
        name: "",
        lastName: "",
        email: "",
        picture: "",
        id: ""
      };
      this.userUpdatedEvent = new EventEmitter<boolean>();
     }

     countPayingUp(count: number){
      this.countPaying = count;
     }

     openHistoryModal(id: string){
      
      firstValueFrom(this.api.httpGet("payments/findPaymentComplete/"+1))
      .then((response: Payment[]) => {
        response.forEach((element)=>{
          console.log(element);
          this.payments.push(element);
        });   
        this.showHistoryModal = true;
      })
      .catch((error: any) => {
        alert("FALLE EN EMPLOYEE");
        console.error('Error al obtener datos del servidor:', error);
      });
     }

     closeHistoryModal(){
      this.showHistoryModal = false;
     }

     //Cargo los datos del user Update para el modal
    loadUpdateUser(user: any){
      const names = user.fullName.split(" ");
      this.userUpdate.name = names[0];
      this.userUpdate.lastName = names[1];
      this.userUpdate.email = user.email;
      this.userUpdate.picture = user.picture;
      this.userUpdate.id = user.id;
    }
    //Cierro el modal de actualizar
    closeUpdateModal(){
      this.showUpdateModal = false;
      //Resetea los datos de updateUser
      this.loadUpdateUser(this.service.userActive);
    }

    //Abre el modal
    openUpdateModal(){
      this.showUpdateModal = true;
    }

    //Envio los datos del usuario actualizados
    sendUpdateUser(){
      if(this.isInvalid()){
        alert("No es valido");
        return;
      }
      
      this.api.httpPut("users/"+this.userUpdate.id, this.userUpdate).subscribe({
        next: (value) => { 
          this.service.updateUserActive(this.userUpdate);
        },
        error: (err) => {
          alert("ERROR");
        },
        complete: () => {
          localStorage.setItem('user', this.userUpdate);
          this.userUpdatedEvent.emit(true);
          this.closeUpdateModal();
        }
      });
    }

    //Valido el usuario
    isInvalid(){
      if(this.userUpdate.name === "" || this.userUpdate.name.length < 3 ){
        return true;
      }
      if(this.userUpdate.lastName === "" || this.userUpdate.lastName.length < 3 ){
        return true;
      }

      const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

      if(this.userUpdate.email === "" ||  !emailRegex.test(this.userUpdate.email) ){
        return true;
      }

      if(this.userUpdate.picture === "" ){

        return true;
      }
      return false;
    }
}