package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.variables.IntVar;

import com.solver.service.SolverService;

public class Modelisation {

	private Model model;
	private Solver solver;
	private IntVar[] planning;
	private IntVar[][] agendajour;
	private IntVar[] Nb_Seances;
	private IntVar[] nb0parjour;
	private Donnee donnee;

	public Modelisation(ArrayList<Module> UE_A, ArrayList<Module> UE_B, ArrayList<Module> UE_C, int Nb_Semaines, ArrayList<Integer> Dispo) {
		this.donnee = new Donnee(UE_A, UE_B, UE_C, Nb_Semaines, Dispo);
	}

	public void BuildModel() {
		model = new Model();
		solver = model.getSolver();
		planning = model.intVarArray("planning", donnee.getCalendrier().getNb_Creneaux(), 0,
		donnee.Nb_cour_different()); // Liste de tout les créneaux, contenant, pour chaque créneaux, le cours affecté.
		agendajour = model.intVarMatrix("agenda-Jour", donnee.getCalendrier().getNb_Jours(), 6, 0,
		donnee.Nb_cour_different()); // Matrice contenant le nombre de jour, et pour chaque jour, 6 créneaux, qui contienne le cours affecté.
		Nb_Seances = model.intVarArray("Nb_seances", donnee.Nb_cour_different(), 0,
		donnee.getCalendrier().getNb_Creneaux()); // Liste contenant le nombre de séance part cours différents (Nb_seances[O] contient le nombre de cours vide
		nb0parjour = model.intVarArray("Nombre de 0 par jour", donnee.getCalendrier().getNb_Jours(), 0, 6); // Liste contenant le nombre de cours vide à attribuer par jours (pour equilibrer)
	}

	public void Contrainte_nbcours() { // Contrainte permettant de remplir toute les variables avec le bon nombre de cours
		// On pose la contrainte sur la variable Nb_Seances
		model.arithm(Nb_Seances[0], "=", donnee.Nb_0()).post();
		for (Module j:donnee.getListe_Module()) {
				model.arithm(Nb_Seances[j.getNumero_module()], "=", j.getNb_creneaux()).post();
			}
		// On rempli la variable planning en fonction de la variable Nb_Seances
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			model.count(i, planning, Nb_Seances[i]).post();
		}
		// On rempli la variable agendajour en fonction de la variable planning
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				model.arithm(agendajour[i][j], "=", planning[6 * i + j]).post();
			}
		}
	}

	public void Contrainte_Equilibrage0() { // Contrainte permettant d'Équilibrer le nombre de 0 par jours
		// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			model.arithm(nb0parjour[i], ">=", donnee.Nb_0Jour()).post();
		}
		/*
		 * for (int i=0; i<donnee.getCalendrier().getNb_Jours(); i++) {
		 * model.arithm(nb0parjour[i], "<=", donnee.Nb_0Jour()+1).post(); }
		 * 
		 */

		// On place le bon nombre de séances vides dans la variable agendaJour
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			model.count(0, agendajour[i], nb0parjour[i]).post();
		}
		// On s'assure que la somme de séances vides est bien égale à Nb_0
		model.sum(nb0parjour, "=", donnee.Nb_0()).post();

	}

	public void Contrainte_mercredi_soir0() { // Contrainte permettant de forcer le créneau du mercredi soir à 0
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours() / 2; i++) {
			model.arithm(agendajour[2 * i + 1][5], "=", 0).post();
		}
	}

	public void Contraintes_Automate1() { // pas de trous dans une journée + même créneaux de cours collés
		// Création des expressions régulières pour les contraintes de cours
		StringBuilder regexp0 = new StringBuilder("0*[^0]{0,4}0*");
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

		// On post les contraintes de chaques automates
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
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
	}

	public void Contrainte_Dispo() {
		for (int i = 0; i < donnee.getCalendrier().getNb_Creneaux(); i++) {
			if (!donnee.getCalendrier().Creneaux_dispo(i)) {
				model.arithm(planning[i], "=", 0).post();
			}
		}
	}
	
	public void Contrainte_Dispo_Module() {
		for (int i = 0; i < donnee.getCalendrier().getNb_Creneaux(); i++) {
			for (Module module:donnee.getListe_Module()) {
				if(!module.Creneaux_dispo(i)) {
					model.arithm(planning[i], "!=", module.getNumero_module()).post();
				}
			}
		}
		
	}

	public void addConstraints() {
		Contrainte_Dispo();
		Contrainte_nbcours();
		Contrainte_Equilibrage0();
		Contrainte_mercredi_soir0();
		Contraintes_Automate1();
		Contrainte_Dispo_Module();
	}

	public void solve() {
		solver.findSolution();
	}
	
	public String getSolution() {
		HashMap<Integer, String> num_nom= new HashMap<>();
		for (Module module: donnee.getListe_Module()) {
			num_nom.put(module.getNumero_module(), module.getName());
		}
		System.out.println("ici :"+num_nom);
		
		String res = "";
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			res += Nb_Seances[i] + "\n";
		}

		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			res += nb0parjour[i] + "\n";
		}

		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				res += agendajour[i][j] + "\n";
			}
		}

		res += "\n\n";
		for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
			res += "Jour " + i + ":  ";
		}
		res += "\n";
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < donnee.getCalendrier().getNb_Jours(); i++) {
				res+= agendajour[i][j].getValue()+"        ";
			}
			res += "\n";
		}

		System.out.println(res);

		return res;
	}
	
	public ArrayList<Integer> Traduction(HashMap<Date,ArrayList<Integer>> a, Date debut ){
		ArrayList<LocalDate> d = new ArrayList<LocalDate>();
		for (Date d1 :a.keySet()) {
			d.add(d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
		LocalDate local_debut= debut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ArrayList<Integer> l_int= new ArrayList<Integer>();
		ArrayList<Integer> l_final= new ArrayList<Integer>();
		for(LocalDate date:d) {
			Duration dure = Duration.between(local_debut.atTime(0, 0), date.atTime(0, 0));
			System.out.println(dure);
			Integer i = (int) (long) dure.toDays();
			l_int.add((i/7)*2+(i%7)+1);
			}
		for(int i:l_int) {
			for(ArrayList<Integer> j:a.values()) {
				for(Integer k : j) {
					l_final.add(i*6+k);
				}
			}
		}
		ArrayList<Integer>l_creneau=new ArrayList<Integer>();
		//changer 120 avec le nombre de creneaux total ( utiliser class creneaux )
		for(int l=0;l<120;l++) {
			for(int e : l_final) {
				if(l==e) {
					l_creneau.add(0);
				} else {
					l_creneau.add(1);					}
				}
				
		}
		return l_creneau;
	}
	
		
	public static void main(String[] args) {
		
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
		
		ArrayList<Integer> Dispo2 = new ArrayList<>(Arrays.asList(
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
				 1,1,1,1,1,1,
				 1,1,1,1,1,1));
		ArrayList<Module> modulesUeA = new ArrayList<Module>();
		modulesUeA.add(new Module(1, "1", 7, Dispo));
		modulesUeA.add(new Module(2, "2", 7,Dispo));
		modulesUeA.add(new Module(3, "3", 8,Dispo));
		ArrayList<Module> modulesUeB = new ArrayList<Module>();
		modulesUeB.add(new Module(4, "4", 11,Dispo2));
		modulesUeB.add(new Module(5, "5", 11,Dispo));
		modulesUeB.add(new Module(6, "6", 11,Dispo));
		ArrayList<Module> modulesUeC = new ArrayList<Module>();
		modulesUeC.add(new Module(7, "7", 4,Dispo));
		modulesUeC.add(new Module(8, "8", 4,Dispo));
		modulesUeC.add(new Module(9, "9", 3,Dispo));
		ArrayList<Unavailable> unavailable = new ArrayList<Unavailable>();
		ArrayList<Integer> slots = new ArrayList<>();
		slots.add(1);
		slots.add(2);
		slots.add(3);
		slots.add(4);
		unavailable.add(new Unavailable("2023-02-08", slots));

		Request request = new Request(14, modulesUeA, modulesUeB, modulesUeC, unavailable, "2023-02-08");
		new SolverService().solver(request);
	}
	
}
