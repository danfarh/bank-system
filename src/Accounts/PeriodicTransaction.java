package Accounts;

import Accounts.Account;
import Files.IOFiles;
import Manager.Time;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PeriodicTransaction implements Serializable {
    private String fromAccount;
    private String toAccountNumber;
    private Date enterDate;
    private Long money;
    private int order=0;
    private boolean depositTime=false;
    private static final long serialVersionUID = 4390482518182625972L;

    public PeriodicTransaction(String formId, String toid,long money){
        this.fromAccount=formId;
        this.toAccountNumber=toid;
        this.money=money;
        this.enterDate= Time.getDate();
    }

    public int getOrder() {
        return order;
    }
    public String getFromAccount(){
        return fromAccount;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }


    public Date getEnterDate() {
        return enterDate;
    }

    public boolean isDepositTime() {
        return depositTime;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public void setDepositTime(boolean depositTime) {
        this.depositTime = depositTime;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    public static ArrayList<PeriodicTransaction> showPT(String fromId){
        ArrayList<PeriodicTransaction>list=new ArrayList<>();
        ArrayList<PeriodicTransaction>myList=new ArrayList<>();
        File file = new File("database/Objects/Periodic.txt");
        if(file.exists()){
            list.addAll(IOFiles.readFromFile("Periodic"));
        }
        for (PeriodicTransaction PT:list){
            if (PT.getFromAccount().equals(fromId)){
                myList.add(PT);
            }
        }
        return myList;
    }
    public synchronized void periodicTransfer(){
        ArrayList<PeriodicTransaction>list=new ArrayList<>();
        list.addAll(PeriodicTransaction.showPT(this.getFromAccount()));

        for (PeriodicTransaction PT :list){
            if(!PT.isDepositTime()){
                try {
                    wait();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            if(Account.transfer_money(this.getFromAccount(), PT.getToAccountNumber(),this.getMoney())){
                this.setOrder(getOrder()+1);
            }

        }
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
