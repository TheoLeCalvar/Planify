package com.solver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.Duration;


import org.springframework.stereotype.Service;

import com.solver.model.Modelisation;
import com.solver.model.Request;

@Service
public class SolverService {
	
	public ArrayList<Integer> Traduction(ArrayList<Date> date_indispo,ArrayList<Integer> creneau_indispo) {
		ArrayList<LocalDate> d = new ArrayList<LocalDate>();
		for(Date d1 :date_indispo) {
		d.add(d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());}
		LocalDate debut= LocalDate.of(2022, 11, 8);
		ArrayList<Integer> l_int= new ArrayList<Integer>();
		ArrayList<Integer> l_final= new ArrayList<Integer>();
		for(LocalDate date:d) {
			Duration dure = Duration.between(debut.atTime(0, 0), date.atTime(0, 0));
			
			System.out.println(dure);
			Integer i = (int) (long) dure.toDays();
			l_int.add((i/7)*2+(i%7)+1);
		}
		for(int i:l_int) {
			for(Integer j:creneau_indispo) {
				l_final.add(i*6+j);
			}
		}
		ArrayList<Integer>l_creneau=new ArrayList<Integer>();
		//changer 120 avec le nombre de creneaux total ( utiliser class creneaux )
		for(int i=0;i<120;i++) {
			for(int e : l_final) {
				if(i==e) {
					l_creneau.add(0);
				}
				else {l_creneau.add(1);
			}
		}}
		return l_creneau;
	}
	
	public String solver(Request request) {
		int Nb_Semaines = request.getWeeksNumber();
		
			
		
		ArrayList<Integer> Dispo = new ArrayList<>(Arrays.asList(
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 0,0,0,0,0,0,
				 0,0,0,0,0,0,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1,
				 1,1,1,1,1,1)); 
		 
		 /*
		 ArrayList<Integer> Dispo = new ArrayList<Integer>(Nb_Semaines*6*2);
		 for (int i = 0 ; i<Nb_Semaines*6*2;i++) {
			 int x = Math.random()>.15?1:0; 
			 Dispo.add(x);
		 }
		  * 
		  */

		System.out.println(Dispo);
		int Result = 0;
		for (int i : Dispo) {
			if (i == 0) {
				Result = Result + 1;
			}
		}
		System.out.println(Result);

		Modelisation test = new Modelisation(request.getModulesUeA(), request.getModulesUeB(), request.getModulesUeC(), Nb_Semaines, Dispo);

		test.BuildModel();
		test.addConstraints();
		test.solve();
		return test.getSolution();
	}
	
}
