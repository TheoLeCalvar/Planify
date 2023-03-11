import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders} from '@angular/common/http';
import { Data } from '../Models/Data';
import { User } from '../Models/User';
import { History } from '../Models/History';

const config = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': "*",
    'Access-Control-Allow-Headers': "*",
    'Access-Control-Allow-Credentials': 'true'
    })
  }

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUrl = "http://localhost:3200/data";

  constructor(private http:HttpClient) { }

  addData(data: Data) {
    return this.http.post<string>(`${this.baseUrl}/save-data-calendar`, data, config);
  }
  addPreferences(user:User){
    return this.http.post<string>(`${this.baseUrl}/save-preferences`, user, config);
  }

  listData(){
    return this.http.get<History[]>(`${this.baseUrl}/list`, config)
  }

  solve() {
    return this.http.post<string>(`${this.baseUrl}/solver`, config);
  }

  getCalendarFile(fileName: string) {
    window.open(`${this.baseUrl}/file/${fileName}`, "_blank");
  }

}
