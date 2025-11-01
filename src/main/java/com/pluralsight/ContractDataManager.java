package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractDataManager {

    //Constant for filepath
    private final String FILE_PATH = "src/main/resources/Contracts";

    //method to save contracts, writing to file
public void saveContract(Contract contract){

    //try\catch with resources to append to file
    try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(FILE_PATH,true))){

        //check if contract is Lease or Sales
        if(contract instanceof SalesContract s){

            //format and append accordingly, format to look pretty
            String sale = String.format("\n%-6s | %-11s | %-30s | %-30s | %-8s | %-5d | %-12s | %-12s | %-10s | %-10s | %-8dmi | $%-10.2f | $%-8.2f | $%-8.2f | $%-8.2f | %-8B | $%-8.2f | $%-8.2f", s.getContractType(), s.getDate(), s.getCustomerName(), s.getCustomerEmail(), s.vehicleSold.getVin(), s.vehicleSold.getYear(), s.vehicleSold.getMake(), s.vehicleSold.getModel(), s.vehicleSold.getVehicleType(), s.vehicleSold.getColor(), s.vehicleSold.getOdometer(),s.vehicleSold.getPrice(), s.getSALES_TAX(), s.getRECORDING_FEE(), s.getProcessingFee(), s.isFinanced(),s.getTotalPrice(), s.getMonthlyPayment());
            buffWrite.write(sale);

        } else if(contract instanceof LeaseContract l){

            String lease = String.format("\n%-6s | %-11s | %-30s | %-30s | %-8s | %-5d | %-12s | %-12s | %-10s | %-10s | %-8dmi | $%-10.2f | $%-8.2f | %-8B | $%-8.2f | $%-8.2f", l.getContractType(), l.getDate(), l.getCustomerName(), l.getCustomerEmail(), l.vehicleSold.getVin(), l.vehicleSold.getYear(), l.vehicleSold.getMake(), l.vehicleSold.getModel(), l.vehicleSold.getVehicleType(), l.vehicleSold.getColor(), l.vehicleSold.getOdometer(),l.vehicleSold.getPrice(), l.getLeaseFee(), l.isFinanced() ,l.getTotalPrice(), l.getMonthlyPayment());
            buffWrite.write(lease);

        } else{
            System.out.print("No bueno.");
        }
    }catch(IOException e){
        System.out.println("What did you do?!?");
    }

    }

}
