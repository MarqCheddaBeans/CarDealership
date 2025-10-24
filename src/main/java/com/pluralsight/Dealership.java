package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.DealershipFileManager.printInventory;
import static com.pluralsight.DealershipFileManager.readInventory;

public class Dealership {

    private String name;
    private String address;
    private String phone;

    static List<Vehicle> inventory = readInventory();

    public static Scanner scan = new Scanner(System.in);

    public Dealership(String name, String address, String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static List<Vehicle> getVehiclesByPrice(double min, double max){
        return null;
    }

    public static List<Vehicle> getVehiclesByMakeModel(String make, String model){
        return null;
    }

    public static List<Vehicle> getVehiclesByYear(int min, int max){
        return null;
    }

    public static List<Vehicle> getVehiclesByColor(String color){
        return null;
    }

    public static List<Vehicle> getVehiclesByMileage(int min, int max){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getOdometer()>=min && v.getOdometer()<=max){
                filteredVehicles.add(v);
            }
        }
        return printInventory(filteredVehicles);
    }

    public static List<Vehicle> getVehiclesByType(String vehicleType){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getVehicleType().equalsIgnoreCase(vehicleType)){
                filteredVehicles.add(v);
            }
        }
        return printInventory(filteredVehicles);
    }

    public static void getAllVehicles(){
        printInventory(readInventory());
    }

    public static void addVehicle(){

        System.out.println("-----------------------");
        System.out.println("Add Vehicle to Inventory");
        System.out.println("-----------------------");

        System.out.print("Enter Vehicle Identification Number: ");
        String vin = scan.nextLine();

        System.out.println("Enter Vehicle Year: ");
        int year = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter Vehicle Make: ");
        String make = scan.nextLine();

        System.out.println("Enter Vehicle Model: ");
        String model = scan.nextLine();

        System.out.println("Enter Vehicle Type: ");
        String type = scan.nextLine();

        System.out.println("Enter Vehicle Color: ");
        String color = scan.nextLine();

        System.out.println("Enter Vehicle Mileage: ");
        int mileage = scan.nextInt();

        System.out.println("Enter Vehicle Price: ");
        double price = scan.nextDouble();

         Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,mileage,price);

         //Add Vehicle to inventory object
         inventory.add(vehicle);

         //Call static method in DealershipFileManager class to write new vehicle to csv file(Inventory)
        DealershipFileManager.writeToCsv(vehicle);
    }
    public static void removeVehicle(Vehicle vehicle){}
}
