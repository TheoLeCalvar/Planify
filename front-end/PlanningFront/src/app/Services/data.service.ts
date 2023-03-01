import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '../Models/Data';
import { User } from '../Models/User';

let config = {
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

  baseUrl : string = "http://localhost:3200/data";

  constructor(private http:HttpClient) { }

  addData(data: Data) {
    //send body with request
    return this.http.post<String>(this.baseUrl+"/save-data-calendar", data, config);
  }
  addPreferences(user:User){
    return this.http.post<String>(this.baseUrl+"/save-preferences", user, config);
  }

  listData(){
    return this.http.get<Data[]>( this.baseUrl+"/list", config)
  }
}
