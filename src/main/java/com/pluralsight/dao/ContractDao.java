package com.pluralsight.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContractDao {

    DataSource ds;

    public ContractDao(DataSource ds) {
        this.ds = ds;
    }

    public void addSalesContract(){
        int rows = 0;

        try(
                Connection c = this.ds.getConnection();

                PreparedStatement q = c.prepareStatement("""
                        INSERT INTO Contracts
                        """)
                )
    }
    public void addLeaseContract(){

    }
}
