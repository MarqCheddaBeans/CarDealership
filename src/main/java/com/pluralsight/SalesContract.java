package com.pluralsight;

public class SalesContract extends Contract{

    private static final double SALES_TAX = 0.05;
    private static final double RECORDING_FEE = 100;
    private double processingFee;
    private boolean financed;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double SALES_TAX, double RECORDING_FEE, double processingFee, boolean financed) {
        super(date, customerName, customerEmail, vehicleSold);
        this.processingFee = (getVehicleSold().getPrice() > 10000) ? 495:295;
        this.financed = financed;
    }

    public double getSALES_TAX() {
        return SALES_TAX;
    }

    public double getRECORDING_FEE() {
        return RECORDING_FEE;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    @Override
    public double getTotalPrice(){

        double total = ( getVehicleSold().getPrice() + this.processingFee + RECORDING_FEE) * 1.05;

        return total;
    }

    @Override
    public double getMonthlyPayment(){

        double total = getTotalPrice();
        double rate;
        int months;

        if( total >= 10000 ){
            rate = .0425;
            months = 48;
        } else{
            rate = .0525;
            months = 24;
        }

        double monthlyRate = rate / 12;

        double monthPay = (totalPrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, - months));

        return monthPay;
    }

}
