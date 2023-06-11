package Accounts;

import Accounts.Account;

public class CurrentDepositAccount extends Account {

    public CurrentDepositAccount(String accountOwnerName, long money, String user_id){
        super(accountOwnerName , money,user_id,"current deposit");
    }

}
