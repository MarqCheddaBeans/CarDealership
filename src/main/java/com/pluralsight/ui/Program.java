package com.pluralsight.ui;

public class Program {

    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("Need user and pass");
        }else{

            // Create UI object
            UserInterface ui = new UserInterface();

            //Run the app
            ui.display(args[0], args[1]);
        }


    }
}
