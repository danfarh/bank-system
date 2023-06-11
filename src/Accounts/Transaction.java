package Accounts;
/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this package have been created for Employee
 */


import Files.IOFiles;
import Manager.AccountManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this class have been created to do transaction
 */
public class  Transaction extends Account  implements Serializable {
    private static int counter;
    private String trackingNumber;
    private String toId;
    private String accountID;
    private String transfer;
    private static final long serialVersionUID = 4390482518182625971L;

    /**
     * Constructor
     * @param accountID
     * @param money
     * @param accountOwnerName
     */
    public Transaction(String accountID, long money, String accountOwnerName, String user_id, String accountType,String toId,String transfer) {
        super(accountOwnerName,money, user_id, accountType);
        this.accountID = accountID;
        this.toId=toId;
        this.transfer=transfer;
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        this.trackingNumber= formatter.format(date)+counter;
        counter++;
    }

    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    public String getToId() {
        return toId;
    }

    /**
     * getter
     * this is the account id
     * @return
     */
    public String getAccountID() {
        return accountID;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getTrackingNumber() {


        return trackingNumber;
    }

    public static ArrayList<Transaction> showTransaction() {
        ArrayList<Transaction>transactions=new ArrayList<>();
        transactions.addAll(IOFiles.readFromFile("Transactions"));
        return transactions;
    }

    @Override
    public String toString(){
        return "transaction was successfully!\n"+getTransfer()+" form the account by number of "+getAccountID()+" to "+getToId()+"\n" +
                "date: "+getDate()+"\n"+"your tracking number: "+getTrackingNumber();
    }



}
