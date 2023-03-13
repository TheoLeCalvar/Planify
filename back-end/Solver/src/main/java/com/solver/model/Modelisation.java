package com.solver.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

import com.solver.util.Localisation;
import com.solver.util.Role;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

public class Modelisation {

	private Model model;
	private Solver solver;
	
	/*
	 * Création des variables de décision
	 */
	private IntVar[] planning;
	private IntVar[][] agendajour;
	private IntVar[] nb_Seances;
	private IntVar[] nb0parjour;
	private IntVar[] planningB;
	private IntVar[][] agendajourB;
	private IntVar[] nb_SeancesB;
	private IntVar[] nb0parjourB;
	private Donnee donnee;
	private HashMap<HashMap<Integer, Integer>, HashMap<Integer, Integer>> contraintes_keysort;
	private Map<String, User> userList;
	
	
	/*
	 * Variables créées pour KeySort; non utilisées dans la version finale
	private IntVar[] ss;
	private IntVar[] sp;
	private IntVar[] p;
	private IntVar[] s;
	 */

	public Modelisation(Donnee donnee, Map<String, User> userList) {
		this.donnee = donnee;
		this.userList = userList;
	}
	
	public void BuildModel() {

		model = new Model();
		solver = model.getSolver();
		
		/*
		 * 2 types de variables : 
		 * Celles pour Nantes ne sont pas indicées; celles pour Brest portent le même nom et finissent par "B"
		 */
		
		planning = model.intVarArray("planning_Nantes", donnee.getCalendrierN().getNb_Creneaux(), 0, donnee.Nb_cour_different()); // Liste de tous les créneaux, contenant, pour chaque créneau, le cours affecté.
		agendajour = model.intVarMatrix("agenda-Jour_Nantes", donnee.getCalendrierN().getNb_Jours(), 6, 0, donnee.Nb_cour_different()); // Vue matricielle du "planning" contenant le nombre de jours (colonnes), et pour chaque jour, 6 créneaux, qui contiennent les cours affectés.
		nb_Seances = model.intVarArray("nb_Seances_Nantes", donnee.Nb_cour_different(), 0, donnee.getCalendrierN().getNb_Creneaux()); // Liste contenant le nombre de séances par cours différent (nb_Seances[O] contient le nombre de créneaux vides.
		nb0parjour = model.intVarArray("Nombre de 0 par jour_Nantes", donnee.getCalendrierN().getNb_Jours(), 0, 6); // Liste contenant le nombre de cours vides à attribuer par jour (pour équilibrer).
		
		planningB = model.intVarArray("planning_Brest", donnee.getCalendrierB().getNb_Creneaux(), 0, donnee.Nb_cour_different()); 
		agendajourB = model.intVarMatrix("agenda-Jour_Brest", donnee.getCalendrierB().getNb_Jours(), 6, 0, donnee.Nb_cour_different()); 
		nb_SeancesB = model.intVarArray("nb_Seances_Brest", donnee.Nb_cour_different(), 0, donnee.getCalendrierB().getNb_Creneaux()); 
		nb0parjourB = model.intVarArray("Nombre de 0 par jour_Brest", donnee.getCalendrierB().getNb_Jours(), 0, 6); 
		

	}
	/*
	 * Contrainte permettant de remplir toutes les variables avec le bon nombre de cours/
	 */

	public void Contrainte_nbcoursN() { 
		// On pose la contrainte sur la variable nb_Seances
		model.arithm(nb_Seances[0], "=", donnee.Nb_0N()).post();
		
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(nb_Seances[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
		}
		// On remplit la variable planning en fonction de la variable nb_Seances
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			model.count(i, planning, nb_Seances[i]).post();
		}
		// On remplit la variable agendajour en fonction de la variable planning
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				model.arithm(agendajour[i][j], "=", planning[6 * i + j]).post();
			}
		}
	}
	
	public void Contrainte_nbcoursB() { 
		// On pose la contrainte sur la variable nb_Seances
		model.arithm(nb_SeancesB[0], "=", donnee.Nb_0B()).post();
		
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(nb_SeancesB[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
		}
		// On remplit la variable planning en fonction de la variable nb_Seances
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			model.count(i, planningB, nb_SeancesB[i]).post();
		}
		// On remplit la variable agendajour en fonction de la variable planning
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				model.arithm(agendajourB[i][j], "=", planningB[6 * i + j]).post();
			}
		}
	}
	
	/*
	 * Contrainte permettant d'équilibrer le nombre de 0 par jour
	 */

	public void Contrainte_Equilibrage0N() { 
		// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			model.arithm(nb0parjour[i], ">=", donnee.Nb_0JourN()).post();
		}

		// On place le bon nombre de séances vides dans la variable agendaJour
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			model.count(0, agendajour[i], nb0parjour[i]).post();
		}
		// On s'assure que la somme de séances vides est bien égale à Nb_0
		model.sum(nb0parjour, "=", donnee.Nb_0N()).post();

	}
	
	public void Contrainte_Equilibrage0B() { 
		// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			model.arithm(nb0parjourB[i], ">=", donnee.Nb_0JourB()).post();
		}

		// On place le bon nombre de séances vides dans la variable agendaJour
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			model.count(0, agendajourB[i], nb0parjourB[i]).post();
		}
		// On s'assure que la somme de séances vides est bien égale à Nb_0
		model.sum(nb0parjourB, "=", donnee.Nb_0B()).post();

	}
	
	/*
	 * Contrainte permettant de forcer le créneau du mercredi soir à 0
	 */

	public void Contrainte_mercredi_soir0N() { 
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours() / 2; i++) {
			model.arithm(agendajour[2 * i + 1][5], "=", 0).post();
		}
	}
	public void Contrainte_mercredi_soir0B() { 
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours() / 2; i++) {
			model.arithm(agendajourB[2 * i + 1][5], "=", 0).post();
		}
	}

	/*
	 * Contrainte "expression régulière" qui permet de grouper les mêmes cours au sein d'une même journée.
	 */

	public void Contrainte_groupement_cours() {
		
		ArrayList<StringBuilder> l= new ArrayList<StringBuilder>();
		StringBuilder expression0 = new StringBuilder (); // Initialisation de l'expression régulière
		String Cours_0 = new String ("0*[^0]{0,4}0*"); // Création de l'expression régulière pour le cours vide : 0 est uniquement en début et en fin de journée.
		expression0.append(Cours_0);
		l.add(expression0);
		ArrayList<FiniteAutomaton> l_auto = new ArrayList<FiniteAutomaton>(); // Création de l'automate
		for (int i=1;i<donnee.Nb_cour_different();i++) { // Création des expressions régulières pour chacun des cours
			StringBuilder expression = new StringBuilder ();
			String automat = new String ("[^"+i+"]*"+i+"{0,3}[^"+i+"]*"); // Au sein d'une même journée, les mêmes cours "i" sont regroupés entre eux.
			expression.append(automat);
			l.add(expression);
		}
		
		for(int i=0;i<l.size();i++) {
			l_auto.add(new FiniteAutomaton((l.get(i)).toString(),0,6)); // Ajout des expressions régulières à l'automate
		}
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) { // Ajout des contraintes "automate" au modèle
			for(int j=0;j<l.size();j++) {
				model.regular(agendajour[i],l_auto.get(j)).post(); 
			}
		}
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			for(int j=0;j<l.size();j++) {
				model.regular(agendajourB[i],l_auto.get(j)).post();
			}
		}
	}
	
	/*
	 * Contrainte permettant qu'un cours soit mené sur un nombre maximum de séances
	 * Création de nouvreaux automates qui s'appliquent à Brest ou Nantes
	 */
	
	public void Contrainte_etalement() {
			
			ArrayList<StringBuilder> lN= new ArrayList<StringBuilder>();
			ArrayList<StringBuilder> lB= new ArrayList<StringBuilder>();
	
			ArrayList<FiniteAutomaton> l_autoN = new ArrayList<FiniteAutomaton>(); //Création de l'automate pour Nantes
			ArrayList<FiniteAutomaton> l_autoB = new ArrayList<FiniteAutomaton>(); //Création des l'automate pour Nantes
	
			for (int i = 1; i < donnee.getListe_Module().size(); i++) {
				String mailBrest = donnee.getListe_Module().get(i).getMails().get(Localisation.Brest);
				String mailNantes = donnee.getListe_Module().get(i).getMails().get(Localisation.Nantes);
				
				StringBuilder expressionN = new StringBuilder (); // Initialisation de l'expression régulière de Nantes
				String automatN = new String ("[^"+i+"]*"+i+"{1}.{0,"+(userList.get(mailNantes).getSpreadWeeks()*12-1)+"}[^"+i+"]*"); //création des expressions régulières liées à Nantes
				expressionN.append(automatN);
				lN.add(expressionN); //Ajout des expressions régulières à l'automate
				
				StringBuilder expressionB = new StringBuilder (); // Initialisation de l'expression régulière de Brest
				String automatB = new String ("[^"+i+"]*"+i+"{1}.{0,"+(userList.get(mailBrest).getSpreadWeeks()*12-1)+"}[^"+i+"]*"); //création des expressions régulières liées à Nantes
				expressionB.append(automatB);
				lB.add(expressionB); //Ajout des expressions régulières à l'automate
			}
			
			/*
			 * Ajout des automates au modèle
			 */
			for(int i=0;i<lN.size();i++) {
					l_autoN.add(new FiniteAutomaton((lN.get(i)).toString(),donnee.getCalendrierN().getNb_Creneaux(),donnee.getCalendrierN().getNb_Creneaux()));
		
				}
			for(int i=0;i<lB.size();i++) {
				l_autoB.add(new FiniteAutomaton((lB.get(i)).toString(),donnee.getCalendrierN().getNb_Creneaux(),donnee.getCalendrierN().getNb_Creneaux()));
		
				}
			lN = new ArrayList<StringBuilder>();
			lB = new ArrayList<StringBuilder>();
	
				for(int j=0;j<l_autoN.size();j++) {
					model.regular(planning,l_autoN.get(j)).post();
			}
				for(int j=0;j<l_autoB.size();j++) {
					model.regular(planningB,l_autoB.get(j)).post();
			}
	}
	
	/*
	 * Contraintes de disponibilités du calendrier général pour Brest et Nantes.
	 */
	public void Contrainte_DispoN() {
		for (int i = 0; i < donnee.getCalendrierN().getNb_Creneaux(); i++) {
			if (!donnee.getCalendrierN().Creneaux_dispo(i)) {
				model.arithm(planning[i], "=", 0).post();
			}
		}
	}
	
	public void Contrainte_DispoB() {
		for (int i = 0; i < donnee.getCalendrierB().getNb_Creneaux(); i++) {
			if (!donnee.getCalendrierB().Creneaux_dispo(i)) {
				model.arithm(planningB[i], "=", 0).post();
			}
		}
	}
	
	/*
	 *  KeySort : demande trop de temps de calcul
	 * 
	 * 
	 * 

	public void test_key() {
		HashMap<HashMap<Integer, Integer>, HashMap<Integer, Integer>> contraintes = this.contraintes_keysort ;
	
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> l1 = new ArrayList<Integer>();
	
		for (int i=0; i<donnee.getCalendrierN().getNb_Creneaux();i++) {
		    l.add(i);
		}
		for(int i=0;i<donnee.Nb_0B();i++) {
			l1.add(0);
		}
		for (int i=1; i<donnee.Nb_cour_different();i++) {
		    for(int j=0;j<donnee.getListe_Module().get(i).getSlotsNumber();j++) {
		    	l1.add(j);
		    }
		    
		}
		

		IntVar[] starts = IntStream.range(0, donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> model.intVar( "S_"+ i, l.get(i))).toArray(IntVar[]::new);
		IntVar[] planning = this.planning;
		IntVar[] sortedplanning = model.intVarArray("SP", donnee.getCalendrierN().getNb_Creneaux(), 0,donnee.Nb_cour_different());
		IntVar[] sortedstarts = model.intVarArray("SS", donnee.getCalendrierN().getNb_Creneaux(), 0,donnee.getCalendrierN().getNb_Creneaux()-1);		
		model.keySort(
		        IntStream.range(0,donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> new IntVar[]{planning[i],starts[i]}).toArray(IntVar[][]::new),
		        //permutation,
		        null,
		        IntStream.range(0,donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> new IntVar[]{sortedplanning[i], sortedstarts[i]}).toArray(IntVar[][]::new),
		        2
		        ).post();

		
		ArrayList<Integer> elgauche=new ArrayList<Integer>();
		ArrayList<Integer> eldroite=new ArrayList<Integer>();

		
		for(HashMap<Integer,Integer> i : contraintes.keySet()) {
			for(Integer elgauche1 : i.keySet()) {
				elgauche.add(this.Nb_Seance(elgauche1)+i.get(elgauche1)-1);
			}
		}
		for(HashMap<Integer, Integer> j:contraintes.values()) {
			for(Integer elgauche2 : j.keySet()) {
				eldroite.add(this.Nb_Seance(elgauche2)+j.get(elgauche2)-1);
			}
		}

		for (int i=0; i<elgauche.size(); i++) {
			model.arithm(sortedstarts[elgauche.get(i)], "<", sortedstarts[eldroite.get(i)]).post();
		}
		this.ss=sortedstarts;
		this.sp=sortedplanning;
		this.s=starts;
		//this.p=plan;
	}
	 */
	
	
	
	/*
	 * Contraintes de disponibilité des enseignants à Nantes et Brest (cf. classe "Donnee")
	 */
	public void Contrainte_Dispo_ModuleN() {
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(nb_Seances[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
		}
		
		for (int i = 0; i < donnee.getCalendrierN().getNb_Creneaux(); i++) {
			for (int j = 1; j < donnee.getListe_Module().size(); j++) {
				String mailNantes = donnee.getListe_Module().get(j).getMails().get(Localisation.Nantes);
				if(!userList.get(mailNantes).getUnavailabilitiesTraduction().get(i).equals(1)) {
					model.arithm(planning[i], "!=", j).post();
				}
			}
		}
	}
	
	public void Contrainte_Dispo_ModuleB() {
		for (int i = 0; i < donnee.getCalendrierB().getNb_Creneaux(); i++) {
			for (int j = 1; j < donnee.getListe_Module().size(); j++) {
				String mailBrest = donnee.getListe_Module().get(j).getMails().get(Localisation.Brest);
				if(!userList.get(mailBrest).getUnavailabilitiesTraduction().get(i).equals(1)) {
					model.arithm(planning[i], "!=", j).post();
				}
			}
		}
	}
	
	/*
	 * Contrainte de simultanéité pour certains modules à Brest et Nantes (cf. classe Module)
	 */
	public void Contrainte_Sync() {
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			if(donnee.getListe_Module().get(i).getIsSync()) {
				for (int j = 0; j < donnee.getCalendrierB().getNb_Creneaux(); j++) {
					model.ifThen(model.arithm(planning[j], "=", i), model.arithm(planningB[j], "=", i));
				}
			}
		}
	}

	public void addConstraints() {
		//test_key(); (Contrainte de KeySort pour la précédence)
		Contrainte_Sync();
		Contrainte_DispoN();
		Contrainte_DispoB();
		Contrainte_nbcoursN();
		Contrainte_nbcoursB();
		Contrainte_Equilibrage0N();
		Contrainte_Equilibrage0B();
		Contrainte_mercredi_soir0N();
		Contrainte_mercredi_soir0B();
		Contrainte_Dispo_ModuleN();
		Contrainte_Dispo_ModuleB();
		Contrainte_groupement_cours();
		Contrainte_etalement();
	}

	public void solve() {
		solver.printShortFeatures();
		solver.showShortStatistics();
		//solver.setSearch(Search.inputOrderLBSearch(planning), Search.inputOrderLBSearch(planningB)); Stratégie à privilégier pour utiliser KeySort
		solver.showDashboard();
		solver.findSolution();
	}
	
	/*
	 * Méthode permettant de récupérer le nom du module à partir de son indice dans la liste des modules
	 */
	
	public String num_nom(int i){
		for (int j = 1; j < donnee.getListe_Module().size(); j++) {
			if (j==i) {
				return donnee.getListe_Module().get(j).getName();
			}
		}
		return "-";	
	}
	
	/*
	 * Méthode permettant de retourner la date en format String du i-ème jour après la date de début"
	 */
	
	public String Jour(int i) {
		
		LocalDate Datedebut = LocalDate.of(Integer.valueOf(donnee.getDebut().substring(0,4)),Integer.valueOf(donnee.getDebut().substring(5,7)), Integer.valueOf(donnee.getDebut().substring(8,10)));
		LocalDate Date = Datedebut;
		if(Datedebut.getDayOfWeek().getValue() == 3) {
			if(i%2==0) {
				Date = Datedebut.plusDays(7*(i/2));
		}
		else if(i%2!=0) {
			Date = Datedebut.plusDays(6+7*(i/2));
			}
		}
		if(Datedebut.getDayOfWeek().getValue() == 2) {
			if(i%2==0) {
				Date = Datedebut.plusDays(7*(i/2));
			} else if(i%2!=0) {
				Date = Datedebut.plusDays(1+7*(i/2));
			}
		}

		String res = "";
		res += Date;
		return res;
		
		}
	/*
	 * Pour tester, méthodes utiles pour "imprimer" le calendrier obtenu à Nantes (getSolutionN) et Brest (getSolutionB)
	public String getSolutionN() {
		
		String res = "";
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			res += nb_Seances[i] + "\n";
		}
		
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			res += nb0parjour[i] + "\n";
		}
		
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				res += agendajour[i][j] + "\n";
			}
		}
		
		res += "\n\n";
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			res += "Jour " + i + ":  ";
		}
		res += "\n";
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
				res+= agendajour[i][j].getValue()+"        ";
			}
			res += "\n";
		}
		
		System.out.println(res);
		
		return res;
	}
	
	public String getSolutionB() {
		HashMap<Integer, String> num_nom= new HashMap<>();
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			num_nom.put(i, donnee.getListe_Module().get(i).getName());
		}		
		String res = "";
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			res += nb_SeancesB[i] + "\n";
		}
		
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			res += nb0parjourB[i] + "\n";
		}
		
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				res += agendajourB[i][j] + "\n";
			}
		}
		
		res += "\n\n";
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			res += "Jour " + i + ":  ";
		}
		res += "\n";
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
				res+= agendajourB[i][j].getValue()+"        ";
			}
			res += "\n";
		}
		
		System.out.println(res);
		
		return res;
	}
	 */
	
	/*
	 * Méthode permettant de créer et remplir un fichier csv avec le planning trouvé.
	 */
	public void ecrire(String fileName) throws IOException {
		String SEPARATOR = "\n";
		String DELIMITER = ",";
	
		File outFile = new File("/var/lib/data/files/" + fileName);
		outFile.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(outFile);
        
        System.out.println("Writer file: " + outFile.getAbsolutePath());
        System.out.println("With encoding: " + fileWriter.getEncoding());
        ArrayList<String> l=new ArrayList<String>();
        l.add("8h-9h15");
        l.add("9h30-10h45");
        l.add("11h-12h15");
        l.add("13h45-15h");
        l.add("15h15-16h30");
        l.add("16h45-18h");
        
		fileWriter.write("Nantes");

		fileWriter.write(DELIMITER);

        for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
        	

        	fileWriter.write(Jour(i));
			fileWriter.write(DELIMITER);
        }
        fileWriter.write(SEPARATOR);
		for (int j = 0; j < 6; j++) {
			
        	fileWriter.write(l.get(j));
			fileWriter.write(DELIMITER);


			for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
		        String res = "";

				res+= num_nom(agendajour[i][j].getValue());
				fileWriter.write(res);
				fileWriter.write(DELIMITER);
			}

			
			fileWriter.write(SEPARATOR);

			
		}
		
		fileWriter.write(SEPARATOR);
		fileWriter.write(SEPARATOR);
		
		fileWriter.write("Brest");

		fileWriter.write(DELIMITER);

        for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
        	

        	fileWriter.write(Jour(i));
			fileWriter.write(DELIMITER);
        }
        fileWriter.write(SEPARATOR);
		for (int j = 0; j < 6; j++) {
			
        	fileWriter.write(l.get(j));
			fileWriter.write(DELIMITER);


			for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
		        String res = "";

				res+= num_nom(agendajourB[i][j].getValue());
				fileWriter.write(res);
				fileWriter.write(DELIMITER);
			}

			
			fileWriter.write(SEPARATOR);
		
		}
		
        fileWriter.close();

	}

	/*
	 * Méthode utile pour utiliser KeySort. Dans le monde trié, renvoie l'indice d'un cours
	public int Nb_Seance(int i) {
		if (i==0) {
			return 0;
		} else {
			int k=donnee.Nb_0B();
			for(int j=1;j<i;j++) {
				k+=donnee.getListe_Module().get(j).getSlotsNumber();
			}
			return k;
		}
	}
	 */
		
	public static void main(String[] args) throws IOException {
//		Request ***********************************************************************************************
		Map<Localisation, String> mails = new HashMap<>();
		mails.put(Localisation.Nantes, "responsableNantes@test.com");
		mails.put(Localisation.Brest, "responsableBrest@test.com");
		
		ArrayList<Module> modulesUeA = new ArrayList<Module>();
		modulesUeA.add(new Module("moduleA1", 7, mails, false)); //7
		modulesUeA.add(new Module("moduleA2", 7, mails, false)); //7
		modulesUeA.add(new Module("moduleA3", 8, mails, false)); //8
		ArrayList<Module> modulesUeB = new ArrayList<Module>();
		modulesUeB.add(new Module("moduleB1", 11, mails, false));//11
		modulesUeB.add(new Module("moduleB2", 11, mails, false));
		modulesUeB.add(new Module("moduleB3", 11, mails, false));
		ArrayList<Module> modulesUeC = new ArrayList<Module>();
		modulesUeC.add(new Module("moduleB1", 4, mails, true));//4
		modulesUeC.add(new Module("moduleB2", 4, mails, false));//4
		modulesUeC.add(new Module("moduleB3", 3, mails, false));//3

		 
		
		Map<Localisation, ArrayList<Unavailability>> unavailabilities = new HashMap<>();
		ArrayList<Integer> slots = new ArrayList<>();
		slots.add(1);
		slots.add(2);
		slots.add(3);
		slots.add(4);
		slots.add(5);
		slots.add(6);
		ArrayList<Unavailability> unavailabilitiesNantes = new ArrayList<>();
		unavailabilitiesNantes.add(new Unavailability("2022-12-20", slots));
		unavailabilities.put(Localisation.Nantes, unavailabilitiesNantes);
		ArrayList<Unavailability> unavailabilitiesBrest = new ArrayList<>();
		unavailabilitiesBrest.add(new Unavailability("2022-12-20", slots));
		unavailabilities.put(Localisation.Brest, unavailabilitiesBrest);
		
		Request request = new Request(14, modulesUeA, modulesUeB, modulesUeC, unavailabilities, "2022-12-14", 12345); //14
		
//		*******************************************************************************************************

//		Function solver() of SolverService ********************************************************************
		
		Donnee data = new Donnee(request.getModulesUeA(), request.getModulesUeB(), request.getModulesUeC(),
				request.getWeeksNumber(), request.getUnavailabilities().get(Localisation.Nantes),
				request.getUnavailabilities().get(Localisation.Brest), request.getStartDate());
		
		ArrayList<Unavailability> unavailabilitiesUserNantes = new ArrayList<>();
		unavailabilitiesUserNantes.add(new Unavailability("2022-12-27", slots));
		User userNantes = new User("responsableNantes@test.com", Role.ResponsableTAF, unavailabilitiesUserNantes, Localisation.Nantes, 14);
		userNantes.setUnavailabilitiesTraduction(data.Traduction(userNantes.getUnavailabilities()));
		
		ArrayList<Unavailability> unavailabilitiesUserBrest = new ArrayList<>();
		unavailabilitiesUserBrest.add(new Unavailability("2022-12-28", slots));
		User userBrest = new User("responsableBrest@test.com", Role.ResponsableTAF, unavailabilitiesUserBrest, Localisation.Brest, 14);
		userBrest.setUnavailabilitiesTraduction(data.Traduction(userBrest.getUnavailabilities()));
		Map<String, User> userList = new HashMap<>();
		userList.put(userNantes.getMail(), userNantes);
		userList.put(userBrest.getMail(), userBrest);
		
		Modelisation test = new Modelisation(data, userList);
		
		/*
		 *  Pour tester KeySort
		HashMap<Integer, Integer> gauche = new HashMap<Integer, Integer>(1);
		HashMap<Integer, Integer> droite = new HashMap<Integer, Integer>(1);
		
		Integer gaucheg = 2;
		Integer gauched = 1;
		Integer droiteg=1;
		Integer droited =1;
		
		HashMap<Integer, Integer> gauche2 = new HashMap<Integer, Integer>(1);
		HashMap<Integer, Integer> droite2 = new HashMap<Integer, Integer>(1);
		
		
		Integer gaucheg2 = 3;
		Integer gauched2 = 1;
		Integer droiteg2=0;
		Integer droited2 =3;
		
		
		gauche.put(gaucheg, gauched);
		gauche2.put(gaucheg2, gauched2);
		droite.put(droiteg, droited);
		droite2.put(droiteg2, droited2);

		HashMap<HashMap<Integer, Integer>, HashMap<Integer, Integer>> contraintes = new HashMap<HashMap<Integer, Integer>, HashMap<Integer, Integer>>(1);
		contraintes.put(gauche, droite);
		contraintes.put(gauche2, droite2);
		test.contraintes_keysort=contraintes;
		System.out.println((test.contraintes_keysort.keySet()));
		}
		 */
		
		test.BuildModel();
		test.addConstraints();
		test.solve();
		test.ecrire(request.getCreationDate() + ".csv");
		test.model.getSolver();
		
//		*******************************************************************************************************
	}

}
