package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Dealership {

    //create properties
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    //The constructorrrr
    public Dealership(String name, String address, String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    //Da getters
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }

    //Method to filter vehicles by min and max price
    public List<Vehicle> getVehiclesByPrice(double min, double max){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getPrice()>= min && v.getPrice()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Method to filter vehicles by make and model
    public List<Vehicle> getVehiclesByMakeModel(String make, String model){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getMake().toLowerCase().contains(make.toLowerCase()) && v.getModel().toLowerCase().contains(model.toLowerCase())){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Method to filter vehicles by min and max year
    public List<Vehicle> getVehiclesByYear(int min, int max){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for (Vehicle v : inventory){
            if(v.getYear()>=min && v.getYear()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Methdod to filter vehicles by color
    public List<Vehicle> getVehiclesByColor(String color){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getColor().equalsIgnoreCase(color)){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Methdod to filter vehicles by min and max miles
    public List<Vehicle> getVehiclesByMileage(int min, int max){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getOdometer()>=min && v.getOdometer()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Methdod to filter vehicles by vehicle type
    public List<Vehicle> getVehiclesByType(String vehicleType){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getVehicleType().equalsIgnoreCase(vehicleType)){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    //Method to get all vehicles in inventory
    public List<Vehicle> getAllVehicles(){
        return this.inventory;
    }

    //Method to add a vehicle to inventory
    public void addVehicle(Vehicle vehicle){
        this.inventory.add(vehicle);
    }

    //Method to remove a vehicle from inventory
    public void removeVehicle(Vehicle vehicle) {

            for (int i = 0; i < inventory.size(); i++){
                if (inventory.get(i).getVin().equalsIgnoreCase(vehicle.getVin()) ) {
                    inventory.remove(i);
                    break;
                }
            }

    }

}
