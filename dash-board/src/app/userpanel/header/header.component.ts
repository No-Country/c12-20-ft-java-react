import { Component } from "@angular/core";

@Component({
    selector: 'usepanel-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
  })
export class UserPanelHeaderComponent {
  user: any = JSON.parse(localStorage.getItem('user') ?? '{}');
}