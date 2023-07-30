import { Component, OnInit } from "@angular/core";
import { ApiService } from "../services/api.service";
import { firstValueFrom } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { UserPanelManager } from "./userpanel.service";
import { UserActiveService } from "../common/userActiveService.service";

@Component({
    selector: 'usepanel-page',
    templateUrl: './userpanel.page.html',
    styleUrls: ['./../common/sharedStyle.css']
  })
export class UserPanelPageComponent implements OnInit{
    activities: any[] = [];

    constructor(
        private route: ActivatedRoute,
        private api: ApiService,
        protected manager: UserPanelManager,
        protected userManager: UserActiveService
      ){ }

    ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      this.userManager.retriveUser(id!);

        if( this.activities.length === 0 ){
            firstValueFrom(this.api.httpGet("users/"+this.userManager.userActive.id+"/subscriptions"))
            .then((response: any) => {
              this.activities = response;
            })
            .catch((error: any) => {
              console.error('Error al obtener datos del servidor:', error);
            });
        }



    }
}