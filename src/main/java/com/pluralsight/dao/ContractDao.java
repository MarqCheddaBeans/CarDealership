package com.pluralsight.dao;

import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContractDao {

    DataSource ds;

    public ContractDao(DataSource ds) {
        this.ds = ds;
    }

    public void addSalesContract(SalesContract s){
        int rows = 0;

        try(
                Connection c = this.ds.getConnection();
        ){

                try(PreparedStatement q = c.prepareStatement("""
                        INSERT INTO Contracts (contractType, ContractDate, CustomerName, CustomerEmail, vehicleVin,
                        SalesTax, RecordingFee, ProcessingFee, financed, Price, MonthlyPayment)
                      
                        Values (?,?,?,?,?,?,?,?,?,?,?);
                        
                        UPDATE vehicles
                                    SET sold = TRUE
                                    WHERE vin = ?;
                        """) ) {
                    q.setString(1, s.getContractType());
                    q.setString(2, s.getDate());
                    q.setString(3, s.getCustomerName());
                    q.setString(4, s.getCustomerEmail());
                    q.setString(5, s.getVehicleSold().getVin());
                    q.setDouble(6, s.getSALES_TAX());
                    q.setDouble(7, s.getRECORDING_FEE());
                    q.setDouble(8, s.getProcessingFee());
                    q.setBoolean(9, s.isFinanced());
                    q.setDouble(10, s.getTotalPrice());
                    q.setDouble(11, s.getMonthlyPayment());
                    q.setString(12, s.getVehicleSold().getVin());

                    rows = q.executeUpdate();
                }

            try(
                    PreparedStatement update = c.prepareStatement("""
                            UPDATE vehicles
                                    SET sold = TRUE
                                    WHERE vin = ?;
                            """)
            ){
                update.setString(1, s.getVehicleSold().getVin());
                update.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println("WTHelly" + e);
        }
    }
    public void addLeaseContract(LeaseContract l){
        int rows = 0;

        try(
                Connection c = this.ds.getConnection();
        ){
            try(
                PreparedStatement q = c.prepareStatement("""
                        INSERT INTO Contracts (contractType, ContractDate, CustomerName, CustomerEmail, vehicleVin,
                        LeaseFee, financed, Price, MonthlyPayment)
                      
                        Values (?,?,?,?,?,?,?,?,?);
                        """)
        ) {
                q.setString(1, l.getContractType());
                q.setString(2, l.getDate());
                q.setString(3, l.getCustomerName());
                q.setString(4, l.getCustomerEmail());
                q.setString(5, l.getVehicleSold().getVin());
                q.setDouble(6, l.getLeaseFee());
                q.setBoolean(7, l.isFinanced());
                q.setDouble(8, l.getTotalPrice());
                q.setDouble(9, l.getMonthlyPayment());
                rows = q.executeUpdate();
            }
            try(
                    PreparedStatement update = c.prepareStatement("""
                            UPDATE vehicles
                                    SET sold = TRUE
                                    WHERE vin = ?;
                            """)
                    ){
                update.setString(1, l.getVehicleSold().getVin());
                update.executeUpdate();
            }
            System.out.println("Contract Confirmed!");

        }catch(SQLException e){
            System.out.println("WTHelly");
        }
    }
}
