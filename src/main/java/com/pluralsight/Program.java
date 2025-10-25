package com.pluralsight;

import java.util.List;
import java.util.Scanner;

import static com.pluralsight.DealershipFileManager.getDealership;
import static com.pluralsight.DealershipFileManager.printInventory;

public class Program {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

            // Load dealership from file
            Dealership dealer = DealershipFileManager.getDealership();
            System.out.println("Dealership Confirmed: " + dealer.getName());

            Scanner scan = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n===== DEALERSHIP MENU =====");
                System.out.println("1. List all vehicles");
                System.out.println("2. Filter by price");
                System.out.println("3. Add a vehicle");
                System.out.println("4. Remove a vehicle");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                choice = scan.nextInt();
                scan.nextLine();  // consume newline

                switch (choice) {
                    case 1 -> {
                        // Display all vehicles
                        DealershipFileManager.printInventory(dealer);
                    }
                    case 2 -> {
                        // Filter by price
                        System.out.print("Enter min price: ");
                        double min = scan.nextDouble();
                        System.out.print("Enter max price: ");
                        double max = scan.nextDouble();
                        scan.nextLine(); // consume newline

                        List<Vehicle> filtered = dealer.getVehiclesByPrice(min, max);

                        // Print filtered list with dealership header
                        DealershipFileManager.printInventory(filtered, dealer);
                    }
                    case 3 -> dealer.addVehicle();
                    case 4 -> dealer.removeVehicle();
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice, try again.");
                }

            } while (choice != 0);

            scan.close();
        }
    }
