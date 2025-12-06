package com.pluralsight.dao;

import com.pluralsight.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleDao {

    private DataSource ds;

    public VehicleDao(DataSource ds) {
        this.ds = ds;
    }

    public int addVehicle(Vehicle vehicle){
        int rows = 0;

        try(
                Connection c = this.ds.getConnection();

                PreparedStatement q = c.prepareStatement("""
                        INSERT INTO vehicles (vin, make, model, year, color, mileage, price, vehicle_type
                        VALUES(?, ?, ?, ?, ?, ?, ?, ?)
                        """)
                ){
            q.setString(1, vehicle.getVin());
            q.setString(2, vehicle.getMake());
            q.setString(3, vehicle.getModel());
            q.setInt(4,vehicle.getYear());
            q.setString(5, vehicle.getColor());
            q.setInt(6,vehicle.getMileage());
            q.setDouble(7, vehicle.getPrice());
            q.setString(8, vehicle.getVehicleType());

            rows = q.executeUpdate();

        }catch(SQLException e){
            System.out.println(e);
        }

        return rows;
    }
    public int removeVehicle(Vehicle v) {
        int rows = 0;
        try(
                Connection c = this.ds.getConnection();

                PreparedStatement q = c.prepareStatement("""
                        DELETE FROM vehicles WHERE vin = ?
                        """);
        ){

            q.setString(1,v.getVin());

            rows = q.executeUpdate();

        }catch(SQLException e){
            System.out.println("Oops");
        }
        return rows;
    }
}

