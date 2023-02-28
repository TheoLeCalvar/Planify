import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
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

    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {

        this.indisponibilityDateString = formatDate(this.indisponible, 'yyyy-MM-dd', 'en-US');

        console.log(this.indisponibilityDateString);

    }

    addFunction(){
        console.log("date indisponible: ", this.indisponibilityDateString);
        let selectedCreneaux = [];
        for(let item of this.items){
            if(item.isChecked){
                selectedCreneaux.push(item.label)
            }
        }
        console.log("creneaux: ", selectedCreneaux);
        this.indisponibility= {
            date: this.indisponibilityDateString,
            slots : selectedCreneaux
        }
        console.log(this.indisponibility);


        this.tableData.push(this.indisponibility);
        console.log(this.tableData)
        
    }
    deleteRow(row: any) {
        const index = this.tableData.indexOf(row);
        this.tableData.splice(index, 1);
        console.log("apres suppression");
        console.log(this.tableData);
    }

    clickFunction() {
        console.log("user mail :", this.userMail);
        console.log("etalement du cours: ", this.etalement);
        console.log("les indisponibilités",this.tableData);

        let userPreferences : User = {
            mail: this.userMail,
            unavailabilities : this.tableData,
            spreadWeeks:this.etalement
        }

        console.log(userPreferences);

        this.dataService.addPreferences(userPreferences).subscribe(
            (dataForm: any) => {
              console.log("OK")
            },
            (erreur) =>{
              console.log(erreur)
            }
        ) 

    }

}
