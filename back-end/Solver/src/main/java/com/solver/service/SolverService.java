package com.solver.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.solver.model.Donnee;
import com.solver.model.Modelisation;
import com.solver.model.Request;
import com.solver.model.Unavailability;

@Service
public class SolverService {
	
	@Autowired
	protected RestTemplate restTemplate;
	
	public String solver(Request request) {
		int Nb_Semaines = request.getWeeksNumber();
		
		ArrayList<Integer> Dispo = new Donnee(request.getStartDate()).Traduction(request.getUnavailables());
		
		ArrayList<Integer> DispoTest = new Donnee(request.getStartDate()).Traduction(request.getUnavailables());
		

//		for(com.solver.model.Module module : request.getModulesUeA()) {
//			module.setDispo(DispoTest);
//		}
//		for(com.solver.model.Module module : request.getModulesUeB()) {
//			module.setDispo(DispoTest);
//		}
//		for(com.solver.model.Module module : request.getModulesUeC()) {
//			module.setDispo(DispoTest);
//		}

		System.out.println(Dispo);
		int Result = 0;
		for (int i : Dispo) {
			if (i == 0) {
				Result = Result + 1;
			}
		}
		System.out.println(Result);

		Modelisation test = new Modelisation(request.getModulesUeA(), request.getModulesUeB(), request.getModulesUeC(), Nb_Semaines, Dispo,Dispo,request.getStartDate());

		test.BuildModel();
		test.addConstraints();
		test.solve();
		return test.getSolutionN();
	}
	
}
