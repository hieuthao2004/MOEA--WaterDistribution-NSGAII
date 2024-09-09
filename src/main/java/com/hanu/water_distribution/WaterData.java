package com.hanu.water_distribution;

public class WaterData {
    private String name;   // Zone
    private double demand; // Litter
    private double cost;   // USD / litter
    private double loss;   // Percentage (0-1)

        // Example
        // {
        //     "Zone"  : Zone A,
        //     "Demand": 500 L, 
        //     "Cost"  : 0.50 usd/l, 
        //     "Loss"  : 0.05
        // }

    public WaterData(String name, double demand, double cost, double loss) {
        this.name = name;
        this.demand = demand;
        this.cost = cost;
        this.loss = loss;
    }

    public String getName() {
        return name;
    }

    public double getDemand() {
        return demand;
    }

    public double getCost() {
        return cost;
    }

    public double getLoss() {
        return loss;
    }
}