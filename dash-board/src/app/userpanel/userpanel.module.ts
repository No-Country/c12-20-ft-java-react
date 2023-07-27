import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { RouterModule } from "@angular/router";
import { UserPanelPageComponent } from "./userpanel.page";
import { UserPanelHeaderComponent } from "./header/header.component";
import { SubscriptionsComponent } from "./subscriptions/subscriptions.component";
import { UpdateModalComponent } from "./updateModal/updateModal.component";
import { SuccessAlertComponent } from "../components/alert/alert.component";
import { SharedModule } from "../common/shared.module";
import { HistoryModalComponent } from "./historyModal/historyModal.component";


@NgModule({
    declarations: [
      UserPanelHeaderComponent,
      SubscriptionsComponent,
      UpdateModalComponent,
      HistoryModalComponent,
      UserPanelPageComponent
    ],
    imports: [
      BrowserModule,
      SharedModule,
      HttpClientModule,
      FormsModule,
      CommonModule, 
      RouterModule.forChild([
        { 
          path: 'user/:id',
          component: UserPanelPageComponent
        }
      ]
      )
    ]
  })
  export class UserPanelModule {}