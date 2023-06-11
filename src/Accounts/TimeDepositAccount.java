package Accounts;
import Accounts.Account;
import Files.IOFiles;
import Manager.AccountManager;
import Manager.Time;

import java.util.ArrayList;
import java.util.Date;

public class TimeDepositAccount extends Account {
    private final double InterestRate = 0.20;
    private Date expired_at;

    public TimeDepositAccount(String accountOwnerName, long money, String user_id) {
        super(accountOwnerName, money,user_id,"TimeDepositAccount");
        expired_at = new Date(System.currentTimeMillis() + 31536000L);
    }

    public double getInterestRate() {
        return InterestRate;
    }


    public  void annualYearRaise(){
        if(Time.getDate().after(expired_at)) {
            this.setMoney((long) (this.getMoney() + (InterestRate * this.getMoney())));
        }
    }
    @Override
    public Boolean allowTransaction(){
        return Time.getDate().after(expired_at);
    }
}
