import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-redirect',
  template: '<p>Adios</p>',
  styleUrls: []
})
export class RedirectComponent implements OnInit {
  constructor(){}
  ngOnInit() {
    window.location.href = 'https://marvelous-fairy-2dbca1.netlify.app/';
   
  }

}
