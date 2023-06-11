package Clients;

import Accounts.*;

import Files.ChangeInfo;
import Files.IOFiles;
import user.User;
import Manager.AccountManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * member(client) class
 */
public class Client extends User  {

    public Client(String fname, String lname, String gender, String dateOfBirth, String dateOfMembership, String idCardNumber, String password , String salary , String resume) {
        super(fname, lname, gender, dateOfBirth, dateOfMembership, idCardNumber, password);

    }

    /**
     * Show all accounts of the client
     */
    public static ArrayList<Account> showAllAccounts(String IdCardNumber,String fileName) {
        File file = new File("database/Objects/"+fileName+".txt");
        ArrayList<Account>accountList=new ArrayList<>();
        if(file.exists()){
            accountList.addAll(IOFiles.readFromFile(fileName));
        }
        ArrayList<Account> user_account = new ArrayList<>();

        if (fileName.equals("Transactions")){
            for (Account account : accountList){
                Transaction transaction=(Transaction) account;
                if(IdCardNumber.equals(transaction.getAccountID())){
                    user_account.add(account);
                }
            }
        }else {
            for (Account account : accountList){
                if (IdCardNumber.equals(account.getUser_id())) {
                    user_account.add(account);
                }
            }
        }
        return user_account;
    }
    public static String addAccount(long money, String accountType, String accountOwnerName, String user_id) {
        if(accountType.equals("TimeDepositAccount")){
            Account account = new TimeDepositAccount(accountOwnerName,money,user_id);
            AccountManager accountManager = new AccountManager("Accounts");
            accountManager.newAccount(account);
            return "successfully added";
        }
        else if(accountType.equals("DemandDepositAccount")){
            Account account = new DemandDepositAccount(accountOwnerName,money,user_id);
            AccountManager accountManager = new AccountManager("Accounts");
            accountManager.newAccount(account);
            return "successfully added";
        }
        else if(accountType.equals("CurrentDepositAccount")){
            Account account = new CurrentDepositAccount(accountOwnerName,money,user_id);
            AccountManager accountManager = new AccountManager("Accounts");
            accountManager.newAccount(account);
            return "successfully added";
        }
        return "can't create account";
    }

}

class SortByOwnerName implements Comparator<Account>
{
    public int compare(Account ac1, Account ac2)
    {
        return ac1.getAccountOwnerName().compareTo(ac2.getAccountOwnerName());
    }
}

