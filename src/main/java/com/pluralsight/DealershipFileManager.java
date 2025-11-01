package com.pluralsight;

import java.io.*;
import java.util.List;

public class DealershipFileManager {

    //Create constant variable for filepath
    private static final String filePath = "src/main/resources/Inventory";

    // instantiate Dealership class
    public static Dealership getDealership(){

        //Create dealership object and set to null for now;
        Dealership dealer = null;

        //try\catch with resources to read file
        try(BufferedReader buffRead = new BufferedReader(new FileReader(filePath))){

            //read first line of file to steal dealer header and store in array
                String dealerHeader = buffRead.readLine();
                String[] headerParts = dealerHeader.split("\\|");

                //assign dealer object with parsed information
                dealer = new Dealership(headerParts[0], headerParts[1], headerParts[2]);

            //Read vehicles in inventory
            String vehicleLines;
            while((vehicleLines = buffRead.readLine()) != null){
                String[] vehicleInfo = vehicleLines.split("\\|");

                try{
                    //parse vehicle data
                    String vin = vehicleInfo[0].trim();
                    int year = Integer.parseInt(vehicleInfo[1].trim());
                    String make = vehicleInfo[2].trim();
                    String model = vehicleInfo[3].trim();
                    String type = vehicleInfo[4].trim();
                    String color = vehicleInfo[5].trim();
                    int odometer = Integer.parseInt(vehicleInfo[6].trim());
                    double price = Double.parseDouble(vehicleInfo[7].trim());

                    //store vehicle data in vehicle object and add to dealer inventory
                    Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,odometer,price);
                    dealer.addVehicle(vehicle);

                }catch(Exception e){
                    System.out.println("Error reading");
                }
            }
        }catch(IOException e){
            System.out.println("Cant read file");
        }
        //return dealer
        return dealer;
    }

    // Method that will take in dealership object and write to inventory
    public static void saveDealership(Dealership dealership){

        //try\catch with resources for writing to files
        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath))){

            //Keep header when rewriting file
            buffWrite.write(String.format("%-25s| %-25s| %-25s", dealership.getName(),dealership.getAddress(),dealership.getPhone()));
            buffWrite.newLine();

            //Write vehicles to list named inventory
            List<Vehicle> inventory = dealership.getAllVehicles();

            //cycle through inventory and write each vehicle line by line to file
            for(Vehicle v : inventory){

                //make information in file look pretty
                String formatVehicle = String.format("%-10s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin().trim(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            }

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }
}
