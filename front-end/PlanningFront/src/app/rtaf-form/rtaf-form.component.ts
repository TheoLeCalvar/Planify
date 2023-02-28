import { formatDate } from '@angular/common';
import { Component, OnInit,ViewEncapsulation  } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Data } from '../Models/Data';
import { Indisponibilite } from '../Models/Indisponibilite';
import { Module } from '../Models/Module';
import { User } from '../Models/User';
import { DataService } from '../Services/data.service';
import { UserService } from '../Services/user.service';




@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RtafFormComponent implements OnInit {

    userEmail: string;
    
   TeachersNantesName : string[] = [];
   TeachersBrestName : string[] = [];
    


    indisponibility: Indisponibilite;

    tableData : Indisponibilite[] = [];


    
    debut: Date ;
    startAt: Date;
    indisponible: Date ;

    startDateString : string; 
    indisponibilityDateString : string; 
    

    selectedNumberWeek: number =0 ;
    selectedNumberModuleUEA : number =0;
    selectedNumberModuleUEB : number =0;
    selectedNumberModuleUEC : number =0;


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
 

    constructor(private  dataService: DataService, private userService: UserService) { 
        this.userEmail = this.userService.getUserEmail();
    }

    ngOnInit(): void {
        //get user list 
        this.userService.getUsers().subscribe(
            (data) => {
            console.log(data)
            //populate TeachersNantesName and TeachersBrestName lists
            data.forEach(e => {
                console.log("user", e);
                if(e.localisation === "Nantes"){
                    this.TeachersNantesName.push(e.mail);
                }
                else if(e.localisation === "Brest"){
                    this.TeachersBrestName.push(e.mail);
                }

            });
        });
        
    }

    changeDate(type: string, event: MatDatepickerInputEvent<Date>) {

        this.startDateString = formatDate(this.debut, 'yyyy-MM-dd', 'en-US');
        this.startAt = new Date(this.debut);
        this.startAt.setDate(this.startAt.getDate() + 1);

        console.log(this.startDateString);
    }

    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {

        this.indisponibilityDateString = formatDate(this.indisponible, 'yyyy-MM-dd', 'en-US');

        console.log(this.indisponibilityDateString);

    }


    selectWeekChange() {
        console.log(this.selectedNumberWeek);
    }
    selectModuleUEAChange(){
        console.log(this.selectedNumberModuleUEA);
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
        console.log(this.selectedNumberModuleUEB);
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
        console.log(this.selectedNumberModuleUEC);
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
        console.log("number of week :",this.selectedNumberWeek);
        console.log("start date: ", this.startDateString);
        console.log("modules UEA",this.modulesUEA);
        console.log("modules UEB",this.modulesUEB);
        console.log("modules UEC",this.modulesUEC);
        console.log("les indisponibilités",this.tableData);

        let data : Data = {
            weeksNumber : this.selectedNumberWeek,
            startDate: this.startDateString,
            modulesUeA : this.modulesUEA,
            modulesUeB : this.modulesUEB,
            modulesUeC : this.modulesUEC,
            unavailabilities : {
                Brest: this.tableData,
                Nantes: this.tableData
            }
        }

        console.log(data);

        this.dataService.addData(data).subscribe(
            (dataForm: any) => {
              console.log("OK")
            },
            erreur =>{
              console.log(erreur)
            }
        ) 

    }

}
