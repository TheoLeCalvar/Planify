import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../Services/user.service';
import { DataService } from '../Services/data.service';
import { formatDate } from '@angular/common';
import { History } from '../Models/History';

@Component({
  selector: 'app-historique',
  templateUrl: './historique.component.html',
  styleUrls: ['./historique.component.css']
})
export class HistoriqueComponent implements OnInit {

    dataTable: History[];
    userMail: string;
    isTeachersListEmpty: boolean;
    disabledButtonCreateNewCalendar: boolean; 

    loading: boolean = false;

    constructor(private http: HttpClient, private userService: UserService, private router: Router, private dataService:DataService) { }

    ngOnInit(): void {
        this.userMail = sessionStorage.getItem('userMail') || '';
        this.dataService.listData().subscribe({
          next: (data: History[]) => {
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

    showButtonSolve(calendar: History) {
      return calendar.isFirstItem && !this.dataTable[0].existCalendarFile;
    }

    showButtonCreateNewCalendar() {
      return sessionStorage.getItem("userRole") === "ResponsableTAF";
    }

    showButtonDownloadCsv(calendar: History) {
      return calendar.existCalendarFile;
    }

    showTeacherList(calendar: History) {
      return this.showButtonSolve(calendar) && !this.isTeachersListEmpty;
    }

    goToFormulaire() {
      this.router.navigate(['/responsableTAF']);
    }

    downloadCsv(row: History) {
      this.dataService.getCalendarFile(row.creationDate.toString());
    }
    
    solve() {
        this.loading = true;
        this.dataService.solve().subscribe({
            next: () => {
            console.log("OK");
            },
            error: erreur => {
            console.log(erreur);
            }
        });
    }

    onProcessComplete() {
        // Handle the completion of the backend process
        this.loading = false;
    }

}
