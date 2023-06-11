package Manager;

import Files.ChangeInfo;
import Files.IOFiles;
import Accounts.Account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author M.Advand
 * @version 0.1.3
 * This class implements ChangeInfo interface which allows to add,remove,show and search account in own files
 */

public class AccountManager implements ChangeInfo {
    private final String fileName;
    private ArrayList<Account> accounts=new ArrayList<>();

    /**
     * A constructor for AccountManager class
     * @param fileName
     * we set fileName as a parameter of the class because most of the method needs this variable
     */

    public AccountManager(String fileName) {
        this.fileName = fileName;
        File file = new File("database/Objects/" +fileName+ ".txt");
        if(file.exists()){
            accounts.addAll(IOFiles.readFromFile(fileName));
        }
    }


    /**
     * this method creates new account and adds it to the file
     * @return A massage that the user added or not
     */

    public String newAccount(Account account){
        IOFiles.WriteToFile(account,fileName);
        return "successfully added";
    }

    /**
     * This method returns a list of account which exist in the file
     * @return an array list of account
     */

    public ArrayList<Account> getAccounts() {
        Collections.sort(accounts,new SortByOwnerName() );
        return accounts;
    }

    /**
     * This method search a account in the file by id
     * @param id
     * @return a account when exists or an error massage
     */
    @Override
    public Object search(String id) {

        for(Account account :accounts){
            if (account.getAccountNumber().equals(id)){
                return account;
            }
        }
        return "invalid id";
    }

    @Override
    public String edit(){
        try {
            new FileOutputStream("database/Objects/" +fileName+ ".txt").close();
            new FileWriter("database/" +fileName+"/"+fileName + ".csv").close();

            for(Account account :accounts) {
                IOFiles.WriteToFile(account, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "something goes wrong!!!";
        }
        return "invalid id";
    }

    /**
     * Delete a user form list of accounts and update the file
     * @param id
     * @return
     */

    @Override
    public String delete(String id) {
        for(Account account :accounts) {
            if (account.getAccountNumber().equals(id)) {
                accounts.remove(account);
                break;
            }
        }

        try {
            new FileOutputStream("database/Objects/" +fileName+ ".txt").close();
            new FileWriter("database/" +fileName+"/"+fileName + ".csv").close();
            for(Account account :accounts) {
                IOFiles.WriteToFile(account, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "something goes wrong!!!";
        }
        return "invalid id";
    }
}
class SortByOwnerName implements Comparator<Account>
{
    public int compare(Account ac1, Account ac2)
    {
        return ac1.getAccountOwnerName().compareTo(ac2.getAccountOwnerName());
    }
}
