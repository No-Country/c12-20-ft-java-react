import { Component, OnInit } from "@angular/core";
import { UserPanelManager } from "../userpanel.service";
import { ApiService } from "src/app/services/api.service";

@Component({
    selector: 'history-modal',
    templateUrl: './historyModal.component.html',
    styleUrls: ['./../../common/sharedStyle.css']
  })
export class HistoryModalComponent implements OnInit{

  payments: any = [];
  subscriptionSelect: string = "";

  constructor(protected manager: UserPanelManager, private api: ApiService ){
    console.log(manager.payments);
  }


  ngOnInit(): void {
    
  }

}