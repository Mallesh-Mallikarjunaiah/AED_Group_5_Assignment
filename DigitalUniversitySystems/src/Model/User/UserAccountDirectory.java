/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.User;

import Model.Department;
import Model.Faculty;
import Model.Person;
import Model.Profile;
import Model.ProfileEnum;
import Model.Registrar;
import Model.Student;
import java.util.ArrayList;

/**
 *
 * @author gagan
 */
public class UserAccountDirectory {

    ArrayList<UserAccount> useraccountlist;

    public UserAccountDirectory() {
        useraccountlist = new ArrayList();
    }

    public UserAccount newUserAccount(String name, String contactNum, String un,
            String pw, ProfileEnum profile, Department department) {
        Person p = new Person(name, name, contactNum);
        Profile newProfile = null;
        switch (profile) {
            case FACULTY:
                newProfile = new Faculty(p, department);
                break;
            case STUDENT:
                newProfile = new Student(p, department);
                break;
            case REGISTRAR:
                newProfile = new Registrar(p);
                break;
        }
        UserAccount ua = new UserAccount(name, un, newProfile);
        useraccountlist.add(ua);
        return ua;
    }

    public UserAccount findUserAccount(String id) {
        for (UserAccount ua : useraccountlist) {
            if (ua.isMatch(id)) {
                return ua;
            }
        }
        return null; //not found after going through the whole list
    }

    public UserAccount AuthenticateUser(String un, String pw) {
        for (UserAccount ua : useraccountlist) {
            if (ua.IsValidUser(un, pw)) {
                return ua;
            }
        }
        return null; //not found after going through the whole list
    }

    public ArrayList<UserAccount> getUserAccountList() {
        return useraccountlist;
    }

    public void addUserAccount(UserAccount account) {
        this.useraccountlist.add(account);
    }
}
