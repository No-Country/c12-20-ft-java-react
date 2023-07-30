import { Component, OnInit } from "@angular/core";
import { UserPanelManager } from "../userpanel.service";

@Component({
    selector: 'subscribe-modal',
    templateUrl: './subscribeModal.component.html',
    styleUrls: ['./../../common/sharedStyle.css']
  })
export class SubscribeModalComponent implements OnInit{

  constructor(protected manager: UserPanelManager ){}


  ngOnInit(): void {
    this.manager.loadUpdateUser(this.manager.service.userActive);
  }

}