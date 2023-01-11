import { Component, OnInit,ViewEncapsulation  } from '@angular/core';
import { MatCalendarCellClassFunction } from '@angular/material/datepicker';
import { Data } from '../Models/Data';
import { Module } from '../Models/Module';
import { DataService } from '../Services/data.service';



@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RtafFormComponent implements OnInit {

    daysSelected: string[] = [];
    event: any;
    isSelected = (event: any) => {
        const date =
        event.getFullYear() +
        "-" +
        ("00" + (event.getMonth() + 1)).slice(-2) +
        "-" +
        ("00" + event.getDate()).slice(-2);
        return this.daysSelected.find(x => x == date) ? "selected" : null;
    };

    select(event: any, calendar: any) {
        const date =
        event.getFullYear() +
        "-" +
        ("00" + (event.getMonth() + 1)).slice(-2) +
        "-" +
        ("00" + event.getDate()).slice(-2);
        const index = this.daysSelected.findIndex(x => x == date);
        if (index < 0) this.daysSelected.push(date);
        else this.daysSelected.splice(index, 1);

        calendar.updateTodaysDate();
        console.log("days seleted",this.daysSelected);
    }

    selectedNumberWeek: number =0 ;
    selectedNumberModuleUEA : number =0;
    selectedNumberModuleUEB : number =0;
    selectedNumberModuleUEC : number =0;

    modulesUEA: Module[] = [];
    modulesUEB: Module[] = [];
    modulesUEC: Module[] = [];

    countId: number = 1;
 

    constructor(private  service: DataService) { }

    ngOnInit(): void {}

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

    clickFunction() {
        console.log("number of week :",this.selectedNumberWeek);
        console.log("modules UEA",this.modulesUEA);
        console.log("modules UEB",this.modulesUEB);
        console.log("modules UEC",this.modulesUEC);

        let data : Data = {
            weeksNumber : this.selectedNumberWeek,
            modulesUeA : this.modulesUEA,
            modulesUeB : this.modulesUEB,
            modulesUeC : this.modulesUEC,
            unavailable: this.daysSelected
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
