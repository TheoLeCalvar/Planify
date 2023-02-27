import { Indisponibilite } from "./Indisponibilite";

export class User {  
    mail: string;
    role?: string;
    unavailabilities : Indisponibilite[] ;
    localisation?: string;
    spreadWeeks:number;
}