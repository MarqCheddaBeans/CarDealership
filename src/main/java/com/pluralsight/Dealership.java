package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.DealershipFileManager.*;

public class Dealership {

    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory;

    public static Scanner scan = new Scanner(System.in);

    public Dealership(String name, String address, String phone, List<Vehicle> inventory){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = inventory;
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

    public List<Vehicle> getInventory(){
        return inventory;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getPrice()>= min && v.getPrice()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getMake().equalsIgnoreCase(make) || v.getModel().contains(model)){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for (Vehicle v : inventory){
            if(v.getYear()>=min && v.getYear()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getVehiclesByColor(String color){
        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getColor().equalsIgnoreCase(color)){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getOdometer()>=min && v.getOdometer()<=max){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getVehiclesByType(String vehicleType){

        List<Vehicle> filteredVehicles = new ArrayList<>();

        for(Vehicle v : inventory){
            if(v.getVehicleType().equalsIgnoreCase(vehicleType)){
                filteredVehicles.add(v);
            }
        }
        return filteredVehicles;
    }

    public List<Vehicle> getAllVehicles(){
       return new ArrayList<>(inventory);
    }

    public void displayAllVehicles(){
        if(inventory == null || inventory.isEmpty()){
            System.out.println("This inventory empty");
        }else {
            DealershipFileManager.printInventory(this);
        }
    }

    public  void addVehicle(){

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
        DealershipFileManager.addVehicleToFile(vehicle);

        System.out.println("Vehicle added successfully");
    }

    public void removeVehicle(){

        //Check for leftover line to munch on
        if(scan.hasNextLine()){
            scan.nextLine();
        }

        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scan.nextLine().trim();

        Vehicle vehicleToPackUp = null;

        for(Vehicle v : inventory){
            if(v.getVin().trim().equalsIgnoreCase(vin)){
                vehicleToPackUp = v;
                break;
            }
        }

        //if no match found
        if(vehicleToPackUp == null){
            System.out.println("Vehicle with VIN " + vin + " not found.");
            return;
        }

        //Display found vehicle
        System.out.println("\n Vehicle found: ");
        System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n", vehicleToPackUp.getVin(),vehicleToPackUp.getYear(),vehicleToPackUp.getMake(),vehicleToPackUp.getModel(),vehicleToPackUp.getVehicleType(),vehicleToPackUp.getColor(),vehicleToPackUp.getOdometer(),vehicleToPackUp.getPrice());

        //Confirm removal
        System.out.println("\nAre you sure you want to remove this vehicle? (Y/N)");
        String confirm = scan.nextLine().trim();

        if(confirm.equalsIgnoreCase("Y")){
            inventory.remove(vehicleToPackUp);
            System.out.println("Vehicle Removed Successfully.");

            //Save updated inventory back to file
            DealershipFileManager.rewriteCsv(this);
        }else{
            System.out.println("Removal cancelled;");
        }
    }
}
