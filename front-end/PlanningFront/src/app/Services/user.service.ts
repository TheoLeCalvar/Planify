import { Injectable } from '@angular/core';
import{HttpClient, HttpHeaders} from '@angular/common/http';
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
export class UserService {

    baseUrl : string = "http://localhost:3202/user/";

    constructor(private http:HttpClient) { }

    getUsers(){
        return this.http.get<User[]>( this.baseUrl+"list", config)
    }

    getUserByMail(mail:string){
      return this.http.get<User>( this.baseUrl+mail, config)
    }
 
}
