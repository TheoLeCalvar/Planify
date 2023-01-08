package com.solver.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.solver.model.Modelisation;
import com.solver.model.Request;

@Service
public class SolverService {
	
	public void solver(Request request) {
		int Nb_Semaines = request.getWeeksNumber();
		
		ArrayList<Integer> Dispo = new ArrayList<Integer>(Nb_Semaines * 6 * 2);
		for (int i = 0; i < Nb_Semaines * 6 * 2; i++) {
			int x = Math.random() > .15 ? 1 : 0;
			Dispo.add(x);
		}

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
		test.show();
	}
	
}
