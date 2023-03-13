package com.solver.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

public class TestKeySort {
	public static void main(String[] args) {
		int EXPERT = 3;
//		int beginnerWorkingTime = 6;
//		int expertWorkingTime = 9;
		Model model = new Model("keysort");
		int n = 10;
		Random rnd= new Random();
		int[] start_dates = IntStream.range(0, n).map(i -> rnd.nextInt(24)).toArray();
		int[] durations = IntStream.range(0, n).map(i -> 1 + rnd.nextInt(2)).toArray();
		int[] difficulties = IntStream.range(0, n).map(i -> rnd.nextInt(2)).toArray(); // 0: easy, 1: difficult

		System.out.printf("int[] start_dates = {%s};\n", Arrays.toString(start_dates));
		System.out.printf("int[] durations = {%s};\n", Arrays.toString(durations));
		System.out.printf("int[] difficulties = {%s};\n", Arrays.toString(difficulties));

		// Where the tasks are not ordered
		IntVar[] starts = IntStream.range(0, n).mapToObj(i -> model.intVar("S_" + i, start_dates[i])).toArray(IntVar[]::new);
		IntVar[] durs = IntStream.range(0, n).mapToObj(i -> model.intVar("D_" + i, durations[i])).toArray(IntVar[]::new);
		IntVar[] ends = IntStream.range(0, n).mapToObj(i -> model.intVar("E_" + i, start_dates[i] + durations[i])).toArray(IntVar[]::new);
		IntVar[] users = IntStream.range(0, n) 
		        .mapToObj(i -> model.intVar("U_" + i,
		                difficulties[i] == 0 ? 0 : 3, 4)) 
		        .toArray(IntVar[]::new); // 0 -> 2 : beginners, 3-4 : experts
/*
		model.diffN(
		        starts, users,
		        durs, IntStream.range(0, n).mapToObj(i -> model.intVar(1)).toArray(IntVar[]::new),
		        true
		).post();
*/
		// Where (views of) the tasks are ordered by resources, then by starting time
		IntVar[] sortedStarts = model.intVarArray("SS", n, 0, 23);
		IntVar[] sortedDurs = model.intVarArray("SD", n, 1, 3);
		IntVar[] sortedEnds = model.intVarArray("SE", n, 1, 24);
		IntVar[] sortedUsers = model.intVarArray("SU", n, 0, 4);
		for (int i = 0; i < n; i++) {
			System.out.print("test1"+sortedUsers[i]);
			}
		// Ordered view of the tasks
		IntVar[] permutations = model.intVarArray("P", n, 1, n);
		model.keySort(
		        IntStream.range(0, n).mapToObj(i -> new IntVar[]{users[i], starts[i], durs[i], ends[i]}).toArray(IntVar[][]::new),
		        permutations,
		        IntStream.range(0, n).mapToObj(i -> new IntVar[]{sortedUsers[i], sortedStarts[i], sortedDurs[i], sortedEnds[i]}).toArray(IntVar[][]::new),
		        3
		).post();
		for (int i = 0; i < n; i++) {
			System.out.print("heyyy1 "+sortedUsers[i].getValue());
			}
		/*
		for (int i = 0; i < n; i++) {
			System.out.print("test2"+sortedUsers[i]);
			}
		// In the sorted side
		BoolVar[] y = model.boolVarArray("shift", n); // Boolean variable : y_i = (u'_(i-1) == u_i)
		IntVar[] w = new IntVar[n];
		for (int i = 0; i < n; i++) {
		    if (i == 0) {
		        y[i].eq(0).post();
		        w[i] = sortedDurs[i];
		    } else {
		        model.reifyXeqY(sortedUsers[i - 1], sortedUsers[i], y[i]);
		        w[i] = sortedDurs[i].add(y[i].ift(w[i - 1].add(sortedStarts[i].sub(sortedEnds[i - 1])), 0)).intVar();
		    }
		    w[i].le(sortedUsers[i].lt(EXPERT).ift(beginnerWorkingTime, expertWorkingTime)).post();
		}
		for (int i = 0; i < n; i++) {
			System.out.print("test3 "+sortedUsers[i]);
			}
		// Redundant constraints
		BiPredicate<Integer, Integer> overlap = (i, j) ->
		        (start_dates[j] <= start_dates[i] && start_dates[i] < start_dates[j] + durations[j])
		                || (start_dates[i] <= start_dates[j] && start_dates[j] < start_dates[i] + durations[i]);
		for (int i = 0; i < n; i++) {
		    for (int j = i + 1; j < n; j++) {
		        if (overlap.test(i, j)) {
		            System.out.printf("[%d,%d] ov [%d,%d]\n",
		                    start_dates[i], start_dates[i] + durations[i],
		                    start_dates[j], start_dates[j] + durations[j]);
		            users[i].ne(users[j]).post();
		        }
		    }
		}*/
		Solver solver = model.getSolver();
		solver.printShortFeatures();
		solver.setSearch(Search.inputOrderLBSearch(users));
		if (solver.solve()) {
			for (int i = 0; i < n; i++) {
				System.out.print("heyyy "+sortedUsers[i].getValue());
				}
		    System.out.printf("\nSolution #%d\n", solver.getSolutionCount());
		    for (int i = 0; i < n; i++) {
		        System.out.printf("\tTask #%d [%d,%d] by user #%d (%s)\n",
		                i + 1, starts[i].getValue(), ends[i].getValue(), users[i].getValue(),
		                users[i].getValue() < EXPERT ? "B" : "E");
		    }
		    System.out.print("In sorted world:\n");
		    for (int i = 0; i < n; i++) {
		        if (i == 0 || sortedUsers[i - 1].getValue() != sortedUsers[i].getValue()) {
		            System.out.printf("\tUser #%d (%s):\n", sortedUsers[i].getValue(),
		                    sortedUsers[i].getValue() < EXPERT ? "B" : "E");
		        }
		        System.out.printf("\t\tTask #%d [%d,%d]\n",
		                permutations[i].getValue(), sortedStarts[i].getValue(), sortedEnds[i].getValue());
		        if (i == n - 1 || sortedUsers[i].getValue() != sortedUsers[i + 1].getValue()) {
		           // System.out.printf("\t--> working time : %d\n", w[i].getValue());
		        }
		    }
		    //solution = true;
		}
		solver.printShortStatistics();
	}

}
