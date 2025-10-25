package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipFileManager {

    public static String filePath = "src/main/resources/Inventory";

    public static Dealership getDealership(){

        List<Vehicle> vehicle = new ArrayList<>();

        String name = "";
        String address = "";
        String phone = "";

        try(BufferedReader buffRead = new BufferedReader(new FileReader(filePath))){
            String line;

            //Read header
            if((line= buffRead.readLine()) !=null){
                String[] header = line.split("\\|");
                name = header[0].trim();
                address = header[1].trim();
                phone = header[2].trim();
            }

            //Read vehicles in inventory
            while((line = buffRead.readLine()) !=null){
                String[] vehicleInfo = line.split("\\|");

                try{

                    String vin = vehicleInfo[0].trim();
                    int year = Integer.parseInt(vehicleInfo[1].trim());
                    String make = vehicleInfo[2].trim();
                    String model = vehicleInfo[3].trim();
                    String type = vehicleInfo[4].trim();
                    String color = vehicleInfo[5].trim();
                    int odometer = Integer.parseInt(vehicleInfo[6].trim());
                    double price = Double.parseDouble(vehicleInfo[7].trim());

                    vehicle.add(new Vehicle(vin,year,make,model,type,color,odometer,price));
                }catch(Exception e){
                    System.out.println("Error reading");
                }
            }
        }catch(IOException e){
            System.out.println("Cant read file");
        }
        return new Dealership(name,address,phone, vehicle);
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

    public static void rewriteCsv(Dealership dealership){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath))){

            //Keep header when rewriting file
            buffWrite.write(String.format("%-25s| %-25s| %-25s", getDealership(),getDealership().getAddress(),getDealership().getPhone()));
            buffWrite.newLine();

            //Write vehicles
            for(Vehicle v : dealership.getInventory()){
                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin().trim(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            }
            System.out.println("Inventory updated successfully!");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }

    public static List<Vehicle> printInventory(Dealership dealership){

        List<Vehicle> vehicleList = dealership.getInventory();

        //check if list exists or if it has no elements
        if(vehicleList == null || vehicleList.isEmpty()){
            //if true, displays error message and exits method
            System.out.println("No vehicles found.");
            return vehicleList;
        }

        String header = String.format("%-25s| %-25s| %-25s",dealership.getName(), dealership.getAddress() , dealership.getPhone());
        System.out.println(header);

        for(Vehicle v : vehicleList){

            String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

            System.out.println(formatVehicle);
        }

        return vehicleList;
    }

    //Overload printInventory method to accept List object , and dealer, helps display filtered vehicles
    public static void printInventory(List<Vehicle> vehicleList, Dealership dealer) {
        // Print header
        System.out.printf("%-25s| %-25s| %-25s%n", dealer.getName(), dealer.getAddress(), dealer.getPhone());

        if(vehicleList == null || vehicleList.isEmpty()){
            System.out.println("No vehicles found.");
            return;
        }

        for(Vehicle v : vehicleList){
            System.out.printf("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10d| $%-10.2f%n",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                    v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
        }
    }

}
