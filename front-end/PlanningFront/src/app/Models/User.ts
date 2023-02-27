import { Indisponibilite } from "./Indisponibilite";

export class User {  
    mail: string;
    role?: string;
    unavailable : Indisponibilite[] ;
    localisation?: string;
    spreadWeeks:number;
}