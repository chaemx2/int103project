
import java.util.Scanner;

import Library.Database;
import Library.Admin;
import Library.NormalUser;
import  Library.User;

public class Main {

    static Scanner s;
    static Database database;


    public static void main(String[] args) {
        database = new Database();
        System.out.println("Welcome to Library");
        int num;
        do {

            System.out.println("0. Exit\n1. Login\n2. New User");
            s = new Scanner(System.in);
            num = s.nextInt();

            switch (num) {
                case 1: login();
                case 2: newUser();
            }
        }while (num != 0);
    }

    private static void login(){
        System.out.println("Enter User Name");
        String userName = s.next();
        System.out.println("Enter Password");
        String password = s.next();
        int n = database.login(userName,password);
        if(n != -1){
            User user = database.getUser(n);
            System.out.println("Welcome " + user.getUserName());

        }else{
            System.out.println("User doesn't exit!");
        }

    }

    private static void newUser(){
        System.out.println("Enter Name:");
        String userName = s.next();
        System.out.println("Enter Password:");
        String password = s.next();
        System.out.println("1. Admin\n2. Normal User");
        int n2 = s.nextInt();
        User user;
        if (n2 == 1){
            user = new Admin(userName, password);
        } else {
            user = new NormalUser(userName, password);
        }
        database.AddUser(user);

    }








}
