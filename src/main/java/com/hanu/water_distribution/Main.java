package com.hanu.water_distribution;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        Problem problem = new WaterDistributionProblem();
        Algorithm algorithm = new NSGAII(problem);
        algorithm.step();

        NondominatedPopulation population = algorithm.getResult();
        for (Solution solution : population) {

            String[] names = (String[]) solution.getAttribute("names");
            System.out.println("Zones found: " + Arrays.toString(names));
            System.out.println("Efficiency of water distribution: " + df.format(-solution.getObjective(0)));
            System.out.println("Water loss: " + df.format(solution.getObjective(1)));

            double totalWaterSupply = calculateTotalWaterSupply(solution);
            System.out.println("Total water supply: " + df.format(totalWaterSupply) + " L");
            if (totalWaterSupply > 5000) {
                System.out.println("Watch out: Total water supply goes over 5000 L");
            }
            System.out.println("-----------");
        }
    }

    private static double calculateTotalWaterSupply(Solution solution) {
        double totalSupply = 0.0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            totalSupply += ((RealVariable) solution.getVariable(i)).getValue();
        }
        return totalSupply;
    }
}
