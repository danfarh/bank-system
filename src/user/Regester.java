package user;

import Files.IOFiles;

import java.io.File;
import java.util.ArrayList;

/**
 * @author M.Advand
 * @version 0.1.2
 * this class allows to login and sign up and has three method by the name of check,signUp,login
 */

public class Regester {

    private String filename;
    public Regester(String filename){
        this.filename=filename;
    }

    /**
     * @version 0.1.0
     * @param username
     * @param password
     * this method check if user enroll before or not
     * @return A boolean that it's true when user exist in file
     */
    public boolean check(String username,String password){
        ArrayList<User>users=new ArrayList<>();
        File file = new File("database/Objects/" +filename+ ".txt");
        if(file.exists()){
            users.addAll(IOFiles.readFromFile(filename));
        }
        boolean exist=false;
        for(User user:users){
            if (user.getIdCardNumber().equals(username) && user.getPassword().equals(password)){
                exist=true;
                break;
            }
        }
        return exist;

    }

    /**
     * @version 0.1.1
     * @param user: an object user than want to sign up
     *  this method check if the user exist in file or not then add it into file
     * @return A boolean that is true when the user add to file
     */

    public boolean signUp(User user){

        if(!check(user.getIdCardNumber(),user.getPassword())){
            IOFiles.WriteToFile(user,filename);
            return true;
        }
        return false;
    }

    /**
     * @version 0.1.1
     * @param username
     * @param password
     * this method allows to login by username and password
     * @return a boolean that when it login returns true
     */

    public boolean logIn(String username,String password){
        if(check(username,password)){
            return true;
        }
        else {
            return false;
        }
    }
}
