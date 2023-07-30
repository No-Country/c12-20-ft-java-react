import { Component, OnInit, inject } from '@angular/core';
import { CustomerPaymentManager } from 'src/app/dashboard/customers/customerPaymentManager.service';
import { EmployeesAdminService } from 'src/app/dashboard/employeeAdminManager.service';
import { Router } from "@angular/router";
import { UserActiveService } from 'src/app/common/userActiveService.service';


@Component({
  selector: 'side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit{

  constructor(
      private router: Router, 
      protected customerManager: CustomerPaymentManager, 
      protected employeeManager: EmployeesAdminService,
      protected service: UserActiveService
    ) { }
    
  ngOnInit(): void {
    this.service = inject(UserActiveService);
  }

  isLinkActive(url: string): boolean {
    return this.router.isActive(url, true);
  }

  rediret(){
    window.location.href = 'https://marvelous-fairy-2dbca1.netlify.app/';
  }
}