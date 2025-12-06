package com.pluralsight;

public class SalesContract extends Contract{

    //Create properties for SalesContract, 2 constant for sales tax and recording fww
    public static final double SALES_TAX = .05;
    public static final double RECORDING_FEE = 100;
    private double processingFee;
    private boolean financed;
    private String contractType;
    private int id;

    //the constructor
    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financed) {
        super(date, customerName, customerEmail, vehicleSold);
        this.processingFee = (getVehicleSold().getPrice() > 10000) ? 495:295;
        this.financed = financed;
        this.contractType = "SALE";
    }

    //getters
    public String getContractType() {
        return contractType;
    }


    public double getSALES_TAX() {
        //calculate sales tax (5% of vehicle price)
        return getVehicleSold().getPrice() * SALES_TAX;
    }

    public double getRECORDING_FEE() {
        return RECORDING_FEE;
    }

    public double getProcessingFee() {
        return this.processingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public double getTotalPrice(){
        //Calculate total price
        double total = ( getVehicleSold().getPrice() + (getVehicleSold().getPrice() * SALES_TAX)+ this.processingFee + RECORDING_FEE);
        return total;
    }

    @Override
    public double getMonthlyPayment(){

        //Calculate montly payment
        double total = getTotalPrice();
        double rate;
        int months;

        //4.25% for 48 months if vehicle over 10k, 5.25% for 24 month if under 10k
        if( total >= 10000 ){
            rate = .0425;
            months = 48;
        } else{
            rate = .0525;
            months = 24;
        }

        double monthlyRate = rate / 12;

        //Calculate montly payent with formula
        monthlyPayment = (totalPrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, - months));

        return monthlyPayment;
    }

    @Override
    public String toString(){
        String sale = String.format("\n%-6s | %-11s | %-30s | %-30s | %-8s | %-5d | %-12s | %-12s | %-10s | %-10s | %-8dmi | $%-10.2f | $%-8.2f | $%-8.2f | $%-8.2f | %-8B | $%-8.2f | $%-8.2f", this.getContractType(), this.getDate(), this.getCustomerName(), this.getCustomerEmail(), this.vehicleSold.getVin(), this.vehicleSold.getYear(), this.vehicleSold.getMake(), this.vehicleSold.getModel(), this.vehicleSold.getVehicleType(), this.vehicleSold.getColor(), this.vehicleSold.getMileage(),this.vehicleSold.getPrice(), this.getSALES_TAX(), this.getRECORDING_FEE(), this.getProcessingFee(), this.isFinanced(), this.getTotalPrice(), this.getMonthlyPayment());

        return sale;
    }

}
