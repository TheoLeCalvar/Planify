import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../Services/user.service';
import { DataService } from '../Services/data.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css']
})
export class HistoriqueComponent {

    dataTable: any[];
    userMail: string;
    isTeachersListEmpty: boolean;
    disabledButtonCreateNewCalendar: boolean; 

    constructor(private http: HttpClient, private userService: UserService, private router: Router, private dataService:DataService) { }

    ngOnInit() {
        this.userMail = sessionStorage.getItem('userMail') || '';
        this.dataService.listData().subscribe({
          next: (data: any) => {
            this.dataTable = data;
            this.dataTable.sort((calendarA, calendarB) => calendarA.creationDate < calendarB.creationDate ? 1 : -1);
            this.dataTable.forEach(calendar => {
              calendar.creationDateFormat = formatDate(calendar.creationDate, 'yyyy-MM-dd', 'en-US')
            });
            if (this.dataTable.length > 0) {
              this.dataTable[0].isFirstItem = true;
              this.isTeachersListEmpty = this.dataTable[0].teacherWaitingList.length === 0;
              this.disabledButtonCreateNewCalendar = !this.dataTable[0].existCalendarFile;
            }
          },
          error: erreur => {
            console.log(erreur)
          }
        }) 
    }

    showButtonSolve(calendar: any) {
      return calendar.isFirstItem && !this.dataTable[0].existCalendarFile;
    }

    showButtonCreateNewCalendar() {
      return sessionStorage.getItem("userRole") === "ResponsableTAF";
    }

    showButtonDownloadCsv(calendar: any) {
      return calendar.existCalendarFile;
    }

    goToFormulaire() {
      this.router.navigate(['/responsableTAF']);
    }

    downloadCsv(row: any){
      this.dataService.getCalendarFile(row.creationDate);
      
      // .subscribe({
      //   next: (res: any) => {
      //     console.log("OK");
      //   },
      //   error: erreur => {
      //     console.log(erreur);
      //   }
      // });
    }
    
    solve(){
      this.dataService.solve().subscribe({
        next: (dataForm: any) => {
          console.log("OK");
        },
        error: erreur => {
          console.log(erreur);
        }
      });
    }

}
