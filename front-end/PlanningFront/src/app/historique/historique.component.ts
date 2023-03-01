import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../Services/user.service';
import { DataService } from '../Services/data.service';
import { User } from '../Models/User';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css']
})
export class HistoriqueComponent {

    data: any[];
    userMail: string;

    constructor(private http: HttpClient, private userService: UserService, private router: Router, private dataService:DataService) { }

    ngOnInit() {
      this.userMail = sessionStorage.getItem('userMail') || '';
      this.dataService.listData().subscribe(
          (data:any) => {
              console.log(data)
          },
          erreur =>{
            console.log(erreur)
          }
      ) 
    }

    goToFormulaire() {
        this.userService.getUserByMail(this.userMail).subscribe(
            (user: User) => {
              console.log(user);
              if(user.role == "Enseignant"){
                this.router.navigate(['/enseignant']);
                
              }
              else if (user.role=="ResponsableTAF"){
                this.router.navigate(['/responsableTAF']);
                console.log("aaaaaa")
                console.log(this.router.navigate(['/responsableTAF']));
              }
              //this.userService.setUserEmail(this.userEmail);

            },
            erreur =>{
              console.log(erreur)
            }
        ) 
        //this.router.navigate(['/responsableTAF']);
    }
    logout() {

        this.router.navigate(['/login']);
        // Redirect to login page
    }
    viewCalendar(row:any){

    }

}
