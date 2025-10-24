package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipFileManager {

    public static String filePath = "src/main/resources/Inventory";

    public static List<Vehicle> readInventory(){

        List<Vehicle> vehicle = new ArrayList<>();

        try(BufferedReader buffRead = new BufferedReader(new FileReader(filePath))){

            String line;

            while((line = buffRead.readLine()) !=null){

                String[] vehicleInfo = line.split("\\|");

                if(vehicleInfo[0].contains("Buy")){
                    continue;
                }

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
        return vehicle;
    }

    public static void addToCsv(Vehicle v){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath,true))){

                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            System.out.println("Vehicle added successfully");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }

    public static void rewriteCsv(List<Vehicle> vehicleList){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath))){
            for(Vehicle v : vehicleList){

                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            }
            System.out.println("Inventory updated successfully!");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }

    public static List<Vehicle> printInventory(List<Vehicle> vehicleList){

        //check if list exists or if it has no elements
        if(vehicleList == null || vehicleList.isEmpty()){
            //if true, displays error message and exits method
            System.out.println("No vehicles found.");
            return vehicleList;
        }
        String header = String.format("%-25s| %-25s| %-25s","BuyNowRegretLater", "Dimension C-137" , "1-877-FAST-AF");
        System.out.println(header);

        for(Vehicle v : vehicleList){

            String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

            System.out.println(formatVehicle);
        }

        return vehicleList;
    }

}
