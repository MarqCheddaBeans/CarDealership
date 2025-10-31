package com.pluralsight;

public class LeaseContract extends Contract{

    //Create constant for lease annual interest and months because it will not change
    private static final double LEASE_RATE = .04;
    private static final int LEASE_MONTHS = 36;
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);

        //Calculate expected ending value and lease fee when contract is created
        double price = vehicleSold.getPrice();
        this.expectedEndingValue = price * .5;
        this.leaseFee = price * 0.07;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    //Override abstract method for Lease pricing
    @Override
    public double getTotalPrice(){

        double price = (getVehicleSold().getPrice() - expectedEndingValue) + leaseFee;

        return price;
    }

    @Override
    public double getMonthlyPayment(){

        //All leases are financed at 4% for 36 months
        double totalPrice = getTotalPrice();
        double monthlyRate = LEASE_RATE/12;

        // Standard amortization formula -> P = r * PV / (1 - (1 + r)^(-n))
        double monthPay = (totalPrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -LEASE_MONTHS));

        return monthPay;
    }

}
