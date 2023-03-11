import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Indisponibilite } from '../Models/Indisponibilite';
import { User } from '../Models/User';
import { DataService } from '../Services/data.service';
import { UserService } from '../Services/user.service';

@Component({
  selector: 'app-enseignant-form',
  templateUrl: './enseignant-form.component.html',
  styleUrls: ['./enseignant-form.component.css']
})
export class EnseignantFormComponent implements OnInit {

    userMail: string;

    etalement: number;

    indisponible: Date ;
    indisponibilityDateString:string;

    items = [
        { label: 1 , temps:" : 8h -> 9h:15min" , isChecked: false },
        { label: 2 , temps:" : 9h:30min -> 10h:45min" , isChecked: false },
        { label: 3, temps:" : 11h -> 12h:15min",isChecked: false },
        { label: 4, temps:" : 13h:45min -> 15h", isChecked: false },
        { label: 5, temps:" : 15h:15min -> 16h:30min",  isChecked: false },
        { label: 6, temps:" :  16h:45min -> 18h", isChecked: false },
    ];

    indisponibility: Indisponibilite;

    tableData : Indisponibilite[] = [];

    constructor(private userService: UserService, private  dataService: DataService) {}

    ngOnInit(): void {
        this.userMail = sessionStorage.getItem("userMail") || "";
    }

    addEvent() {
        this.indisponibilityDateString = formatDate(this.indisponible, 'yyyy-MM-dd', 'en-US');
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
        const userPreferences : User = {
            mail: this.userMail,
            unavailabilities : this.tableData,
            spreadWeeks:this.etalement
        }

        this.dataService.addPreferences(userPreferences).subscribe(
            () => {
              console.log("OK")
            },
            (erreur) =>{
              console.log(erreur)
            }
        ) 
    }

}
