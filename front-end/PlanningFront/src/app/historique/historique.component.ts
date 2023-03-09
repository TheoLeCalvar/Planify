import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../Services/user.service';
import { DataService } from '../Services/data.service';
import { User } from '../Models/User';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css']
})
export class HistoriqueComponent {

    dataTable: any[];
    userMail: string;
    isTeachersListEmpty: boolean; // un booléen pour vérifier si la liste "TeachersList" est vide ou non

    constructor(private http: HttpClient, private userService: UserService, private router: Router, private dataService:DataService) { }

    ngOnInit() {
        this.userMail = sessionStorage.getItem('userMail') || '';
        this.dataService.listData().subscribe(
          (data:any) => {
            console.log(data)
            this.dataTable = data;
            this.dataTable.forEach(calendrier => {
                calendrier.creationDate = formatDate(calendrier.creationDate, 'yyyy-MM-dd', 'en-US')
            });
            console.log(this.dataTable); 
            this.isTeachersListEmpty = this.dataTable.every(
                (calendar) => calendar.teacherWaitingList.length === 0
            );
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
    viewCalendar(row:any){

    }
    solve(){
        this.dataService.solve().subscribe(
            (dataForm: any) => {
              console.log("OK")
            },
            erreur =>{
              console.log(erreur)
            }
        ) 
    }

}
