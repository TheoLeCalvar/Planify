import { Component } from '@angular/core';

interface Module {
    id: number;
    name: string;
    nbCreneau: number
}

@Component({
  selector: 'app-rtaf-form',
  templateUrl: './rtaf-form.component.html',
  styleUrls: ['./rtaf-form.component.css']
})
export class RtafFormComponent {
    selectedNumberWeek: number =0 ;
    selectedNumberModuleUEA : number =0;

    modulesUEA: Module[] = [
        {
            id: 1,
            name: "UEA-1",
            nbCreneau: 5
        },
        {
            id: 2,
            name: "UEA-2",
            nbCreneau: 6
        }
    ];

    selectWeekChange() {
        console.log(this.selectedNumberWeek);
    }
    selectModuleUEAChange(){
        console.log(this.selectedNumberModuleUEA);
       
    }
    log(index: number) {
        console.log(index);
    }

}
