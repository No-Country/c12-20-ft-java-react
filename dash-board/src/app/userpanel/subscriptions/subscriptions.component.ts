import { Component, OnInit } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { ApiService } from "src/app/services/api.service";
import { UserPanelManager } from "../userpanel.service";
import { UserActiveService } from "src/app/common/userActiveService.service";


@Component({
    selector: 'subscriptions-section',
    templateUrl: './subscriptions.component.html',
    styleUrls: ['./subscriptions.component.css', './../../common/sharedStyle.css']
  })
export class SubscriptionsComponent implements OnInit{
  sessions: any[] = [];

  constructor(private api: ApiService, protected manager: UserPanelManager, protected userManager: UserActiveService){ }

  ngOnInit(): void {
    console.log(this.sessions);
    console.log(this.sessions.length);
    if( this.sessions.length === 0 ){
      firstValueFrom(this.api.httpGet("subscriptions/users/"+this.userManager.userActive.id))
      .then((response: any) => {
     
        this.sessions = response;
      })
      .catch((error: any) => {
        console.error('Error al obtener datos del servidor:', error);
      });

      this.manager.countPayingUp(
          this.sessions.filter((element)=>element.role = 'ACTIVE').length
        );
  }
  }


    

}