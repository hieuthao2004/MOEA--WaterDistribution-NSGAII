package com.hanu.water_distribution;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

import java.util.ArrayList;
import java.util.List;

public class WaterDistributionProblem implements Problem {

    private final List<WaterData> waterDataList;
    private static final double MAX_WATER_SUPPLY = 5000.0; // Giá trị tối đa của tổng lượng nước cung cấp

    public WaterDistributionProblem() {
        this.waterDataList = createDataset();
    }

    // Phương thức tạo dataset
    private List<WaterData> createDataset() {
        List<WaterData> dataset = new ArrayList<>();
        dataset.add(new WaterData("Zone A", 500, 0.50, 0.05));
        dataset.add(new WaterData("Zone B", 300, 0.70, 0.07));
        dataset.add(new WaterData("Zone C", 450, 0.80, 0.04));
        dataset.add(new WaterData("Zone D", 600, 0.60, 0.10));
        dataset.add(new WaterData("Zone E", 350, 0.40, 0.06));
        dataset.add(new WaterData("Zone F", 700, 1.00, 0.03));
        dataset.add(new WaterData("Zone G", 800, 0.90, 0.08));
        dataset.add(new WaterData("Zone H", 550, 0.75, 0.09));
        dataset.add(new WaterData("Zone I", 400, 0.65, 0.02));
        return dataset;
    }

    @Override
    public String getName() {
        return "Water Distribution Problem";
    }

    @Override
    public int getNumberOfVariables() {
        return waterDataList.size(); // numbers of zones found
    }

    @Override
    public int getNumberOfObjectives() {
        return 2; // 2 tasks: maximum water use and minimize loss
    }

    @Override
    public int getNumberOfConstraints() {
        return 1; // 1 constraint (rằng buộc: giới hạn lượng nước tối đa là 5000 litter)
    }

    @Override
    public void evaluate(Solution solution) {
        double[] variables = new double[getNumberOfVariables()];
        String[] regionNames = new String[getNumberOfVariables()]; // Mảng chứa tên vùng
        
        for (int i = 0; i < getNumberOfVariables(); i++) {
            variables[i] = ((RealVariable) solution.getVariable(i)).getValue();
            regionNames[i] = waterDataList.get(i).getName(); // Lấy tên vùng tương ứng
        }

        // Efficiency and waste (loss) variable
        double efficiency = calculateEfficiency(variables);
        double waste = calculateWaste(variables);

        // set objectives (tasks to be done) for the problem
        solution.setObjective(0, -efficiency); // Tối đa hóa hiệu quả (let the variable be negative)
        solution.setObjective(1, waste); // Tối thiểu hóa lãng phí    (let the variable be positive)

        // Gán thuộc tính "names" cho solution
        solution.setAttribute("names", regionNames);

        // Kiểm tra ràng buộc
        double totalWaterSupply = calculateTotalWaterSupply(variables);
        if (totalWaterSupply > MAX_WATER_SUPPLY) {
            // Nếu ràng buộc không được thỏa mãn, điều chỉnh mục tiêu hoặc thêm penalty vào các mục tiêu
            solution.setObjective(0, Double.MAX_VALUE); // Ví dụ: penalize giải pháp nếu không thỏa mãn ràng buộc
        }
    }

    // double efficiency = calculateEfficiency(variables);
    private double calculateEfficiency(double[] variables) {
        // Tính toán hiệu quả phân phối nước dựa trên nhu cầu
        double totalEfficiency = 0.0;
        for (int i = 0; i < variables.length; i++) {
            totalEfficiency += variables[i] * waterDataList.get(i).getDemand();
        }
        return totalEfficiency / waterDataList.size();
    }

    // double waste = calculateWaste(variables);
    private double calculateWaste(double[] variables) {
        // Calculate total loss water
        double totalWaste = 0.0;
        for (int i = 0; i < variables.length; i++) {
            totalWaste += waterDataList.get(i).getLoss() * variables[i];
        }
        return totalWaste;
    }

    //Total water supply
    private double calculateTotalWaterSupply(double[] variables) {
        double totalSupply = 0.0;
        for (int i = 0; i < variables.length; i++) {
            totalSupply += variables[i];
        }
        return totalSupply;
    }

    @Override
    public Solution newSolution() {
        // Tạo giải pháp mới với các biến quyết định
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives());
        for (int i = 0; i < getNumberOfVariables(); i++) {
            solution.setVariable(i, new RealVariable(0.0, 1.0)); // Deciding variables (results) in the percentage form [0, 1]
        }
        return solution;
    }

    @Override
    public void close() {};
}
