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
import org.chocosolver.solver.variables.IntVar;

import com.solver.util.Localisation;
import com.solver.util.Role;

public class Modelisation {

	private Model model;
	private Solver solver;
		
	private IntVar[] planning;
	private IntVar[][] agendajour;
	private IntVar[] Nb_Seances;
	private IntVar[] nb0parjour;
	private IntVar[] planningB;
	private IntVar[][] agendajourB;
	private IntVar[] Nb_SeancesB;
	private IntVar[] nb0parjourB;
	
	private Donnee donnee;
	private Map<String, User> userList;

	public Modelisation(Donnee donnee, Map<String, User> userList) {
		this.donnee = donnee;
		this.userList = userList;
	}

	public void BuildModel() {
		model = new Model();
		solver = model.getSolver();
		
		planning = model.intVarArray("planning_Nantes", donnee.getCalendrierN().getNb_Creneaux(), 0, donnee.Nb_cour_different()); // Liste de tout les créneaux, contenant, pour chaque créneaux, le cours affecté.
		agendajour = model.intVarMatrix("agenda-Jour_Nantes", donnee.getCalendrierN().getNb_Jours(), 6, 0, donnee.Nb_cour_different()); // Matrice contenant le nombre de jour, et pour chaque jour, 6 créneaux, qui contienne le cours affecté.
		Nb_Seances = model.intVarArray("Nb_seances_Nantes", donnee.Nb_cour_different(), 0, donnee.getCalendrierN().getNb_Creneaux()); // Liste contenant le nombre de séance part cours différents (Nb_seances[O] contient le nombre de cours vide
		nb0parjour = model.intVarArray("Nombre de 0 par jour_Nantes", donnee.getCalendrierN().getNb_Jours(), 0, 6); // Liste contenant le nombre de cours vide à attribuer par jours (pour equilibrer)
		
		planningB = model.intVarArray("planning_Brest", donnee.getCalendrierB().getNb_Creneaux(), 0, donnee.Nb_cour_different()); // Liste de tout les créneaux, contenant, pour chaque créneaux, le cours affecté.
		agendajourB = model.intVarMatrix("agenda-Jour_Brest", donnee.getCalendrierB().getNb_Jours(), 6, 0, donnee.Nb_cour_different()); // Matrice contenant le nombre de jour, et pour chaque jour, 6 créneaux, qui contienne le cours affecté.
		Nb_SeancesB = model.intVarArray("Nb_seances_Brest", donnee.Nb_cour_different(), 0, donnee.getCalendrierB().getNb_Creneaux()); // Liste contenant le nombre de séance part cours différents (Nb_seances[O] contient le nombre de cours vide
		nb0parjourB = model.intVarArray("Nombre de 0 par jour_Brest", donnee.getCalendrierB().getNb_Jours(), 0, 6); // Liste contenant le nombre de cours vide à attribuer par jours (pour equilibrer)
		

	}

	public void Contrainte_nbcoursN() { // Contrainte permettant de remplir toute les variables avec le bon nombre de cours
		// On pose la contrainte sur la variable Nb_Seances
		model.arithm(Nb_Seances[0], "=", donnee.Nb_0N()).post();
		
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(Nb_Seances[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
		}
		// On rempli la variable planning en fonction de la variable Nb_Seances
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			model.count(i, planning, Nb_Seances[i]).post();
		}
		// On remplit la variable agendajour en fonction de la variable planning
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				model.arithm(agendajour[i][j], "=", planning[6 * i + j]).post();
			}
		}
	}
	
	public void Contrainte_nbcoursB() { // Contrainte permettant de remplir toute les variables avec le bon nombre de cours
		// On pose la contrainte sur la variable Nb_Seances
		model.arithm(Nb_SeancesB[0], "=", donnee.Nb_0B()).post();
		
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(Nb_SeancesB[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
		}
		// On rempli la variable planning en fonction de la variable Nb_Seances
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			model.count(i, planningB, Nb_SeancesB[i]).post();
		}
		// On rempli la variable agendajour en fonction de la variable planning
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			for (int j = 0; j < 6; j++) {
				model.arithm(agendajourB[i][j], "=", planningB[6 * i + j]).post();
			}
		}
	}

	public void Contrainte_Equilibrage0N() { // Contrainte permettant d'Équilibrer le nombre de 0 par jours
		// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			model.arithm(nb0parjour[i], ">=", donnee.Nb_0JourN()).post();
		}
		/*
		 * for (int i=0; i<donnee.getCalendrier().getNb_Jours(); i++) {
		 * model.arithm(nb0parjour[i], "<=", donnee.Nb_0Jour()+1).post(); }
		 * 
		 */

		// On place le bon nombre de séances vides dans la variable agendaJour
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
			model.count(0, agendajour[i], nb0parjour[i]).post();
		}
		// On s'assure que la somme de séances vides est bien égale à Nb_0
		model.sum(nb0parjour, "=", donnee.Nb_0N()).post();

	}
	
	public void Contrainte_Equilibrage0B() { // Contrainte permettant d'Équilibrer le nombre de 0 par jours
		// On remplit la variable nb0parjour qui correspond au nombre de 0 par jour
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			model.arithm(nb0parjourB[i], ">=", donnee.Nb_0JourB()).post();
		}
		/*
		 * for (int i=0; i<donnee.getCalendrier().getNb_Jours(); i++) {
		 * model.arithm(nb0parjour[i], "<=", donnee.Nb_0Jour()+1).post(); }
		 * 
		 */

		// On place le bon nombre de séances vides dans la variable agendaJour
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours(); i++) {
			model.count(0, agendajourB[i], nb0parjourB[i]).post();
		}
		// On s'assure que la somme de séances vides est bien égale à Nb_0
		model.sum(nb0parjourB, "=", donnee.Nb_0B()).post();

	}

	public void Contrainte_mercredi_soir0N() { // Contrainte permettant de forcer le créneau du mercredi soir à 0
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours() / 2; i++) {
			model.arithm(agendajour[2 * i + 1][5], "=", 0).post();
		}
	}
	public void Contrainte_mercredi_soir0B() { // Contrainte permettant de forcer le créneau du mercredi soir à 0
		for (int i = 0; i < donnee.getCalendrierB().getNb_Jours() / 2; i++) {
			model.arithm(agendajourB[2 * i + 1][5], "=", 0).post();
		}
	}

	public void Contraintes_Automate1_v2() {
		
		ArrayList<StringBuilder> l= new ArrayList<StringBuilder>();
		StringBuilder expression0 = new StringBuilder ();
		String Cours_0 = new String ("0*[^0]{0,4}0*");
		expression0.append(Cours_0);
		l.add(expression0);
		ArrayList<FiniteAutomaton> l_auto = new ArrayList<FiniteAutomaton>();
		for (int i=1;i<donnee.Nb_cour_different();i++) {
			StringBuilder expression = new StringBuilder ();
			String automat = new String ("[^"+i+"]*"+i+"{0,3}[^"+i+"]*");
			expression.append(automat);
			l.add(expression);
		}
		
		for(int i=0;i<l.size();i++) {
			l_auto.add(new FiniteAutomaton((l.get(i)).toString(),0,6));
		}
		for (int i = 0; i < donnee.getCalendrierN().getNb_Jours(); i++) {
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
	
public void Contraintes_Automate2_v2() {
		
		ArrayList<StringBuilder> lN= new ArrayList<StringBuilder>();
		ArrayList<StringBuilder> lB= new ArrayList<StringBuilder>();

		ArrayList<FiniteAutomaton> l_autoN = new ArrayList<FiniteAutomaton>();
		ArrayList<FiniteAutomaton> l_autoB = new ArrayList<FiniteAutomaton>();

		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			String mailBrest = donnee.getListe_Module().get(i).getMails().get(Localisation.Brest);
			String mailNantes = donnee.getListe_Module().get(i).getMails().get(Localisation.Nantes);
			
			StringBuilder expressionN = new StringBuilder ();
			String automatN = new String ("[^"+i+"]*"+i+"{1}.{0,"+(userList.get(mailNantes).getSpreadWeeks()*12-1)+"}[^"+i+"]*");
			expressionN.append(automatN);
			lN.add(expressionN);
			
			StringBuilder expressionB = new StringBuilder ();
			String automatB = new String ("[^"+i+"]*"+i+"{1}.{0,"+(userList.get(mailBrest).getSpreadWeeks()*12-1)+"}[^"+i+"]*");
			expressionB.append(automatB);
			lB.add(expressionB);
		}
		
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
	
	public void precedence() {
		ArrayList<Integer> l = new ArrayList<Integer>();
		for (int i=0; i<donnee.getCalendrierN().getNb_Creneaux();i++) {
			l.add(i);
		}
		IntVar[] starts = IntStream.range(0, donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> model.intVar( "S_"+ i, l.get(i))).toArray(IntVar[]::new);
		IntVar[] sortedplanning = model.intVarArray("SP", donnee.getCalendrierN().getNb_Creneaux(), 0,donnee.Nb_cour_different());
		IntVar[] permutation = model.intVarArray("P", donnee.getCalendrierN().getNb_Creneaux(),0,donnee.getCalendrierN().getNb_Creneaux());
		model.keySort(IntStream.range(0,donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> new IntVar[]{starts[i],planning[i]} ).toArray(IntVar[][]::new), permutation, IntStream.range(0,donnee.getCalendrierN().getNb_Creneaux()).mapToObj(i -> new IntVar[]{starts[i],sortedplanning[i]} ).toArray(IntVar[][]::new),2).post();

		
		for (int i=0; i<donnee.getCalendrierN().getNb_Creneaux();i++) {
			System.out.print(planning[i].getValue());
		}
		System.out.println(" ");
		for (int i=0; i<donnee.getCalendrierN().getNb_Creneaux();i++) {
			System.out.print(sortedplanning[i].getValue());
		}
	}
	
	public void Contrainte_Dispo_ModuleN() {
		for (int i = 1; i < donnee.getListe_Module().size(); i++) {
			model.arithm(Nb_Seances[i], "=", donnee.getListe_Module().get(i).getSlotsNumber()).post();
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
		Contrainte_DispoN();
		Contrainte_nbcoursN();
		Contrainte_Equilibrage0N();
		Contrainte_mercredi_soir0N();
		Contrainte_Dispo_ModuleN();
		Contrainte_DispoB();
		Contrainte_nbcoursB();
		Contrainte_Equilibrage0B();
		Contrainte_mercredi_soir0B();
		Contrainte_Dispo_ModuleB();
		Contrainte_Sync();
		Contraintes_Automate1_v2();
		Contraintes_Automate2_v2();
	}

	public void solve() {
		solver.findSolution();
	}
	
	
	public String num_nom(int i){
		for (int j = 1; j < donnee.getListe_Module().size(); j++) {
			if (j==i) {
				return donnee.getListe_Module().get(j).getName();
			}
		}
		return "-";	
	}
	
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
		}
		else if(i%2!=0) {
			Date = Datedebut.plusDays(1+7*(i/2));
		}
		}

		
		
		
		String res = "";

		res += Date;
		return res;
		
		}
	
	
	public String getSolutionN() {
		
		String res = "";
		for (int i = 0; i < donnee.Nb_cour_different(); i++) {
			res += Nb_Seances[i] + "\n";
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
			res += Nb_SeancesB[i] + "\n";
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

	
	public void ecrire() throws IOException {
		
		String SEPARATOR = "\n";
		String DELIMITER = ",";
	
		File outFile = new File("/Users/maximelizot/Desktop/CalendrierNantes.csv");
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

public void ecrireB() throws IOException {
		
		String SEPARATOR = "\n";
		String DELIMITER = ",";
	
		File outFile = new File("/Users/maximelizot/Desktop/CalendrierBrest.csv");
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
		
	
	

		
	public static void main(String[] args) throws IOException {
//		Request ***********************************************************************************************
		Map<Localisation, String> mails = new HashMap<>();
		mails.put(Localisation.Nantes, "responsableNantes@test.com");
		mails.put(Localisation.Brest, "responsableBrest@test.com");
		
		ArrayList<Module> modulesUeA = new ArrayList<Module>();
		modulesUeA.add(new Module("moduleA1", 7, mails, false));
		modulesUeA.add(new Module("moduleA2", 7, mails, false));
		modulesUeA.add(new Module("moduleA3", 8, mails, false));
		ArrayList<Module> modulesUeB = new ArrayList<Module>();
		modulesUeB.add(new Module("moduleB1", 11, mails, false));
		modulesUeB.add(new Module("moduleB2", 11, mails, false));
		modulesUeB.add(new Module("moduleB3", 11, mails, false));
		ArrayList<Module> modulesUeC = new ArrayList<Module>();
		modulesUeC.add(new Module("moduleB1", 4, mails, true));
		modulesUeC.add(new Module("moduleB2", 4, mails, false));
		modulesUeC.add(new Module("moduleB3", 3, mails, false));
		
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
		
		Request request = new Request(14, modulesUeA, modulesUeB, modulesUeC, unavailabilities, "2022-12-14");
		
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
		test.BuildModel();
		test.addConstraints();
		test.solve();
		test.getSolutionN();
		test.getSolutionB();
		test.ecrire();

		
//		*******************************************************************************************************
	}

}
