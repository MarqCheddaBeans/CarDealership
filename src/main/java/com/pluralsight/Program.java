package com.pluralsight;

import java.util.Scanner;

import static com.pluralsight.Dealership.getAllVehicles;
import static com.pluralsight.Dealership.getVehiclesByType;
import static com.pluralsight.DealershipFileManager.printInventory;

public class Program {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Dealership dealer = new Dealership("BuyNowRegretLater","Dimension C-137", "1-877-FAST-AF");

        getAllVehicles();

        System.out.println();
        System.out.println();

        String type = "SUV";
        getVehiclesByType(type);

    }
}
