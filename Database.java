/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Library;

import java.util.ArrayList;

public class Database {

    ArrayList<User> users = new ArrayList<User>();
    ArrayList<String> userNames = new ArrayList<String>();


    public void AddUser(User s){
        users.add(s);
        userNames.add(s.getUserName());
    }

    public int login(String userName , String password){
        int n = -1;
        for(User s: users){
            if(s.getUserName().matches(userName) && s.getPassword().matches(password)){
                n = users.indexOf(s);
                break;
            }
        }
        return n;
    }
    public User getUser(int n){
        return users.get(n);
    }

}
