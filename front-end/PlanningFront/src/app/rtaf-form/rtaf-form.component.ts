import { Component, OnInit,ViewEncapsulation  } from '@angular/core';
import { MatCalendarCellClassFunction, MatDatepickerInputEvent } from '@angular/material/datepicker';
import { Data } from '../Models/Data';
import { Indisponibilite } from '../Models/Indisponibilite';
import { Module } from '../Models/Module';
import { DataService } from '../Services/data.service';



@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RtafFormComponent implements OnInit {


    indisponibility: Indisponibilite;

    tableData : Indisponibilite[] = [];
    input1Value: string;
    input2Value: string;
    input3Value: string;

    debut: Date = new Date();
    startDate : string; 
    startAt: Date;
    
    indisponible: Date = new Date();
    indisponibilityDate : string; 
    

    selectedNumberWeek: number =0 ;
    selectedNumberModuleUEA : number =0;
    selectedNumberModuleUEB : number =0;
    selectedNumberModuleUEC : number =0;

    modulesUEA: Module[] = [];
    modulesUEB: Module[] = [];
    modulesUEC: Module[] = [];

    items = [
        { label: 1, isChecked: false },
        { label: 2, isChecked: false },
        { label: 3, isChecked: false },
        { label: 4, isChecked: false },
        { label: 5, isChecked: false },
        { label: 6, isChecked: false },
    ];
 

    countId: number = 1;
 

    constructor(private  service: DataService) { }

    ngOnInit(): void {
        let month = this.debut.getMonth() ; 
        let year = this.debut.getUTCFullYear();
        let day = this.debut.getDay();
        this.startAt = new Date(this.debut);
        //console.log(this.startAt);
    }

    changeDate(type: string, event: MatDatepickerInputEvent<Date>) {
        this.startDate = new Date(this.debut.getTime() - this.debut.getTimezoneOffset() * 60000).toISOString().slice(0, 10);
        console.log("debut: ",this.debut);
        console.log("startDate: ",this.startDate);
        let month = this.debut.getMonth() ; 
        let year = this.debut.getUTCFullYear();
        let day = this.debut.getDay();
        this.startAt = new Date(this.debut);
        console.log(this.startAt);
    }

    addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.indisponibilityDate = new Date(this.indisponible.getTime() - this.indisponible.getTimezoneOffset() * 60000).toISOString().slice(0, 10);
        console.log("indisponible: ",this.indisponible);
        console.log("indisponibilityDate: ",this.indisponibilityDate);
    }


    selectWeekChange() {
        console.log(this.selectedNumberWeek);
    }
    selectModuleUEAChange(){
        console.log(this.selectedNumberModuleUEA);
        this.modulesUEA = [];
        for(let i=1; i <= this.selectedNumberModuleUEA ; i++){
            this.modulesUEA.push({
                id: this.countId++,
                name: "",
                slotsNumber: 0
            })
        }
       
    }

    selectModuleUEBChange(){
        console.log(this.selectedNumberModuleUEB);
        this.modulesUEB = [];
        for(let i=1; i <= this.selectedNumberModuleUEB ; i++){
            this.modulesUEB.push({
                id: this.countId++,
                name: "",
                slotsNumber: 0
            })
        }
       
    }

    selectModuleUECChange(){
        console.log(this.selectedNumberModuleUEC);
        this.modulesUEC = [];
        for(let i=1; i <= this.selectedNumberModuleUEC ; i++){
            this.modulesUEC.push({
                id: this.countId++,
                name: "",
                slotsNumber: 0
            })
        }
    }

    addFunction(){
        console.log("date indisponible: ", this.indisponibilityDate);
        let selectedCreneaux = [];
        for(let item of this.items){
            if(item.isChecked){
                selectedCreneaux.push(item.label)
            }
        }
        console.log("creneaux: ", selectedCreneaux);
        this.indisponibility= {
            date: this.indisponibilityDate,
            creneaux : selectedCreneaux
        }
        console.log(this.indisponibility);


        this.tableData.push(this.indisponibility);
        console.log(this.tableData)
        
    }

    clickFunction() {
        console.log("number of week :",this.selectedNumberWeek);
        console.log("modules UEA",this.modulesUEA);
        console.log("modules UEB",this.modulesUEB);
        console.log("modules UEC",this.modulesUEC);

        let data : Data = {
            weeksNumber : this.selectedNumberWeek,
            startDate: this.startDate,
            modulesUeA : this.modulesUEA,
            modulesUeB : this.modulesUEB,
            modulesUeC : this.modulesUEC,
            unavailable: []
        }

        this.service.addData(data).subscribe(
            (dataForm: any) => {
              console.log(dataForm.reponse.replaceAll("   ", "\n"))
            },
            erreur =>{
              console.log(erreur)
            }
          )  

    }

}
