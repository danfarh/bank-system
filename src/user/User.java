/**
 * @author M.Advand
 * @version 0.1.0
 * this package has two class by the name of User and Regester
 */
package user;

import java.io.Serializable;

/**
 * @author M.Advand
 * @version 0.1.2
 * Main properties of person which goes to bank as client or employee implemented in thi class
 */

public class User  implements Serializable {
    private String fname;
    private String lnmae;
    private String gender;
    private String dateOfBirth;
    private String dateOfMembership;
    private String idCardNumber;
    private String password;
    private static final long serialVersionUID = 1113799434508676095L;

    /**
     * A standard constructor for initializing properties of user
     *
     * @param fname:first        name
     * @param lname:last         name
     * @param gender
     * @param dateOfBirth
     * @param dateOfMembership:A date which user enroll in the bank
     * @param idCardNumber
     * @param password:Which     use for log in
     */

    public User(String fname, String lname, String gender, String dateOfBirth, String dateOfMembership, String idCardNumber, String password) {
        this.fname = fname;
        this.lnmae = lname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateOfMembership = dateOfMembership;
        this.idCardNumber = idCardNumber;
        this.password = password;
    }

    /**
     * use for showing information and log in
     *
     * @return id card number
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * use for showing information
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * use for showing information
     *
     * @return A date which user enroll in the bank
     */
    public String getDateOfMembership() {
        return dateOfMembership;
    }

    /**
     * use for showing information
     *
     * @return first name
     */

    public String getFname() {
        return fname;
    }

    /**
     * use for showing information
     *
     * @return gender
     */

    public String getGender() {
        return gender;
    }

    /**
     * use for showing information
     *
     * @return last name
     */

    public String getLnmae() {
        return lnmae;
    }

    /**
     * use for showing information and log in
     *
     * @return password
     */

    public String getPassword() {
        return password;
    }

    /**
     * use for edit personal information
     *
     * @param gender
     */

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * use for edit personal information
     *
     * @param dateOfBirth
     */

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * use for edit personal information
     *
     * @param dateOfMembership
     */

    public void setDateOfMembership(String dateOfMembership) {
        this.dateOfMembership = dateOfMembership;
    }

    /**
     * use for edit personal information
     *
     * @param fname
     */

    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * use for edit personal information
     *
     * @param idCardNumber
     */

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    /**
     * use for edit personal information
     *
     * @param lnmae
     */

    public void setLnmae(String lnmae) {
        this.lnmae = lnmae;
    }

    /**
     * use for edit personal information
     *
     * @param password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getFname() + '\n' + getLnmae() + '\n' + getGender() + '\n' + getDateOfBirth() + '\n' + getDateOfMembership();
    }
}
