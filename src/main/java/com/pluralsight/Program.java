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

        removeVehicle();










    }
}
