package com.pluralsight.models;

import com.pluralsight.dao.VehicleDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dealership {

    //create properties
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    //The constructorrrr
    public Dealership(String name, String address, String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    //Da getters
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }

    //Method to filter vehicles by min and max price
    public static List<Vehicle> getVehiclesByPrice(BasicDataSource bds, double min, double max){
        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT
                            *
                        FROM
                            vehicles
                        WHERE
                            price BETWEEN ? AND ?
                        """)
        ){
            q.setDouble(1,min);
            q.setDouble(2,max);
            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }
            }
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
        return vehicles;
    }

    //Method to filter vehicles by make and model
    public static List<Vehicle> getVehiclesByMakeModel(BasicDataSource bds ,String make, String model){
        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT
                            *
                        FROM
                            vehicles
                        WHERE
                            make = ? OR model = ?
                        """)
        ){
            q.setString(1,make);
            q.setString(2, model);

            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String Vmake = r.getString("make");
                    String Vmodel = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,Vmake,Vmodel,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }
            }
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
        return vehicles;
    }

    //Method to filter vehicles by min and max year
    public static List<Vehicle> getVehiclesByYear(BasicDataSource bds, int min, int max){
        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT
                            *
                        FROM
                            vehicles
                        WHERE
                            year BETWEEN ? AND ?
                        """)
        ){
            q.setInt(1,min);
            q.setInt(2,max);

            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }
            }
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
        return vehicles;
    }

    //Methdod to filter vehicles by color
    public static List<Vehicle> getVehiclesByColor(BasicDataSource bds, String col){
        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT
                            *
                        FROM
                            vehicles
                        WHERE
                            color = ?
                        """)
                ){
            q.setString(1,col);
            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }
            }
        }catch(SQLException e){
            System.out.println("Error" + e);
        }
        return vehicles;
    }

    //Methdod to filter vehicles by min and max miles
    public static List<Vehicle> getVehiclesByMileage(BasicDataSource bds, int min, int max){

        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT
                            *
                        FROM
                            vehicles
                        WHERE
                            mileage BETWEEN ? AND ?
                        """);
                ){
            q.setInt(1,min);
            q.setInt(2, max);

            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }

            }

        }catch(SQLException e){
            System.out.println("What in tarnation" + e);
        }

        return vehicles;
    }

    //Methdod to filter vehicles by vehicle type
    public static List<Vehicle> getVehiclesByType(BasicDataSource bds, String type){

        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT 
                            *
                        FROM
                            vehicles
                        WHERE
                            vehicle_Type = ?                        
                        """)
                ){
            q.setString(1, type);
            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                    vehicles.add(v);
                }
            }

        }catch(SQLException e){
            System.out.println("MY LEG!");
        }

        return vehicles;
    }

    public static Vehicle getVehicleByVin(BasicDataSource bds, String s){

        Vehicle vehicle = null;

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("""
                        SELECT 
                            *
                        FROM
                            vehicles
                        WHERE
                            vin = ?                        
                        """)
        ){
            q.setString(1, s);
            try(ResultSet r = q.executeQuery()){
                while(r.next()){
                    String vin = r.getString("vin");
                    int year = r.getInt("year");
                    String make = r.getString("make");
                    String model = r.getString("model");
                    String vehicleType = r.getString("vehicle_type");
                    int mileage = r.getInt("mileage");
                    String color = r.getString("color");
                    double price = r.getDouble("price");

                    vehicle = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                }
            }

        }catch(SQLException e){
            System.out.println("MY LEG!");
        }

        return vehicle;
    }

    //Method to get all vehicles in inventory
    public static List<Vehicle> getAllVehicles(BasicDataSource bds){
        List<Vehicle> vehicles = new ArrayList<>();

        try(
                Connection c = bds.getConnection();
                PreparedStatement q = c.prepareStatement("SELECT * FROM vehicles WHERE sold = false")

                ){
            ResultSet r = q.executeQuery();

            while(r.next()){
                String vin = r.getString("vin");
                int year = r.getInt("year");
                String make = r.getString("make");
                String model = r.getString("model");
                String vehicleType = r.getString("vehicle_type");
                int mileage = r.getInt("mileage");
                String color = r.getString("color");
                double price = r.getDouble("price");

                Vehicle v = new Vehicle(vin,year,make,model,vehicleType,color,mileage,price);
                vehicles.add(v);
            }
        }catch(SQLException e){
            System.out.println("Oops" + e);
        }

        return vehicles;
    }

    //Method to add a vehicle to inventory
    public static void addVehicle(BasicDataSource bds ,Vehicle vehicle){
        VehicleDao vDao = new VehicleDao(bds);

        vDao.addVehicle(vehicle);

    }

    @Override
    public String toString(){
        String format = String.format("%-25s| %-25s| %-25s", this.name, this.address, this.phone);

        return format;
    }

}
