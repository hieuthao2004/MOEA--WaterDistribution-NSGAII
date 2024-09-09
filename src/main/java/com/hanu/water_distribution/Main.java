package com.hanu.water_distribution;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.util.TypedProperties;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Khởi tạo vấn đề và thuật toán
        Problem problem = new WaterDistributionProblem();
        TypedProperties properties = new TypedProperties();
        Algorithm algorithm = new NSGAII(problem);

        // Thực hiện nhiều bước thuật toán
        int maxSteps = 100; // Số bước tối đa của thuật toán
        for (int step = 0; step < maxSteps; step++) {
            algorithm.step();
        }

        // Lấy kết quả từ thuật toán NSGA-II
        NondominatedPopulation population = algorithm.getResult();
        for (Solution solution : population) {

            // Lấy thông tin các vùng
            String[] names = (String[]) solution.getAttribute("names");
            System.out.println("Zones found:                      " + Arrays.toString(names));

            // Hiển thị hiệu quả phân phối nước và lãng phí nước
            System.out.println("Water distribution efficiency:    " + df.format(-solution.getObjective(0)));
            System.out.println("Water loss:                       " + df.format(solution.getObjective(1)));

            // Hiển thị các vi phạm constraints
            double[] constraints = solution.getConstraints();
            System.out.println("Constraint 1 (Over-supply violation): " + df.format(constraints[0]));
            System.out.println("Constraint 2 (High demand priority violation): " + df.format(constraints[1]));
            System.out.println("------------------------------");
        }

        // Kết thúc và đóng vấn đề
        problem.close();
        System.out.println("The result is given after: " + System.currentTimeMillis() + " milliseconds");
    }
}
