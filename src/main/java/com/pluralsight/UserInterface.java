package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private static Dealership dealer;
    public static Scanner scan = new Scanner(System.in);

    //private constructor
    private UserInterface(){}

    public static void display(){

        init();

        int choice = -1;
        while(choice != 0){

            System.out.println("\n===================================");
            System.out.println("              Welcome              ");
            System.out.println("===================================");

            System.out.println("1 - Find vehicles within a price range");
            System.out.println("2 - Find vehicles by make/model");
            System.out.println("3 - Find vehicles by year range");
            System.out.println("4 - Find vehicles by color");
            System.out.println("5 - Find vehicles by mileage range");
            System.out.println("6 - Find vehicles by type");
            System.out.println("7 - List ALL vehicles");
            System.out.println("8 - Add a vehicle");
            System.out.println("9 - Remove a vehicle");
            System.out.println("0 - Quit");
            System.out.print("Please enter your choice: ");

            //make sure user inputs an int
            if (!scan.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number from 0–9.");
                //clear invalid input and restart loop
                scan.next();
                continue;
            }

            choice = scan.nextInt();
            //Hungry buffer
            scan.nextLine();

            switch(choice){

                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processByMakeModelRequest();
                    break;
                case 3:
                    processByYearRequest();
                    break;
                case 4:
                    processByColorRequest();
                    break;
                case 5:
                    processByMileageRequest();
                    break;
                case 6:
                    processByVehicleTypeRequest();
                    break;
                case 7:
                    processGetByAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 0:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Nope. try again.");
            }
        }
    }

    //Pulls dealer inventory from file
    private static void init(){
        dealer = DealershipFileManager.getDealership();
    }

    public static void processGetByPriceRequest(){
        System.out.print("Enter a minimum price: ");
        double min = scan.nextDouble();
        System.out.print("Enter a maximum price: ");
        double max = scan.nextDouble();
        scan.nextLine();

        List<Vehicle> filteredV = dealer.getVehiclesByPrice(min, max);
        printInventory(filteredV);
    }

    public static void processByMakeModelRequest(){
        System.out.print("Enter vehicle make: ");
        String make = scan.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scan.nextLine();

        List<Vehicle> filteredV = dealer.getVehiclesByMakeModel(make,model);
        printInventory(filteredV);
    }

    public static void processByYearRequest(){
        System.out.print("Enter minimum year: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scan.nextInt();

        List<Vehicle> filteredV = dealer.getVehiclesByYear(min,max);
        printInventory(filteredV);
    }

    public static void processByColorRequest(){
        System.out.print("Enter color: ");
        String color = scan.nextLine();

        List<Vehicle> filteredV = dealer.getVehiclesByColor(color);
        printInventory(filteredV);
    }

    public static void processByMileageRequest(){
        System.out.print("Enter minimum mileage: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scan.nextInt();
        scan.nextLine();

        List<Vehicle> filteredV = dealer.getVehiclesByMileage(min,max);
        printInventory(filteredV);
    }

    public static void processByVehicleTypeRequest(){
        System.out.print("Enter vehicle type (Sedan, Truck, SUV, Van): ");
        String type = scan.nextLine();

        List<Vehicle> filteredV = dealer.getVehiclesByType(type);
        printInventory(filteredV);
    }

    public static void processGetByAllVehiclesRequest(){

        List<Vehicle> inventory = dealer.getAllVehicles();
        printInventory(inventory);

    }

    public static void processAddVehicleRequest(){

        System.out.println("-----------------------");
        System.out.println("Add Vehicle to Inventory");
        System.out.println("-----------------------");

        System.out.print("Enter Vehicle Identification Number: ");
        String vin = scan.nextLine();

        System.out.print("Enter Vehicle Year: ");
        int year = scan.nextInt();
        scan.nextLine();

        System.out.print("Enter Vehicle Make: ");
        String make = scan.nextLine();

        System.out.print("Enter Vehicle Model: ");
        String model = scan.nextLine();

        System.out.print("Enter Vehicle Type: ");
        String type = scan.nextLine();

        System.out.print("Enter Vehicle Color: ");
        String color = scan.nextLine();

        System.out.print("Enter Vehicle Mileage: ");
        int mileage = scan.nextInt();

        System.out.print("Enter Vehicle Price: ");
        double price = scan.nextDouble();

        Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,mileage,price);
        dealer.addVehicle(vehicle);

        DealershipFileManager.saveDealership(dealer);

        System.out.println("Vehicle added successfully");
    }

    public static void processRemoveVehicleRequest(){

        processGetByAllVehiclesRequest();

        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scan.nextLine().trim();

        Vehicle vehicleToPackUp = null;

        for(Vehicle v : dealer.getAllVehicles()){
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
            dealer.removeVehicle(vehicleToPackUp);
            DealershipFileManager.saveDealership(dealer);
            System.out.println("Vehicle Removed Successfully.");
        }else{
            System.out.println("Removal cancelled;");
        }
    }

    public static void printInventory(List<Vehicle> vehicleList) {
        // Print header
        System.out.printf("%-25s| %-25s| %-25s%n", dealer.getName(), dealer.getAddress(), dealer.getPhone());

        if(vehicleList == null || vehicleList.isEmpty()){
            System.out.println("No vehicles found.");
            return;
        }

        for (Vehicle v : vehicleList) {
            System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n",v.getVin(), v.getYear(), v.getMake(), v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
            }
    }

}
