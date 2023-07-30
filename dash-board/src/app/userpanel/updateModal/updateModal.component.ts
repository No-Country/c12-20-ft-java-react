import { Component, OnInit } from "@angular/core";
import { UserPanelManager } from "../userpanel.service";

@Component({
    selector: 'update-modal',
    templateUrl: './updateModal.component.html',
    styleUrls: ['./../../common/sharedStyle.css']
  })
export class UpdateModalComponent implements OnInit{

  constructor(protected manager: UserPanelManager ){}


  ngOnInit(): void {
    this.manager.loadUpdateUser(this.manager.service.userActive);
  }

}