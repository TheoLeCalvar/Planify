import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../Services/data.service';
import { formatDate } from '@angular/common';
import { History } from '../Models/History';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    loading = false;

    constructor(private router: Router, private dataService:DataService, private snackBar: MatSnackBar) { }

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
          error: () => {
            this.snackBar.open("Une erreur s'est produite", 'Close', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            });
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
              this.ngOnInit();
              this.loading = false;
            },
            error: () => {
              this.snackBar.open("Une erreur s'est produite", 'Close', {
                duration: 5000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
            }
        });
    }

}
