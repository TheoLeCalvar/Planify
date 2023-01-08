import { Component, OnInit  } from '@angular/core';
import { Data } from '../Models/Data';
import { Module } from '../Models/Module';
import { DataService } from '../Services/data.service';



@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css']
})
export class RtafFormComponent implements OnInit {
    selectedNumberWeek: number =0 ;
    selectedNumberModuleUEA : number =0;
    selectedNumberModuleUEB : number =0;
    selectedNumberModuleUEC : number =0;

    modulesUEA: Module[] = [];
    modulesUEB: Module[] = [];
    modulesUEC: Module[] = [];

    unavailable: string []; 

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
                id: i,
                name: "",
                nbCreneau: 0
            })
        }
       
    }

    selectModuleUEBChange(){
        console.log(this.selectedNumberModuleUEB);
        this.modulesUEB = [];
        for(let i=1; i <= this.selectedNumberModuleUEB ; i++){
            this.modulesUEB.push({
                id: i,
                name: "",
                nbCreneau: 0
            })
        }
       
    }

    selectModuleUECChange(){
        console.log(this.selectedNumberModuleUEC);
        this.modulesUEC = [];
        for(let i=1; i <= this.selectedNumberModuleUEC ; i++){
            this.modulesUEC.push({
                id: i,
                name: "",
                nbCreneau: 0
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
            unavailable: this.unavailable
        }

        this.service.addData(data).subscribe(
            dataForm => {
              console.log(dataForm)
            },
            erreur =>{
              console.log(erreur)
            }
          )  

    }

}
