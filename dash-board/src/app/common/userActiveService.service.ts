import { EventEmitter, Injectable, Output } from '@angular/core';
import { ApiService } from '../services/api.service';
import { UserActive } from '../models/userActive.model';
import { identifierName } from '@angular/compiler';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserActiveService {

    userActive!: UserActive;

    constructor(private api: ApiService){}

     retriveUser(id: string){
      let user = localStorage.getItem("user");

      if(user === null || user === undefined){
        firstValueFrom(this.api.httpGet("active/"+id))
          .then((response: any) => {
            
            localStorage.setItem('user', JSON.stringify(response));
          })
          .catch((error: any) => {
            window.location.href = 'http://localhost:4200/notfound';
          }
        );
        
      } else {
        const userObj = JSON.parse(user);

        if(id === null || userObj.id != id){
          alert("PARAA "+id+" ID OBJ "+userObj.id);
          window.location.href = 'http://localhost:4200/notfound';
        }
      }

      this.loadUserActive(JSON.parse( localStorage.getItem('user') ?? '{}') );  
     }

    loadUserActive(user: any){
      if(this.userActive === undefined || this.userActive === null){
        this.userActive = {
          id: user.id,
          fullName: user.fullName,
          email: user.email,
          role: user.role,
          picture: user.picture,
          token: user.token
        };
      }
    }

    updateUserActive(user: any){
      this.userActive = {
        id: user.id,
        fullName: user.name+' '+user.lastName,
        email: user.email,
        role: user.role,
        picture: user.picture,
        token: user.token
      };
    }

}