package com.deutsch;
import java.util.*;
import com.deutsch.TradeModel;
import java.lang.*;

public class TradeService {
    HashMap<String, TradeModel> allTrade = new HashMap<String, TradeModel>();


    //check if no trade Exists
    public boolean checkIfTradeEmpty() {
        return allTrade.isEmpty();
    }

    //Check if the lower version is being received by the store it will reject the trade and throw an exception.
    //If the version is same it will override the existing record
    public void checkVersion(TradeModel t, int version) throws Exception {
        if (t.getVersion() < version) {
            throw new Exception(t.getVersion() + " is less than " + version);

        }

    }

    //Check if maturityDate
    public boolean checkMaturityDate(Date maturityDate, Date CurrentDate) {


        if (CurrentDate.compareTo(maturityDate) > 0)
            return false;

        return true;


    }

    public void checkExpiredDates() {

        //SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();

        for (String strKey : allTrade.keySet()) {
            if (currentDate.compareTo(allTrade.get(strKey).getMaturityDate()) > 0) {
                TradeModel t = allTrade.get(strKey);
                t.setExpired('Y');
                allTrade.replace(strKey, t);
            }
        }

    }


    //add Trade
    public void addTrade(TradeModel T) throws Exception {
        if (allTrade.containsKey(T.getTradeId())) {
            //System.out.println(allTrade.get(T.getTradeId()).getCounterPartId());
            //System.out.println(T.getVersion()+"  " +allTrade.get(T.getTradeId()).getVersion());
            checkVersion(T, allTrade.get(T.getTradeId()).getVersion());

            if (checkMaturityDate(T.getMaturityDate(), allTrade.get(T.getTradeId()).getMaturityDate())) {
                allTrade.replace(T.getTradeId(), T);
                System.out.println(T.getTradeId() + " is added to the Store");
            } else {
                System.out.println("Not able to add " + T.getTradeId() + " in the store as maturity date is lower than current date");
            }
        } else {

            if (checkMaturityDate(T.getMaturityDate(), T.getCreatedDate())) {

                allTrade.put(T.getTradeId(), T);
                System.out.println(T.getTradeId() + " is added to the Store");

            } else {
                System.out.println("Not able to add " + T.getTradeId() + " in the store as maturity date is lower than current date");
            }
        }

    }


    //get trade
    public TradeModel getTrade(String tId) throws Exception {
        if (allTrade.containsKey(tId))
            return allTrade.get(tId);
        throw new Exception("Trade with " + tId + " not Found");

    }

    //printAllTrade
    public void printTrade() {
        for (String tId : allTrade.keySet()) {
            System.out.println(allTrade.get(tId).toString());
        }
    }
}
