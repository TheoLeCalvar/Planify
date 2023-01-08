import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Data } from '../Models/Data';

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

  baseUrl : string = "http://localhost:3200/data/solver";

  constructor(private http:HttpClient) { }

  addData(data: Data):Observable<Data>{
    //send body with request
    return this.http.post<Data>(this.baseUrl, data, config);
  }
}
