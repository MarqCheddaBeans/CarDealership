package com.pluralsight;

public class LeaseContract extends Contract{

    //Create constant for lease annual interest and months because it will not change
    private static final double LEASE_RATE = .04;
    private static final int LEASE_MONTHS = 36;
    private double expectedEndingValue;
    private double leaseFee;
    private String contractType;
    private boolean financed;

    //constructor
    public LeaseContract(int id,String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financed) {
        super(id,date, customerName, customerEmail, vehicleSold);

        //Calculate expected ending value and lease fee when contract is created
        double price = vehicleSold.getPrice();
        this.expectedEndingValue = price * .5;
        this.leaseFee = price * 0.07;
        this.contractType = "LEASE";
        this.financed = financed;
    }

    //Getters
    public String getContractType() {
        return contractType;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public boolean isFinanced(){
        return financed;
    }

    @Override
    public int getID() {
        return this.id;
    }

    //Override abstract method for Lease pricing
    @Override
    public double getTotalPrice(){
        //Calculate total price
        double price = (getVehicleSold().getPrice() - expectedEndingValue) + leaseFee;

        return price;
    }

    @Override
    public double getMonthlyPayment(){

        //All leases are financed at 4% for 36 months
        double totalPrice = getTotalPrice();
        double monthlyRate = LEASE_RATE/12;

        // Standard amortization formula -> P = r * PV / (1 - (1 + r)^(-n))
        monthlyPayment = (totalPrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -LEASE_MONTHS));

        return monthlyPayment;
    }

    @Override
    public String toString(){
        String lease = String.format("\n%-6s | %-11s | %-30s | %-30s | %-8s | %-5d | %-12s | %-12s | %-10s | %-10s | %-8dmi | $%-10.2f | $%-8.2f | %-8B | $%-8.2f | $%-8.2f", this.getContractType(), this.getDate(), this.getCustomerName(), this.getCustomerEmail(), this.vehicleSold.getVin(), this.vehicleSold.getYear(), this.vehicleSold.getMake(), this.vehicleSold.getModel(), this.vehicleSold.getVehicleType(), this.vehicleSold.getColor(), this.vehicleSold.getMileage(),this.vehicleSold.getPrice(), this.getLeaseFee(), this.isFinanced() ,this.getTotalPrice(), this.getMonthlyPayment());

        return lease;
    }
}
