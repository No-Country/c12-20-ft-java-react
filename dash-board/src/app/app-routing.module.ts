import { NgModule } from '@angular/core';
import { RouterModule, } from '@angular/router';
import { DashboardModule } from './dashboard/dashboard.module';

import { UserPanelModule } from './userpanel/userpanel.module';
import { RedirectComponent } from './common/redirect/redirect.component';

import { NotFoundComponent } from './common/redirect/NotFound.component';


@NgModule({
  imports: [
    RouterModule.forRoot([
      { 
        path: 'dashboard/:id', 
        component: DashboardModule
      },
      { 
        path: '', 
        component: RedirectComponent,
      },
      {
        path: 'user/:id',
        component: UserPanelModule
      },
      {
        path: '**',
        component: NotFoundComponent
      }
    ]
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }