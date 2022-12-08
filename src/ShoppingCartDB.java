// Shopping Cart Class to create

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        LOGIN, ADD, LIST, SAVE, USERS, EXIT);
    
    // initialise temporary variables
    private String currentUser; // to track active user
    private CartDBInMemory db; // for Hashmap
    private String baseFolder;

    // Constructor (DEFAULT) to initialise instance of hashmap database 
    public ShoppingCartDB() {
        this.baseFolder = "db"; //default name for new folder
        this.setup();
        this.db = new CartDBInMemory(this.baseFolder);
    }

    // Constructor (OVERLOAD) initialise instance of hashmap with baseFolder input
    // refer to MultiUserShoppingCart line 7 for input
    public ShoppingCartDB(String baseFolderName) {
        this.baseFolder = baseFolderName;
        this.setup();
        this.db = new CartDBInMemory(this.baseFolder);
    }

    // checks for existing directory, if none, create new directory
    public void setup() {
        // creates a path object instance with Fully Qualified Path
        Path path = Paths.get(this.baseFolder);

        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }
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
                System.out.println("Thank you for Shopping");
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
                this.loginAction(userName);
                break;

            case ADD:
                // scan for incoming items after add command
                String[] items = scan.nextLine().trim().replace(",", "").split(" "); // trims, split string to array
                this.addAction(items);
                break;

            case LIST:
                this.listAction();
                break;

            case SAVE:
                this.saveAction();
                break;

            case USERS:
                this.listUsers();
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
            listAction();

        } else { 
            // if does not exist create new user name with new value array
            this.db.userMap.put(username, new ArrayList<String>());
            // String to print for empty cart
            System.out.printf("%s, your cart is empty\n", this.currentUser);
        }
    }
    
    // to add items to arrayList of currentUser
    public void addAction(String[] items) {
        // get ArrayList of current user
        ArrayList<String> selectedArray = this.db.userMap.get(this.currentUser);
        // For Each loop to check for all items in array
        for (String item : items) {
            // filters duplicates
            if (!selectedArray.contains(item)) {
                selectedArray.add(item);
                System.out.println(item + " added to cart");
            }
        }
    }

    public void listAction() {
        // get value array base on key(currentUser)
        ArrayList<String> arrayList = this.db.userMap.get(this.currentUser);
        // print out all values in array
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, arrayList.get(i));
        }
    }

    public void listUsers() {
        int i = 0;
        for (String key : this.db.userMap.keySet()) {
            i++;
            System.out.printf("%d. %s\n", i, key);
        }
    }

    public void saveAction() {
        // create new db file with username 
        String userFile = this.baseFolder + "/" + this.currentUser + ".db";
        try {
            FileWriter myWriter = new FileWriter(userFile);
            BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
            // read through current user's Array
            for (String line : db.userMap.get(this.currentUser)) {
                // write words to file
                bufferedWriter.write(line + "\n");
            }
            bufferedWriter.close();
            System.out.println(this.currentUser + "'s data saved");
        } catch (IOException e) {
            e.getMessage();
        }
    }

}

