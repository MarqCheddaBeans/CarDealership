package com.pluralsight;

public class Vehicle {

    //declare properties
    private String vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int mileage;
    private double price;


    //Constructor
    public Vehicle(String vin, int year, String make, String model, String vehicleType, String color, int mileage, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
    }
    //Lotta getters
    public String getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public int getMileage() {
        return mileage;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString(){
        String formatVehicle = String.format("%-10s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",this.vin,this.year,this.make,this.model,this.vehicleType, this.color, this.mileage, this.price);

        return formatVehicle;
    }
}
