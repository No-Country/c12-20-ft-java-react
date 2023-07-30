// app.module.ts (o el nombre de tu módulo)
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  //Recibe una lista de objetos y los filtra segun el termino proporcionado
  transform(items: any[], searchTerm: string): any[] {
    if (!searchTerm) {
      return items; // Si no hay término de búsqueda, devolver todos los elementos
    }

    searchTerm = searchTerm.toLowerCase(); // Convertir el término de búsqueda a minúsculas
    return items.filter(item => {
      // Filtrar los elementos que contienen el término de búsqueda en el nombre, email, etc.
      return (
        item.subscriberName.toLowerCase().includes(searchTerm) ||
        item.subscriberEmail.toLowerCase().includes(searchTerm) ||
        item.activity.toLowerCase().includes(searchTerm) ||
        item.roomName.toLowerCase().includes(searchTerm) ||
        item.classTime.toLowerCase().includes(searchTerm) ||
        item.state.toLowerCase().includes(searchTerm)
      );
    });
  }

}