package com.pluralsight;

import java.io.*;
import java.util.List;

public class DealershipFileManager {

    private static final String filePath = "src/main/resources/Inventory";

    public static Dealership getDealership(){

        Dealership dealer = null;

        try(BufferedReader buffRead = new BufferedReader(new FileReader(filePath))){

                String dealerHeader = buffRead.readLine();

                String[] headerParts = dealerHeader.split("\\|");

                dealer = new Dealership(headerParts[0], headerParts[1], headerParts[2]);

            //Read vehicles in inventory
            String vehicleLines;
            while((vehicleLines = buffRead.readLine()) != null){
                String[] vehicleInfo = vehicleLines.split("\\|");

                try{
                    String vin = vehicleInfo[0].trim();
                    int year = Integer.parseInt(vehicleInfo[1].trim());
                    String make = vehicleInfo[2].trim();
                    String model = vehicleInfo[3].trim();
                    String type = vehicleInfo[4].trim();
                    String color = vehicleInfo[5].trim();
                    int odometer = Integer.parseInt(vehicleInfo[6].trim());
                    double price = Double.parseDouble(vehicleInfo[7].trim());

                    Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,odometer,price);
                    dealer.addVehicle(vehicle);

                }catch(NullPointerException e){
                    System.out.println("Error reading");
                }
            }
        }catch(IOException e){
            System.out.println("Cant read file");
        }
        return dealer;
    }

    public static void addVehicleToFile(Vehicle v){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath,true))){

                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin().trim(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
                System.out.println("Vehicle added successfully");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }

    public static void saveDealership(Dealership dealership){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath))){

            //Keep header when rewriting file
            buffWrite.write(String.format("%-25s| %-25s| %-25s", dealership.getName(),dealership.getAddress(),dealership.getPhone()));
            buffWrite.newLine();

            //Write vehicles
            List<Vehicle> inventory = dealership.getAllVehicles();
            for(Vehicle v : inventory){
                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin().trim(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            }
            System.out.println("Inventory updated successfully!");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }
}
