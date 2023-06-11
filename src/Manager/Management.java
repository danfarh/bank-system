package Manager;

import Accounts.Account;
import Employee.Employee;
import Files.ChangeInfo;
import Files.IOFiles;
import user.Regester;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @author M.Advand
 * @version 0.1.3
 * This class implements ChangeInfo interface which allows to add,remove,show and search users in own files
 */
public class Management implements ChangeInfo {
    private final String fileName;
    private ArrayList<User>users=new ArrayList<>();

    /**
     * A constructor for Management class
     * @param fileName
     * we set fileName as a parameter of the class because most of the method needs this variable
     */
    public Management(String fileName){
        this.fileName = fileName;
        File file = new File("database/Objects/" +fileName+ ".txt");
        if(file.exists()){
            users.addAll(IOFiles.readFromFile(fileName));
        }
    }

    /**
     * this static method check the identity of manager
     * @param username
     * @param password
     * @return a boolean that is true when authentication confirmed
     */

    public static boolean Authentication(String username, String password) {
        try {
            File myObj = new File("database/ManagerAuth/ManagerAuth.csv");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            myReader.close();
            String []auth=data.split(",");
            if(auth[0].equals(username) && auth[1].equals(password)){
                return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method creates new user and adds it to the file
     * @param user
     * @return A massage that the user added or not
     */

    public boolean newUser(User user){
        if(new Regester(fileName).signUp(user)){
            return true;
        }
        return false;
    }

    /**
     * This method returns a list of users which exist in the file sorted by last name alphabetical order
     * @return an array list of user
     */

    public ArrayList getUsers() {
        Collections.sort(users,new Sortbylname() );
        return users;
    }

    /**
     * This method search a user in the file by id
     * @param id
     * @return a user when exists or an error massage
     */

    @Override
    public Object search(String id) {
        for(User user :users){
            if (user.getIdCardNumber().equals(id)){
                return user;
            }
        }
        return "invalid id";
    }


    public String edit(){
        try {
            new FileOutputStream("database/Objects/" +fileName+ ".txt").close();
            new FileWriter("database/" +fileName+"/"+fileName + ".csv").close();
            for(User user :users) {
                IOFiles.WriteToFile(user, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "something goes wrong!!!";
        }
        return "invalid id";
    }

    /**
     * Delete a user form list of users and update the file
     * @param id
     * @return
     */
    @Override
    public  String delete(String id) {
        for(User user :users) {
            if (user.getIdCardNumber().equals(id)) {
                users.remove(user);
                break;
            }
        }

        try {
            new FileOutputStream("database/Objects/" +fileName+ ".txt").close();
            new FileWriter("database/" +fileName+"/"+fileName + ".csv").close();
            for(Object user :users) {
                IOFiles.WriteToFile(user, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "something goes rung!!!";
        }
        return "invalid id";
    }

}
class Sortbylname implements Comparator<User>
{
    public int compare(User u1, User u2) {
        return u1.getLnmae().compareTo(u2.getLnmae());
    }
}
