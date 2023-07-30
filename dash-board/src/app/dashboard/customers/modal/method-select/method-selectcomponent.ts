import { AfterViewInit, Component, EventEmitter, Output } from '@angular/core';


@Component({
  selector: 'method-select',
  templateUrl: './method-select.component.html',
  styleUrls: []
})
export class MethodSelectComponent {
  open: boolean;
  items: string[];
  selectedItem: string;
  @Output() selectedItemChange: EventEmitter<string> = new EventEmitter<string>();


  constructor() {
    this.open = false;
    this.items = [
      'CASH',
      'DEBIT CARD',
      'CREDIT CARD',
      'PREPAID CARD',
      'Wire Transfer'
    ];
    this.selectedItem = "Pick an method...";
  }

  toggle() {
    this.open = !this.open;
  }

  choose(item: string) {
    this.selectedItem = item;
    this.open = false;
    this.selectedItemChange.emit(this.selectedItem);
  }
}