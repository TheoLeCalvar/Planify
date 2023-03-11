import { Indisponibilite } from "./Indisponibilite";
import { Module } from "./Module";

export class Data {
    weeksNumber: number;
    startDate: string;
    modulesUeA: Module[];
    modulesUeB: Module[];
    modulesUeC: Module[];
    unavailabilities : {
        Brest: Indisponibilite[],
        Nantes: Indisponibilite[]
    };
}