import { formatDate } from '@angular/common';
import { Component, OnInit,ViewEncapsulation  } from '@angular/core';
import { Data } from '../Models/Data';
import { Indisponibilite } from '../Models/Indisponibilite';
import { Module } from '../Models/Module';
import { DataService } from '../Services/data.service';
import { UserService } from '../Services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RtafFormComponent implements OnInit {

    userMail: string;
    
    TeachersNantesName : string[] = [];
    TeachersBrestName : string[] = [];

    indisponibility: Indisponibilite;

    tableData : Indisponibilite[] = [];
    
    debut: Date ;
    startAt: Date;
    indisponible: Date ;

    startDateString : string; 
    indisponibilityDateString : string; 

    selectedNumberWeek =0 ;
    selectedNumberModuleUEA  =0;
    selectedNumberModuleUEB  =0;
    selectedNumberModuleUEC  =0;

    modulesUEA: Module[] = [];
    modulesUEB: Module[] = [];
    modulesUEC: Module[] = [];

    items = [
        { label: 1 , temps:" : 8h -> 9h:15min" , isChecked: false },
        { label: 2 , temps:" : 9h:30min -> 10h:45min" , isChecked: false },
        { label: 3, temps:" : 11h -> 12h:15min",isChecked: false },
        { label: 4, temps:" : 13h:45min -> 15h", isChecked: false },
        { label: 5, temps:" : 15h:15min -> 16h:30min",  isChecked: false },
        { label: 6, temps:" :  16h:45min -> 18h", isChecked: false },
    ];
 

    constructor(private  dataService: DataService, private userService: UserService, private snackBar: MatSnackBar) {}

    ngOnInit(): void {
        this.userMail = sessionStorage.getItem("userMail") || "";
        this.userService.getUsers().subscribe(
            (users) => {
                users = users.filter(user => user.role === 'Enseignant');
                users.forEach(user => {
                    if (user.role !== "ResponsableTAF"){
                        if(user.localisation === "Nantes"){
                            this.TeachersNantesName.push(user.mail);
                        } else if(user.localisation === "Brest"){
                            this.TeachersBrestName.push(user.mail);
                        }
                    }
                    
                });
            }
        );
    }

    changeDate() {
        this.startDateString = formatDate(this.debut, 'yyyy-MM-dd', 'en-US');
        this.startAt = new Date(this.debut);
        this.startAt.setDate(this.startAt.getDate() + 1);
    }

    addEvent() {
        this.indisponibilityDateString = formatDate(this.indisponible, 'yyyy-MM-dd', 'en-US');
    }

    selectModuleUEAChange(){
        this.modulesUEA = [];
        for(let i=1; i <= this.selectedNumberModuleUEA ; i++){
            this.modulesUEA.push({
                name: "",
                slotsNumber: 0,
                mails: {
                    Nantes: "",
                    Brest: ""
                },
                isSync: false
            })
        }
    }

    selectModuleUEBChange(){
        this.modulesUEB = [];
        for(let i=1; i <= this.selectedNumberModuleUEB ; i++){
            this.modulesUEB.push({
                name: "",
                slotsNumber: 0,
                mails: {
                    Nantes: "",
                    Brest: ""
                },
                isSync: false
            })
        }
    }

    selectModuleUECChange(){
        this.modulesUEC = [];
        for(let i=1; i <= this.selectedNumberModuleUEC ; i++){
            this.modulesUEC.push({
                name: "",
                slotsNumber: 0,
                mails: {
                    Nantes: "",
                    Brest: ""
                },
                isSync: false
            })
        }
    }

    addFunction(){
        const selectedCreneaux = [];
        for(const item of this.items){
            if(item.isChecked){
                selectedCreneaux.push(item.label)
            }
        }
        this.indisponibility= {
            date: this.indisponibilityDateString,
            slots : selectedCreneaux
        }
        this.tableData.push(this.indisponibility);
    }

    deleteRow(row: Indisponibilite) {
        const index = this.tableData.indexOf(row);
        this.tableData.splice(index, 1);
    }

    clickFunction() {
        const data : Data = {
            weeksNumber : this.selectedNumberWeek,
            startDate: this.startDateString,
            modulesUeA : this.modulesUEA,
            modulesUeB : this.modulesUEB,
            modulesUeC : this.modulesUEC,
            unavailabilities : {
                Brest: this.tableData,
                Nantes: this.tableData
            }
        };

        this.dataService.addData(data).subscribe({
            next: () => {
                this.snackBar.open('Bien envoyé!', 'Close', {
                    duration: 3000,
                    verticalPosition: 'top',
                    horizontalPosition: 'center'
                });
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
