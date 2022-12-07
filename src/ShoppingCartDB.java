// Shopping Cart Class to create

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class ShoppingCartDB {
    
    // class variable / member (CAPS to represent constants)
    private static final String LOGIN = "login";
    private static final String ADD = "add";
    private static final String LIST = "list";
    private static final String SAVE = "save";
    private static final String USERS = "users";
    private static final String EXIT = "exit";
    
    private static final List<String> VALID_COMMANDS = Arrays.asList(
        LOGIN, ADD, LIST, USERS, EXIT);
    
    // initialise temporary variables
    private String currentUser;
    private CartDBInMemory db; 

    // Constructor to initialise instance of hashmap database 
    public ShoppingCartDB() {
        this.db = new CartDBInMemory();
    }

    // method to control app flow
    public void startShell() {
        // Welcome message
        System.out.println("Welcome to MultiUser Shopping Cart >>");
        // initialise scanner object
        Scanner scan = new Scanner(System.in);

        Boolean stop = false;

        // while loop to run through the
        while (!stop) {
            // takes in entire input
            String input = scan.nextLine().trim();

            // check for exit keyword
            if (input.equals("exit")) {
                System.out.println("Exiting");
                stop = true;
            }
            
            // check for valid input
            if (!validateCommand(input)) {
                // command is invalid, print error message
                System.out.println("Invalid input detected");
            } else {
                // calls method to process and process command with relative actions
                processInput(input);
            }
            // System.out.println(validateCommand(input)); // to view boolean output

        }
        scan.close();
    }

    // method to check if command is one of the listed commands
    public boolean validateCommand(String input) {
        // remove commas if any
        input.replace(",", "");
        // split input into array of command and content
        String[] parts = input.split(" ");
        // check with default for any existing 
        return VALID_COMMANDS.contains(parts[0]);
    }

    // method to process identify command and execute
    public void processInput(String input) {
        // clean and split input into individual strings
        input.replace(",", "");
        Scanner scan = new Scanner(input);
        String command = scan.next();

        // detect command keywords
        switch (command) {
            // 
            case LOGIN:
                String userName = scan.nextLine().trim();
                // set user as currentUser
                this.currentUser = userName;
                // calls login action methods
                loginAction(userName);
                break;

            case ADD:
                // scan for incoming items after add command
                String[] items = scan.nextLine().trim().split(" ");
                System.out.println( "ADD fruits to user's cart command");
                addAction(items);
                break;

            case LIST:
                System.out.println("print out all shopping cart of usersLIST command");
                break;

            case SAVE:
                System.out.println("SAVE all inputs in hashmap and separate path file");
                break;

            case USERS:
                System.out.println("print all USERS names command");
                break;

            default:
                break;
        }
        
        scan.close();
    }

    // method to assist in login command
    public void loginAction(String username) {
        // check if user exists in hashmap key
        this.currentUser = username;
        if (this.db.userMap.containsKey(username)) {
            // print welcome message
            System.out.printf("%s, your cart contains the following items\n", this.currentUser);
            // call listAction method of items 
            System.out.println("calls method to print out lines of fruits"); // ******$$ add later

        } else { 
            // if does not exist create new user name with new value array
            this.db.userMap.put(username, new ArrayList<String>());
            System.out.printf("%s, your cart is empty\n", this.currentUser);
        }
    }
    
    public void addAction(String[] items) {

    }

}

