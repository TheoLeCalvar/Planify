package com.solver.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.solver.model.Modelisation;
import com.solver.model.Module;

@Service
public class SolverService {
	
	public void solver(int Nb_Semaines, int Nb_module_UEA, int Nb_module_UEB, int Nb_module_UEC) {
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
		
		// TODO: Ca marche seulement si Nb_module = 3
		Module Module_A1 = new Module(7, "Module A1", 1);
		Module Module_A2 = new Module(7, "Module A2", 2);
		Module Module_A3 = new Module(8, "Module A3", 3);
		Module Module_B1 = new Module(11, "Module B1", 4);
		Module Module_B2 = new Module(11, "Module B2", 5);
		Module Module_B3 = new Module(11, "Module B3", 6);
		Module Module_C1 = new Module(4, "Module C1", 7);
		Module Module_C2 = new Module(4, "Module C2", 8);
		Module Module_C3 = new Module(3, "Module C3", 9);

		ArrayList<Module> UE_A = new ArrayList<Module>(Nb_module_UEA);
		UE_A.add(Module_A1);
		UE_A.add(Module_A2);
		UE_A.add(Module_A3);

		ArrayList<Module> UE_B = new ArrayList<Module>(Nb_module_UEB);
		UE_B.add(Module_B1);
		UE_B.add(Module_B2);
		UE_B.add(Module_B3);

		ArrayList<Module> UE_C = new ArrayList<Module>(Nb_module_UEC);
		UE_C.add(Module_C1);
		UE_C.add(Module_C2);
		UE_C.add(Module_C3);

		Modelisation test = new Modelisation(UE_A, UE_B, UE_C, Nb_Semaines, Dispo);

		test.BuildModel();
		test.addConstraints();
		test.solve();
		test.show();
	}
	
}
