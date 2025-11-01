package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    //Declare Dealership object named dealer and Static scanner
    private Dealership dealer;
    public static Scanner scan = new Scanner(System.in);

    //Empty constructor
    public UserInterface(){}

    //This method here is the stage of sorts
    public void display(){

        //Call init method to initialize dealership
        init();

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
                    processBuyLeaseVehicleRequest();
                    break;
                case 99:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Nope. try again.");
            }
        }
    }

    //Pulls dealer inventory from file, initializes app
    private void init(){
        dealer = DealershipFileManager.getDealership();
    }

    //Method that allows user to Buy or Lease vehicles
    public void processBuyLeaseVehicleRequest(){

        //Create instance of ContractDataManager
        ContractDataManager cdm = new ContractDataManager();
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
        processGetByAllVehiclesRequest();

        System.out.print("\nEnter vin of vehicle you are interested in: ");
        String vin = scan.nextLine().trim();

        //Create vehicle object set to null for now, will be for the chosen vehicle
        Vehicle interestedVehicle = null;

        //Cycle through vehicles
        for(Vehicle v: dealer.getAllVehicles()){
            //check for vehicles with same vin as user input
            if(v.getVin().equalsIgnoreCase(vin)){
                //if found, store in our vehicle object created before and break loop
                interestedVehicle = v;
                break;
            }
        }

        //check if any vehicle was found
        if(interestedVehicle == null){
            //tell user they vehicle nowhere to be seen
            System.out.println("Vehicle with vin " + vin + " was not found.");
            return;
        }

        //display found vehicle
        System.out.println("Vehicle found!\n");
        System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n", interestedVehicle.getVin(),interestedVehicle.getYear(),interestedVehicle.getMake(),interestedVehicle.getModel(),interestedVehicle.getVehicleType(),interestedVehicle.getColor(),interestedVehicle.getOdometer(),interestedVehicle.getPrice());

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

                    //assign contract to salescontract and add information according
                    buyLease = new SalesContract(date, name, email, interestedVehicle,false);

                    //call ContractDataManager to save this contract and print confirmation
                    cdm.saveContract(buyLease);

                    //Remove vehicle and update inventory
                    dealer.removeVehicle(interestedVehicle);
                    DealershipFileManager.saveDealership(dealer);
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

                    //call ContractDataManager to save this contract and print confirmation
                    cdm.saveContract(buyLease);

                    //Remove vehicle and update inventory
                    dealer.removeVehicle(interestedVehicle);
                    DealershipFileManager.saveDealership(dealer);
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

                    //call ContractDataManager to save this contract and print confirmation
                    cdm.saveContract(buyLease);

                    //Remove vehicle and update inventory
                    dealer.removeVehicle(interestedVehicle);
                    DealershipFileManager.saveDealership(dealer);
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

                    //call ContractDataManager to save this contract and print confirmation
                    cdm.saveContract(buyLease);

                    //Remove vehicle and update inventory
                    dealer.removeVehicle(interestedVehicle);
                    DealershipFileManager.saveDealership(dealer);
                    System.out.println("Purchase successful!");
                }
            }

        }else{
            //cancel purchase if user indecisive
            System.out.println("Purchase cancelled.");
        }

    }

    //Method that allows user to remove vehicles
    public void processRemoveVehicleRequest(){

        //Display inventory for user to choose
        processGetByAllVehiclesRequest();

        //prompt user for info
        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scan.nextLine().trim();

        //create Vehicle object for vehicle to pack up, set as null for now
        Vehicle vehicleToPackUp = null;

        //cycle through inventory
        for(Vehicle v : dealer.getAllVehicles()){
            //check for vehicle with matching vin as user input
            if(v.getVin().trim().equalsIgnoreCase(vin)){
                //if found add vehicle to our vehicle object that was set to null and leave loop
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

        //check if user confirmed removal
        if(confirm.equalsIgnoreCase("Y")){

            //if confirmed remove vehicle and update dealer inventory
            dealer.removeVehicle(vehicleToPackUp);
            DealershipFileManager.saveDealership(dealer);
            System.out.println("Vehicle Removed Successfully.");
        }else{
            System.out.println("Removal cancelled;");
        }
    }

    //Method that allows user to filter vehicles by price
    public void processGetByPriceRequest(){

        //prompt user for info
        System.out.print("Enter a minimum price: ");
        double min = scan.nextDouble();
        System.out.print("Enter a maximum price: ");
        double max = scan.nextDouble();
        scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = dealer.getVehiclesByPrice(min, max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by make and model
    public void processByMakeModelRequest(){

        //prompt user for info
        System.out.print("Enter vehicle make: ");
        String make = scan.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = dealer.getVehiclesByMakeModel(make,model);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by year
    public void processByYearRequest(){

        //prompt user for info
        System.out.print("Enter minimum year: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scan.nextInt();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = dealer.getVehiclesByYear(min,max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by color
    public void processByColorRequest(){

        //prompt user for info
        System.out.print("Enter color: ");
        String color = scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = dealer.getVehiclesByColor(color);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by mileage
    public void processByMileageRequest(){

        //prompt user for info
        System.out.print("Enter minimum mileage: ");
        int min = scan.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scan.nextInt();
        scan.nextLine();

        //call method to filter vehicles and display to user
        List<Vehicle> filteredV = dealer.getVehiclesByMileage(min,max);
        printInventory(filteredV);
    }

    //Method that allows user to filter vehicels by type
    public void processByVehicleTypeRequest(){
        //prompt user for info
        System.out.print("Enter vehicle type (Sedan, Truck, SUV, Van): ");
        String type = scan.nextLine();

        //call method to filter vehicles
        List<Vehicle> filteredV = dealer.getVehiclesByType(type);
        printInventory(filteredV);
    }

    //Method that displays all of the vehicles in inventory
    public void processGetByAllVehiclesRequest(){

        //Store all vehicles in list named inventory and printing to screen
        List<Vehicle> inventory = dealer.getAllVehicles();
        printInventory(inventory);

    }

    //Method that allows user to add vehicles to inventory
    public void processAddVehicleRequest(){

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

        //add vehicle to inventory
        dealer.addVehicle(vehicle);

        //update dealer inventory and display confirmation
        DealershipFileManager.saveDealership(dealer);
        System.out.println("Vehicle added successfully");
    }

    //Helper method to display vehicles to the screen
    public void printInventory(List<Vehicle> vehicleList) {
        // Print header
        System.out.printf("%-25s| %-25s| %-25s%n", dealer.getName(), dealer.getAddress(), dealer.getPhone());

        //check if vehicles list is empty, return if empty
        if(vehicleList == null || vehicleList.isEmpty()){
            System.out.println("No vehicles found.");
            return;
        }

        //cycles through list of vehicles and prints them out
        for (Vehicle v : vehicleList) {
            System.out.printf("%-10s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n",v.getVin(), v.getYear(), v.getMake(), v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
            }
    }

}
