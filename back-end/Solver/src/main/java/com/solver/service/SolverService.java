package com.solver.service;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.stereotype.Service;

@Service
public class SolverService {
	
	public void solver() {
		/*
		 * 1. Création du solveur et du modèle associé
		 */
	
			Model model = new Model();
			Solver solver = model.getSolver();
	
		/*
		* 2. Création des données
		*/
		
			// Données relatives aux nombres de cours différents
			int Nb_Module_A = 3;	//donée à remplir
			int Nb_Module_B = 3;	//donée à remplir
			int Nb_Module_C = 3;	//donée à remplir
			int Nb_cours_different = Nb_Module_A + Nb_Module_B + Nb_Module_C+1;
			
			// Données relatives aux nombre de créneaux
			int Nb_Semaines = 14;	//donée à remplir
			int Nb_Creneaux=Nb_Semaines*2*6;
			int Nb_Jours = Nb_Semaines*2;
			
			// Données relatives aux nombres de séances à pourvoir.
			int Nb_Cours_A_1 = 7;	//donée à remplir
			int Nb_Cours_A_2 = 7;	//donée à remplir
			int Nb_Cours_A_3 = 8;	//donée à remplir
			int Nb_Cours_B_1 = 11;	//donée à remplir
			int Nb_Cours_B_2 = 11;	//donée à remplir
			int Nb_Cours_B_3 = 11;	//donée à remplir
			int Nb_Cours_C_1 = 4;	//donée à remplir
			int Nb_Cours_C_2 = 4;	//donée à remplir
			int Nb_Cours_C_3 = 3;	//donée à remplir
			int Nb_Cours_vide = Nb_Creneaux-Nb_Cours_A_1-Nb_Cours_A_2-Nb_Cours_A_3-Nb_Cours_B_1-Nb_Cours_B_2-Nb_Cours_B_3-Nb_Cours_C_1-Nb_Cours_C_2-Nb_Cours_C_3;
			int nb0jour = Nb_Cours_vide/Nb_Jours;
			
			// Création des expressions régulières pour les contraintes de cours : pas de trous dans une journée + même créneaux de cours collés
			StringBuilder regexp0 = new StringBuilder("0*[^0]{0,3}0*");
			StringBuilder regexp1 = new StringBuilder("[^1]*1{0,3}[^1]*");
			StringBuilder regexp2 = new StringBuilder("[^2]*2{0,3}[^2]*");
			StringBuilder regexp3 = new StringBuilder("[^3]*3{0,3}[^3]*");
			StringBuilder regexp4 = new StringBuilder("[^4]*4{0,3}[^4]*");
			StringBuilder regexp5 = new StringBuilder("[^5]*5{0,3}[^5]*");
			StringBuilder regexp6 = new StringBuilder("[^6]*6{0,3}[^6]*");
			StringBuilder regexp7 = new StringBuilder("[^7]*7{0,3}[^7]*");
			StringBuilder regexp8 = new StringBuilder("[^8]*8{0,3}[^8]*");
			StringBuilder regexp9 = new StringBuilder("[^9]*9{0,3}[^9]*");
			
			// Création des automates représentant les expressions régulières
			FiniteAutomaton auto0 = new FiniteAutomaton(regexp0.toString(), 0, 6);
			FiniteAutomaton auto1 = new FiniteAutomaton(regexp1.toString(), 0, 6);
			FiniteAutomaton auto2 = new FiniteAutomaton(regexp2.toString(), 0, 6);
			FiniteAutomaton auto3 = new FiniteAutomaton(regexp3.toString(), 0, 6);
			FiniteAutomaton auto4 = new FiniteAutomaton(regexp4.toString(), 0, 6);
			FiniteAutomaton auto5 = new FiniteAutomaton(regexp5.toString(), 0, 6);
			FiniteAutomaton auto6 = new FiniteAutomaton(regexp6.toString(), 0, 6);
			FiniteAutomaton auto7 = new FiniteAutomaton(regexp7.toString(), 0, 6);
			FiniteAutomaton auto8 = new FiniteAutomaton(regexp8.toString(), 0, 6);
			FiniteAutomaton auto9 = new FiniteAutomaton(regexp9.toString(), 0, 6);

		
		/*
		 * 3. Déclaration des variables
		 */
			
			// Liste de tous les créneaux contenant, pour chaque créneau, le cours affecté.	
			IntVar[] planning = model.intVarArray("planning",Nb_Creneaux, 0 , Nb_cours_different-1);
			
			// Matrice de taille (nombre de jour)x6 dont chaque cellule contient le cours affecté. C'est une représentation sous forme d'agenda de "planning".
			IntVar[][] agendajour = model.intVarMatrix("agenda-Jour",Nb_Jours,6,0, Nb_cours_different-1);
			
			// Liste contenant le nombre de séances par cours différents (Nb_seances[O] contient le nombre de cours vides dans le planning).
			IntVar[] Nb_Seances = model.intVarArray("Nb_seances",Nb_cours_different, 0, Nb_Creneaux);
			
			// Liste contenant le nombre de cours vides à attribuer par jour (pour équilibrer).
			IntVar[] nb0parjour = model.intVarArray("Nombre de 0 par jour",Nb_Jours,0,6); 
			
		/*
		 * 4. Déclaration et pose des contraintes
		 */
		
			// On remplit la variable Nb_Seances avec le bon nombre de cours
			model.arithm(Nb_Seances[0], "=", Nb_Cours_vide).post(); 
			model.arithm(Nb_Seances[1], "=", Nb_Cours_A_1).post(); 
			model.arithm(Nb_Seances[2], "=", Nb_Cours_A_2).post(); 
			model.arithm(Nb_Seances[3], "=", Nb_Cours_A_3).post(); 
			model.arithm(Nb_Seances[4], "=", Nb_Cours_B_1).post(); 
			model.arithm(Nb_Seances[5], "=", Nb_Cours_B_2).post(); 
			model.arithm(Nb_Seances[6], "=", Nb_Cours_B_3).post();
			model.arithm(Nb_Seances[7], "=", Nb_Cours_C_1).post(); 
			model.arithm(Nb_Seances[8], "=", Nb_Cours_C_2).post(); 
			model.arithm(Nb_Seances[9], "=", Nb_Cours_C_3).post(); 
			
			// On force le dernier créneau du mercredi soir à 0 : il ne peut pas y avoir cours.
			for (int i=0; i<Nb_Jours/2; i++) { 
				model.arithm(agendajour[2*i+1][5], "=", 0).post();
			}
			
			// On place le bon nombre de séances de chaque cours dans la variable planning
			for (int i=0; i< Nb_cours_different; i++) { 
				model.count(i, planning, Nb_Seances[i]).post(); 				
			}
			
			// On remplit la variable agendajour qui est une représentation du plannning sous forme de tableau
			for (int i=0; i<Nb_Jours; i++) { 
				for (int j=0; j<6; j++) {
					model.arithm(agendajour[i][j], "=", planning[6*i+j]).post();
				}
			}
			
			// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
			for (int i=0; i<Nb_Jours; i++) { 
				model.arithm(nb0parjour[i], ">=", nb0jour).post();
				}
			for (int i=0; i<Nb_Jours; i++) { 
				model.arithm(nb0parjour[i], "<=", nb0jour+1).post();
				}
			
			// On place le bon nombre de séances vides  dans la variable agendaJour
			for (int i=0; i<Nb_Jours; i++) { 
				model.count(0, agendajour[i], nb0parjour[i]).post(); 				
			}
			model.sum(nb0parjour, "=", Nb_Cours_vide).post(); 
			
	
			for (int i = 0; i <Nb_Jours; i++) {
				model.regular(agendajour[i], auto0).post();
				model.regular(agendajour[i], auto1).post();
				model.regular(agendajour[i], auto2).post();			
				model.regular(agendajour[i], auto3).post();			
				model.regular(agendajour[i], auto4).post();			
				model.regular(agendajour[i], auto5).post();			
				model.regular(agendajour[i], auto6).post();			
				model.regular(agendajour[i], auto7).post();			
				model.regular(agendajour[i], auto8).post();			
				model.regular(agendajour[i], auto9).post();			
			}
		
		// 5. Définition de la stratégie de recherche (facultatif, il en existe une par défaut)
		solver.setSearch(Search.lastConflict(Search.randomSearch(planning, 0)));
		
		// 6. Recherche et affichage d’une solution
		solver.showSolutions();
		solver.findSolution();
		
		// 7. Affichage des statistiques de la recherche
		
		solver.printStatistics();
		for (int i=0; i<Nb_Jours; i++) {
			for (int j=0; j<6; j++) {
				System.out.println(agendajour[i][j]);
			}
		}
		for (int i=0; i<Nb_Jours; i++) {
			System.out.println(nb0parjour[i]);
		}
		for (int i=0; i<Nb_cours_different; i++) {
			System.out.println(Nb_Seances[i]);
		}
		System.out.println(Nb_Cours_vide);
		
		for (int i=0; i<Nb_Creneaux; i++) {
			System.out.println(planning[i]);
		}
	}
	
}
