import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    private devUrl: string = 'http://localhost:8080/api/v1'; 
    private prodUrl: string = 'https://c12-20-ft-java-react-production.up.railway.app/api/v1'; 

    constructor(private http: HttpClient) { }

    httpGet(path: string): Observable<any> {
        const url = `${this.prodUrl}/${path}`;
        console.log(url);
        return this.http.get<any>(url);
    }

    httpPost(path: string, body: any): Observable<any> {
      const url = `${this.prodUrl}/${path}`;
      return this.http.post<any>(url, body);
    }

    httpPut(path: string, body: any): Observable<any> {
      console.log(path);
        const url = `${this.prodUrl}/${path}`;
        return this.http.put<any>(url, body);
      }

}