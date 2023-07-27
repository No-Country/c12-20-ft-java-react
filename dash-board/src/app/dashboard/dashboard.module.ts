import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CustomerPageComponent } from './customers/customer.page';
import { DashBoardPageComponent } from './dashboard.component';
import { SideBarComponent } from '../components/side-bar/side-bar.component';
import { CustomerTableComponent } from './customers/table/table.component';
import { ModalPaymentComponent } from './customers/modal/modal.component';
import { MethodSelectComponent } from './customers/modal/method-select/method-selectcomponent';
import { EmployeeTableComponent } from './employee/table/table.component';
import { EmployeeModalComponent } from './employee/modal/addEmployee.component';
import { ConfirmModalComponent } from './employee/confirm/confirm.component';
import { SuccessAlertComponent } from '../components/alert/alert.component';
import { FilterPipe } from '../pipes/filter.pipe';
import { EmployeePageComponent } from './employee/employee.page';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { SharedModule } from '../common/shared.module';
import { ActivityRoomPageComponent } from './activityRoom/activityRoom.page';
import { RoomTableComponent } from './activityRoom/roomTable/roomTable.component';
import { TrainingPageComponent } from './trainingSession/training.component';

@NgModule({
  declarations: [
    DashBoardPageComponent,

    CustomerTableComponent,
    ModalPaymentComponent,
    MethodSelectComponent,

    EmployeeTableComponent,
    EmployeeModalComponent,
    ConfirmModalComponent,

    RoomTableComponent,
    
    SideBarComponent,
    FilterPipe,

    EmployeePageComponent,
    CustomerPageComponent,
    TrainingPageComponent,
    ActivityRoomPageComponent
    
  ],
  imports: [
    BrowserModule,
    SharedModule,
    HttpClientModule,
    FormsModule,
    CommonModule, 
    RouterModule.forChild([
      { 
        path: 'dashboard/:id',
        component: DashBoardPageComponent,
        children: [
          { 
            path: 'customers', 
            component: CustomerPageComponent 
          },
          { 
            path: '', 
            component: CustomerPageComponent 
          },
          { 
            path: 'employees',
            component: EmployeePageComponent
          },
          { 
            path: 'activity-rooms',
            component: ActivityRoomPageComponent
          },
          {
            path: 'training',
            component: TrainingPageComponent
          }
        ]
      }
    ]
    
    )
  ]
})
export class DashboardModule {}