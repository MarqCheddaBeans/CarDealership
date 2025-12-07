package com.pluralsight.ui;

import com.pluralsight.dao.ContractDao;
import com.pluralsight.dao.VehicleDao;
import com.pluralsight.models.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    public static Scanner scan = new Scanner(System.in);
    //Empty constructor
    public UserInterface(){}

    //This method here is the stage of sorts
    public void display(String user, String pass){

        //Call init method to initialize dealership
        BasicDataSource bds = init(user, pass);

        //Keep menu looping until user wants to exit
        int choice = -1;
        while(choice != 99){

            //Menu screens
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
            System.out.println("0 - Buy/Lease a vehicle");
            System.out.println("99 - Quit");
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

            //Switch case to deal with users input/ navigation throughout the menu
            switch(choice){

                case 1:
                    processGetByPriceRequest(bds);
                    break;
                case 2:
                    processByMakeModelRequest(bds);
                    break;
                case 3:
                    processByYearRequest(bds);
                    break;
                case 4:
                    processByColorRequest(bds);
                    break;
                case 5:
                    processByMileageRequest(bds);
                    break;
                case 6:
                    processByVehicleTypeRequest(bds);
                    break;
                case 7:
                    processGetByAllVehiclesRequest(bds);
                    break;
                case 8:
                    processAddVehicleRequest(bds);
                    break;
                case 9:
                    processRemoveVehicleRequest(bds);
                    break;
                case 0:
                    processBuyLeaseVehicleRequest(bds);
                    break;
                case 99:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Nope. try again.");
            }
        }
    }

    //Configures BasicDataSource to connect to our database, initializes app
    private BasicDataSource init(String user, String pass){
        BasicDataSource bds = new BasicDataSource();

        bds.setUrl("jdbc:mysql://localhost:3306/cardealership");
        bds.setUsername(user);
        bds.setPassword(pass);

        //Automatically open 5 connections in pool
        bds.setInitialSize(5);
        //Max 20 connections open at a time
        bds.setMaxTotal(20);
        //Max 10 idle connections allowed open
        bds.setMaxIdle(10);

        return bds;
    }

    //Method that allows user to Buy or Lease vehicles
    public void processBuyLeaseVehicleRequest(BasicDataSource bds){


        ContractDao cDao = new ContractDao(bds);

        VehicleDao vDao = new VehicleDao(bds);
        //Get live date for contract date and format to preference
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String date = LocalDate.now().format(dateFormat);

        //create contract object and set to null for now
        Contract buyLease = null;

        //pretty header incase user forgot what option they chose
        System.out.println("\n===================================");
        System.out.println("              Buy/Lease              ");
        System.out.println("===================================");

        //display all vehicles for user to choose and prompt for info
        processGetByAllVehiclesRequest(bds);

        System.out.print("\nEnter vin of vehicle you are interested in: ");
        String vin = scan.nextLine().trim();

        //Create vehicle object set to null for now, will be for the chosen vehicle
        Vehicle interestedVehicle = Dealership.getVehicleByVin(bds, vin);

        //check if any vehicle was found
        if(interestedVehicle == null){
            //tell user they vehicle nowhere to be seen
            System.out.println("Vehicle with vin " + vin + " was not found.");
            return;
        }

        //display found vehicle
        System.out.println("Vehicle found!\n");
        System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n", interestedVehicle.getVin(),interestedVehicle.getYear(),interestedVehicle.getMake(),interestedVehicle.getModel(),interestedVehicle.getVehicleType(),interestedVehicle.getColor(),interestedVehicle.getMileage(),interestedVehicle.getPrice());

        //Ask user for confirmation
        System.out.print("Are you sure you would like to purchase this vehicle? (Y/N): ");
        String input = scan.nextLine().trim();

        //check if user wants to continue
        if(input.equalsIgnoreCase("Y")){

            //ask if user wants to buy or lease
            System.out.print("Would you like to (B)uy or (L)ease the vehicle? ");
            String choice = scan.nextLine();

            // Check if user chose to buy
            if(choice.equalsIgnoreCase("B")){

                //Headerrr
                System.out.println("\n===================================");
                System.out.println("                 BUY               ");
                System.out.println("===================================");

                //Ask user if they want to finance
                System.out.print("\nWould you like to finance? (Y/N): ");
                String financeInput = scan.nextLine();

                //deeper if statement! Check if user wants to finance
                if(financeInput.equalsIgnoreCase("N")){

                    //prompt user for info
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();

                    //assign contract as salescontract and add information according
                    buyLease = new SalesContract(date, name, email, interestedVehicle,false);

                    //call ContractDAO to add sale to our contract database
                    cDao.addSalesContract((SalesContract) buyLease);

                    //Update vehicle sold status and print confirmation
                    interestedVehicle.setSold(true);
                    System.out.println("Purchase successful!");

                    //check if user financed
                } else if(financeInput.equalsIgnoreCase("Y")){

                    //prompt user for info
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();

                    //assign contract as salescontract and add relevant information that was collected
                    buyLease = new SalesContract(date, name, email, interestedVehicle,true);

                    //call ContractDAO to add sale to our contract database
                    cDao.addSalesContract((SalesContract) buyLease);

                    //update vehicle sold status
                    interestedVehicle.setSold(true);
                    System.out.println("Purchase successful!");
                }
            }else if(choice.equalsIgnoreCase("L")){

                //Header you already know
                System.out.println("\n===================================");
                System.out.println("                 LEASE             ");
                System.out.println("===================================");

                //ask user if they wanna finance
                System.out.print("\nWould you like to finance? (Y/N): ");
                String financeInput = scan.nextLine();


                //check if user financed
                if(financeInput.equalsIgnoreCase("N")){

                    //prompt user for information
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();

                    //assign contract as leasecontract and add relevant data
                    buyLease = new LeaseContract(date, name, email, interestedVehicle,false);

                    //call ContractDAO to add lease to our contract database
                    cDao.addLeaseContract((LeaseContract) buyLease);

                    //Update vehicle sold status and print confirmation
                    interestedVehicle.setSold(true);
                    System.out.println("Purchase successful!");

                    //ask if user financed
                }else if(financeInput.equalsIgnoreCase("Y")){

                    //prompt user for info
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();

                    //assign contract as leasecontract and add relevant data
                    buyLease = new LeaseContract(date, name, email, interestedVehicle,true);

                    //call ContractDAO to add lease to our contract database
                    cDao.addLeaseContract((LeaseContract) buyLease);

                    //Update vehicle sold status and print confirmation
                    interestedVehicle.setSold(true);
                    System.out.println("Purchase successful!");
                }
            }

        }else{
            //cancel purchase if user indecisive
            System.out.println("Purchase cancelled.");
        }

    }

    //Method that allows user to remove vehicles
    public void processRemoveVehicleRequest(BasicDataSource bds){

        VehicleDao vDao = new VehicleDao(bds);

        //Display inventory for user to choose
        processGetByAllVehiclesRequest(bds);

        //prompt user for info
        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scan.nextLine().trim();

        //create Vehicle object for vehicle to pack up, set as null for now
        Vehicle vehicleToPackUp = null;

        vehicleToPackUp = Dealership.getVehicleByVin(bds, vin);

        //if no match found
        if(vehicleToPackUp == null){
            System.out.println("Vehicle with VIN " + vin + " not found.");
            return;
        }

        //Display found vehicle
        System.out.println("\n Vehicle found: ");
        System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n", vehicleToPackUp.getVin(),vehicleToPackUp.getYear(),vehicleToPackUp.getMake(),vehicleToPackUp.getModel(),vehicleToPackUp.getVehicleType(),vehicleToPackUp.getColor(),vehicleToPackUp.getMileage(),vehicleToPackUp.getPrice());

        //Confirm removal
        System.out.println("\nAre you sure you want to remove this vehicle? (Y/N)");
        String confirm = scan.nextLine().trim();

        //check if user confirmed removal
        if(confirm.equalsIgnoreCase("Y")){

            //if confirmed remove vehicle and update dealer inventory
            vDao.removeVehicle(vehicleToPackUp);
            System.out.println("Vehicle Removed Successfully.");
        }else{
            System.out.println("Removal cancelled;");
        }
    }

    //Method that allows user to filter vehicles by price
    public void processGetByPriceRequest(BasicDataSource bds){

        //prompt user for info
        System.out.print("Enter a minimum price: ");
        double min = scan.nextDouble();
        System.out.print("Enter a maximum price: ");
        double max = scan.nextDouble();
        scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = Dealership.getVehiclesByPrice(bds, min, max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by make and model
    public void processByMakeModelRequest(BasicDataSource bds){

        //prompt user for info
        System.out.print("Enter vehicle make: ");
        String make = scan.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = Dealership.getVehiclesByMakeModel(bds,make,model);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by year
    public void processByYearRequest(BasicDataSource bds){

        //prompt user for info
        System.out.print("Enter minimum year: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scan.nextInt();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = Dealership.getVehiclesByYear(bds,min,max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by color
    public void processByColorRequest(BasicDataSource bds){

        //prompt user for info
        System.out.print("Enter color: ");
        String color = scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = Dealership.getVehiclesByColor(bds,color);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by mileage
    public void processByMileageRequest(BasicDataSource bds){

        //prompt user for info
        System.out.print("Enter minimum mileage: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scan.nextInt();
        scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = Dealership.getVehiclesByMileage(bds,min,max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by type
    public void processByVehicleTypeRequest(BasicDataSource bds){
        //prompt user for info
        System.out.print("Enter vehicle type (Sedan, Truck, SUV, Van): ");
        String type = scan.nextLine();

        //call method to filter vehicles
        List<Vehicle> filteredV = Dealership.getVehiclesByType(bds,type);
        printInventory(filteredV);
    }

    //Method that displays all of the vehicles in inventory
    public void processGetByAllVehiclesRequest(BasicDataSource bds){

        //Store all vehicles in list named inventory and printing to screen
        List<Vehicle> inventory = Dealership.getAllVehicles(bds);
        printInventory(inventory);

    }

    //Method that allows user to add vehicles to inventory
    public void processAddVehicleRequest(BasicDataSource bds){

        //Little header to let user know whats going on
        System.out.println("-----------------------");
        System.out.println("Add Vehicle to Inventory");
        System.out.println("-----------------------");

        //Prompt user for vehicle information
        System.out.print("Enter Vehicle Identification Number: ");
        String vin = scan.nextLine();

        System.out.print("Enter Vehicle Year: ");
        int year = scan.nextInt();
        //hungry buffer
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

        //Create new vehicle object and store user inputted info
        Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,mileage,price);

        //add vehicel to database
        Dealership.addVehicle(bds, vehicle);

        System.out.println("Vehicle added successfully");
    }

    //Helper method to display vehicles to the screen
    public void printInventory(List<Vehicle> vehicleList) {

        //check if vehicles list is empty, return if empty
        if(vehicleList == null || vehicleList.isEmpty()){
            System.out.println("No vehicles found.");
            return;
        }

        System.out.printf("%-16s | %-4s | %-14s | %-14s | %-9s | %-7s | %-9s | %-7s%n",
                "VIN", "Year", "Make", "Model", "Type", "Color", "Mileage", "Price");

        //cycles through list of vehicles and prints them out
        for (Vehicle v : vehicleList) {
            System.out.printf("%-10s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n",v.getVin(), v.getYear(), v.getMake(), v.getModel(),v.getVehicleType(), v.getColor(), v.getMileage(), v.getPrice());
            }
    }

}
