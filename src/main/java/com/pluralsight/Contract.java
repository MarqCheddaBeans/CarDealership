package com.pluralsight;

public abstract class Contract {

    //declare properties
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;
    protected double totalPrice;
    protected double monthlyPayment;

    //constructor
    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }
    //getters
    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }

    //abstract classes force children to inherit, contract of sorts
    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();
}
