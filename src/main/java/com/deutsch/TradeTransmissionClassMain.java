package com.deutsch;
import java.text.*;
import java.util.*;
import java.lang.*;

public class TradeTransmissionClassMain {

    public static void main(String[] args) throws Exception {
        TradeService tsObj = new TradeService();
        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

        //Adding Trade T1
        Date maturityDate = sd.parse("10/05/2021");
        TradeModel t1 = new TradeModel("T1", 1, "CP-1", "B1", maturityDate, todaysDate, 'N');
        tsObj.addTrade(t1);

        //Adding Trade T2
        maturityDate = sd.parse("20/05/2021");
        TradeModel t2 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
        tsObj.addTrade(t2);

        //Changing Trade T2
        TradeModel t4 = new TradeModel("T3", 5, "CP-4", "B1", maturityDate, todaysDate, 'N');
        tsObj.addTrade(t4);


        //Adding Trade T3
        maturityDate = sd.parse("20/05/2021");
        TradeModel t3 = new TradeModel("T4", 5, "CP-3", "B2", maturityDate, todaysDate, 'N');
        tsObj.addTrade(t3);

        //Display all Trade
        System.out.println("\n\n");
        System.out.println("Displaying total number of Trade in the list");
        tsObj.printTrade();
        System.out.println("\n\n");

        //Checking for all Expired Flag
        System.out.println("Checking for Expired Flag");
        maturityDate = sd.parse("20/05/2020");
        TradeModel t6 = new TradeModel("T2", 2, "CP-2", "B1", maturityDate, todaysDate, 'N');
        tsObj.allTrade.replace("T2", t6);

        maturityDate = sd.parse("20/05/2020");
        TradeModel t7 = new TradeModel("T4", 5, "CP-3", "B2", maturityDate, todaysDate, 'N');
        tsObj.allTrade.replace("T4", t7);
        tsObj.checkExpiredDates();
        tsObj.printTrade();
    }
}
