import { CommonModule } from "@angular/common";
import { SuccessAlertComponent } from "../components/alert/alert.component";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
    declarations: [SuccessAlertComponent],
    exports: [SuccessAlertComponent],
    imports: [CommonModule,BrowserModule],
  })
  export class SharedModule {}