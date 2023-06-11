package Accounts;


import Accounts.Account;
import Manager.AccountManager;
import Manager.Time;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DemandDepositAccount extends Account {
    private final double InterestRate = 0.15;
    private final Integer Mounths = 12;
    private Date expired_at;

    public DemandDepositAccount(String accountOwnerName, long money, String user_id) {
        super(accountOwnerName, money,user_id,"DemandDepositAccount");
        expired_at = new Date(System.currentTimeMillis() + 2592000000L);
    }


    public Integer getMounths() {
        return Mounths;
    }

    public double getInterestRate() {
        return InterestRate;
    }


    public synchronized   void annualMonthRaise(){
        try {
            wait();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        AccountManager AM=new AccountManager("Accounts");
        Account account= (Account) AM.search(this.getAccountNumber());
        account.setMoney((long) (this.getMoney() + (InterestRate / Mounths * this.getMoney())));
        AM.edit();

    }
    public synchronized void checkDate(){
        Date expired_at = new Date(System.currentTimeMillis() + 2592000000L);
        if(Time.getDate().after(expired_at)) {
            long diffInMillies=Math.abs(Time.getDate().getTime()-this.getEnterDate().getTime());
            double diff = Math.floor(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)/30);
            this.setMoney((long)(this.getMoney()*diff));
            notify();
            this.setEnterDate(Time.getDate());
        }
    }
}
