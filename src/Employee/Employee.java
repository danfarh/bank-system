/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this package have been created for Employee
 */
package Employee;

import user.User;

/**
 * @author  Daniyal_Farhangi
 * @version 0.1.2
 * this class have been for employees
 */
public class Employee extends User {

    private String salary;
    private String resume;

    /**
     * constructor
     * @param fname first name
     * @param lname last name
     * @param gender gender
     * @param dateOfBirth
     * @param dateOfMembership
     * @param idCardNumber
     * @param password
     * @param salary
     * @param resume
     */
    public Employee(String fname, String lname, String gender, String dateOfBirth, String dateOfMembership, String idCardNumber, String password , String salary , String resume) {
        super(fname, lname, gender, dateOfBirth, dateOfMembership, idCardNumber, password);
        this.salary = salary;
        this.resume = resume;
    }

    /**
     * getter
     * get Resume
     * @return
     */
    public String getResume() {
        return resume;
    }

    /**
     * setter
     * @param resume
     */
    public void setResume(String resume) {
        this.resume = resume;
    }

    /**
     * getter
     * get salary
     * @return
     */
    public String getSalary() {
        return salary;
    }

    /**
     * setter
     * @param salary
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }
    @Override
    public String toString() {
        return getFname() + '\n' + getLnmae() + '\n' + getGender() + '\n' + getDateOfBirth() + '\n' + getDateOfMembership()+'\n'+getSalary()+'\n'+getResume();
    }
}
