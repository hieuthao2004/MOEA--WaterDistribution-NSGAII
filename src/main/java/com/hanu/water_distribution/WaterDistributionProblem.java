package com.hanu.water_distribution;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

import java.util.ArrayList;
import java.util.List;

public class WaterDistributionProblem implements Problem {

    private final List<WaterData> waterDataList;

    public WaterDistributionProblem() {
        this.waterDataList = createDataset();
    }

    // Phương thức tạo dataset
    // Phương thức tạo dataset
    private List<WaterData> createDataset() {
        List<WaterData> dataset = new ArrayList<>();
        
        // Các vùng với nhu cầu và chi phí khác nhau
        dataset.add(new WaterData("Zone A", 500, 0.50, 0.05)); // Vùng có nhu cầu cao
        dataset.add(new WaterData("Zone B", 300, 0.70, 0.07)); // Vùng có nhu cầu thấp
        dataset.add(new WaterData("Zone C", 450, 0.80, 0.04)); // Vùng nhu cầu trung bình
        dataset.add(new WaterData("Zone D", 700, 0.60, 0.10)); // Vùng có nhu cầu cao
        dataset.add(new WaterData("Zone E", 200, 0.40, 0.06)); // Vùng có nhu cầu rất thấp
        dataset.add(new WaterData("Zone F", 900, 1.00, 0.03)); // Vùng có nhu cầu rất cao
        dataset.add(new WaterData("Zone G", 100, 0.90, 0.08)); // Vùng có nhu cầu rất thấp
        dataset.add(new WaterData("Zone H", 800, 0.75, 0.09)); // Vùng có nhu cầu cao
        dataset.add(new WaterData("Zone I", 150, 0.65, 0.02)); // Vùng có nhu cầu rất thấp
        
        return dataset;
    }


    @Override
    public String getName() {
        return "Water Distribution Problem";
    }

    @Override
    public int getNumberOfVariables() {
        return waterDataList.size(); // số lượng biến tương ứng với số vùng
    }

    @Override
    public int getNumberOfObjectives() {
        return 2; // 2 mục tiêu: tối đa hóa hiệu quả phân phối nước và tối thiểu hóa lãng phí
    }

    @Override
    public int getNumberOfConstraints() {
        return 2; // 2 constraints như đã mô tả
    }

    @Override
    public void evaluate(Solution solution) {
        double[] variables = new double[getNumberOfVariables()];   // Mảng chứa biến
        String[] regionNames = new String[getNumberOfVariables()]; // Mảng chứa tên vùng

        for (int i = 0; i < getNumberOfVariables(); i++) {
            variables[i] = ((RealVariable) solution.getVariable(i)).getValue();
            regionNames[i] = waterDataList.get(i).getName(); // Lấy tên vùng tương ứng
        }

        // Tính toán hiệu quả và lãng phí
        double efficiency = calculateEfficiency(variables);
        double waste = calculateWaste(variables);

        // Đặt mục tiêu (objectives) cho bài toán
        solution.setObjective(0, -efficiency);  // Tối đa hóa hiệu quả (cho giá trị âm để tối ưu hóa)
        solution.setObjective(1, waste);        // Tối thiểu hóa lãng phí

        // Gán thuộc tính "names" cho solution
        solution.setAttribute("names", regionNames);

        // Tính toán và kiểm tra các constraints
        double[] constraints = new double[getNumberOfConstraints()];

        // Constraint 1: Giới hạn nước cung cấp cho từng vùng
        for (int i = 0; i < getNumberOfVariables(); i++) {
            double maxSupply = 1.2 * waterDataList.get(i).getDemand();
            if (variables[i] > maxSupply) {
                constraints[0] += variables[i] - maxSupply;
            }
        }

        // Constraint 2: Ưu tiên phân phối nước cho vùng nhu cầu cao
        double highDemandSupply = 0.0;
        double lowDemandSupply = 0.0;
        for (int i = 0; i < getNumberOfVariables(); i++) {
            if (waterDataList.get(i).getDemand() > 500) {
                highDemandSupply += variables[i];
            } else {
                lowDemandSupply += variables[i];
            }
        }

        if (highDemandSupply < lowDemandSupply) {
            constraints[1] = lowDemandSupply - highDemandSupply;
        }

        // Gán các constraints vào solution
        solution.setConstraints(constraints);
    }

    // Tính toán hiệu quả phân phối nước
    private double calculateEfficiency(double[] variables) {
        double totalEfficiency = 0.0;
        for (int i = 0; i < variables.length; i++) {
            totalEfficiency += variables[i] * waterDataList.get(i).getDemand();
        }

        return totalEfficiency / waterDataList.size();
    }

    // Tính toán lãng phí nước
    private double calculateWaste(double[] variables) {
        double totalWaste = 0.0;
        for (int i = 0; i < variables.length; i++) {
            totalWaste += waterDataList.get(i).getLoss() * variables[i];
        }
        return totalWaste;
    }

    @Override
    public Solution newSolution() {
        // Tạo giải pháp mới với các biến quyết định
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        for (int i = 0; i < getNumberOfVariables(); i++) {
            solution.setVariable(i, new RealVariable(0.0, 1.0)); // Các biến quyết định nằm trong khoảng [0, 1]
        }
        return solution;
    }

    @Override
    public void close() {}
}
