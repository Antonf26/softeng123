/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package QuizApp.Core;

/**
 *
 * @author Anton
 */
public class User {
    public String fullName;
    public User()
    {
        
    }
    public User(int Utype, String UserName)
    {
        userName = UserName;
        utype = UserType.values()[Utype - 1];
    }
    public String userName;
    public UserType utype;
    public enum UserType{Student, Lecturer, ModuleLeader};
    public int dbId;
    
}
