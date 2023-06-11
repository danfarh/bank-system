/**
 * @author M.Advand
 * @version 0.1.0
 * this package has a class and an interface by name of IOFiles and ChangeInfo which allowed us write and read from files  and store,show and edit information in the file
 */

package Files;

import Accounts.Account;
import Accounts.Transaction;
import Employee.Employee;
import Accounts.PeriodicTransaction;
import user.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author  M.Advand
 * @version 0.1.3
 * this class have been created to write and read from files
 */

public class IOFiles {
    /**
     * @version 0.2.2
     * @param obj:An object which file most be in this type ex:User,Account,...
     * @param filename:store file name which we want to write or read from it
     *  this is a static method which write object into file and handel it exception
     */


    public static void WriteToFile(Object obj,String filename) {

        List<Object> objects=new ArrayList<>();
        File file = new File("database/Objects/" +filename+ ".txt");
        if(file.exists()){
            objects.addAll(readFromFile(filename));
        }
        boolean exist=false;
        for(Object object : objects){
            if(object.toString().equals(obj.toString())){

                exist=true;
                break;
            }
        }

        if(!exist){

            try (FileOutputStream fos = new FileOutputStream(file,true);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);

                fos.close();
                oos.close();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter("database/" +filename+"/"+filename + ".csv",true))) {
                    String[] values=new String[9];
                    if(obj.getClass()== PeriodicTransaction.class){
                        values=new String[]{((PeriodicTransaction) obj).getFromAccount(),((PeriodicTransaction) obj).getToAccountNumber(), String.valueOf(((PeriodicTransaction) obj).getEnterDate()),String.valueOf(((PeriodicTransaction) obj).getMoney()), String.valueOf(((PeriodicTransaction) obj).getOrder())};
                    }
                    else if(obj.getClass()==Employee.class){
                        values = new String[]{((Employee) obj).getFname(), ((Employee) obj).getLnmae(), ((Employee) obj).getGender(), ((Employee) obj).getDateOfBirth(), ((Employee) obj).getDateOfMembership(), ((Employee) obj).getIdCardNumber(), ((Employee) obj).getPassword(),((Employee) obj).getSalary(),((Employee) obj).getResume()};
                    }
                    else if(obj instanceof User){
                        values = new String[]{((User) obj).getFname(), ((User) obj).getLnmae(), ((User) obj).getGender(), ((User) obj).getDateOfBirth(), ((User) obj).getDateOfMembership(), ((User) obj).getIdCardNumber(), ((User) obj).getPassword()};
                    }
                    else if(obj.getClass()==Transaction.class){
                        values=new String[]{((Transaction)obj).getUser_id(),((Transaction)obj).getAccountID(),((Transaction)obj).getToId(), String.valueOf(((Transaction)obj).getMoney()),((Transaction)obj).getTransfer(),((Transaction)obj).getDate(), String.valueOf(((Transaction)obj).getTrackingNumber())};
                    }
                    else if(obj instanceof Account){
                        values=new String[]{((Account)obj).getAccountOwnerName(), String.valueOf(((Account)obj).getMoney()), String.valueOf(((Account)obj).getAccountNumber()),((Account)obj).getUser_id(),((Account)obj).getAccountType()};
                    }


                    String line = String.join(",", values);
                    bw.append(line);
                    bw.append("\n");
                    bw.flush();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * @version 0.1.1
     * @param filename:store file name which we want to write or read from it
     * A static method which read objects from a file and return them as an arraylist
     * @return An arrayList of object which was stored in the file
     */

    public static ArrayList readFromFile(String filename) {
        ArrayList<Object> users = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File("database/Objects/" +filename+ ".txt"));

            while(fis.available() != 0){
                ObjectInputStream  ois = new ObjectInputStream(fis);
                Object user=null;
                try {
                    user = (Object) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if(user != null)
                    users.add(user);
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return users;
    }
}

