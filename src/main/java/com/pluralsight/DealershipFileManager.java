package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.pluralsight.Dealership.inventory;

public class DealershipFileManager {

    public static void writeToCsv(Vehicle v){

        String filePath = "src/main/resources/Inventory";

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filePath,true))){

                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f",v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            System.out.println("Vehicle added successfully");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }

}
