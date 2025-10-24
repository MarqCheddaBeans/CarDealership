package com.pluralsight;

import java.util.Scanner;

import static com.pluralsight.Dealership.*;
import static com.pluralsight.DealershipFileManager.printInventory;
import static com.pluralsight.DealershipFileManager.readInventory;

public class Program {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        Dealership dealer = new Dealership("BuyNowRegretLater","Dimension C-137", "1-877-FAST-AF");

        printInventory(readInventory());

        System.out.println();
        System.out.println();

        int minY = 2000;
        int maxY = 2013;
        getVehiclesByYear(minY, maxY);

        System.out.println();
        System.out.println();

        String make = "";
        String model = "Civic";
        getVehiclesByMakeModel(make,model);











    }
}
