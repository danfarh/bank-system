/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this package have been created for Employee
 */
package Accounts;

import Files.IOFiles;
import Manager.AccountManager;
import Manager.Time;
import Accounts.PeriodicTransaction;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Daniyal_Farhangi
 * @version 0.1.2
 * account class
 */

public class Account implements Serializable {
    private String accountOwnerName;
    private static int counter;
    private String user_id;
    private long money = 0;
    private String accountNumber;
    private String accountType;
    private Date enterDate;
    private static final long serialVersionUID = 4390482518182625971L;

    /**
     * Constructor
     * @param accountOwnerName
     * @param money
     * @param user_id
     * @param accountType
     */
    public Account(String accountOwnerName, long money, String user_id, String accountType){
        this.accountOwnerName = accountOwnerName;
        this.money = money;
        this.accountType = accountType;
        this.user_id = user_id;
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        this.accountNumber= formatter.format(date)+counter;
        counter++;
        enterDate=Time.getDate();
    }


    public String getUser_id() {
        return user_id;
    }
    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {

        return accountNumber;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public  long getMoney() {
        return money;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public  Boolean allowTransaction(){
       if (this.getAccountType().equals("CurrentDepositAccount")){
           return true;
       }
       else if(this.getAccountType().equals("DemandDepositAccount")){
           return true;
       }
       else if(this.getAccountType().equals("TimeDepositAccount")){
           return false;
       }
       return true;
    }

    /**
     * deposit money
     * this method is for putting some money in a account
     *  @param fromId
     * @return
     */
    public synchronized String depositFund(long amount,String fromId) {
        if (amount > 0) {
            double pool = this.getMoney();
            pool += amount;
            this.setMoney((long) pool);
            Transaction transaction=new Transaction(this.getAccountNumber(),amount,this.getAccountOwnerName(),this.getUser_id(),this.getAccountType(),fromId,"deposit");
            AccountManager tManager=new AccountManager("Transactions");
            tManager.newAccount(transaction);
        }
        return "a mount of money most be more than zero";
    }

    /**
     * withdraw money
     * this method is for getting money from an account
     * @param formId
     * @param amount
     * @return
     */
    public synchronized  boolean withdrawFund(long amount,String formId){


        double pool = this.getMoney();
        if(amount <= 0 || (pool - amount) < 0) {
            return false;
        }
        pool -= amount;
        this.setMoney((long) pool);
        Transaction transaction=new Transaction(this.getAccountNumber(),amount,this.getAccountOwnerName(),this.getUser_id(),this.getAccountType(),formId,"withdraw");
        AccountManager tManager=new AccountManager("Transactions");
        tManager.newAccount(transaction);
        System.out.println(transaction.toString());
        return true;


    }

    /**
     * Method transfer money
     * this method is for tranfer money betweem two accounts
     * @param toId
     * @param fromId
     * @param amount
     * @return
     */

    public static boolean transfer_money(String fromId,String toId , long amount){
        AccountManager accountManager=new AccountManager("Accounts");
        ArrayList<Account> accountNumbersList=new ArrayList<>();
        accountNumbersList.addAll(accountManager.getAccounts());

        Account toAccount=null;
        Account fromAccount = null;

        boolean flag=false;

        for (Account account : accountNumbersList) {
            if(account.getAccountNumber().equals(toId)){
                toAccount=account;
            }
            else if(account.getAccountNumber().equals(fromId)){
                flag=true;
                fromAccount=account;
            }
        }
        if(flag){

            if(toAccount.allowTransaction() && fromAccount.allowTransaction()){
                Account finalFromAccount = fromAccount;
                Account finalToAccount = toAccount;
                if(finalFromAccount.withdrawFund(amount,toId)){
                    finalToAccount.depositFund( amount,fromId);
                    accountManager.edit();
                }
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    public static boolean singleTransaction(long amount,String formId,String type) {
        AccountManager AM = new AccountManager("Accounts");
        Account account = (Account) AM.search(formId);
        if (account.allowTransaction()) {
            if (type.equals("withdraw")) {
                if (account.withdrawFund(amount, formId)) {
                    AM.edit();
                    return true;
                }
            }
            else if (type.equals("deposit")) {
                account.depositFund(amount, formId);
                AM.edit();
                return true;
            }
            return false;
        }
        return false;
    }

    public static PeriodicTransaction addPeriodicTransaction(String fromId,String toId,Long money){
        PeriodicTransaction PT=new PeriodicTransaction(fromId,toId,money);
        IOFiles.WriteToFile(PT,"Periodic");
        return PT;
    }

    /**
     * Show Inventory Method
     * This method is for showing the inventory
     * @param accountNumber
     * @return
     */
    public static long showInvestory(String accountNumber){
        File file = new File("database/Objects/Accounts.txt");
        ArrayList<Account>accountList=new ArrayList<>();
        if(file.exists()) {
            accountList.addAll(IOFiles.readFromFile("Accounts"));
        }
        for (Account account : accountList) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return (account.getMoney());
            }
        }

        return -1 ;

    }
    /**
     * tostring method
     * @return
     */
    public String toString(){
        return  getAccountNumber() + accountOwnerName + " " +  money + " " ;
    }

}
