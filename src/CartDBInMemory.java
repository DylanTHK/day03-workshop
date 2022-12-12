import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CartDBInMemory {

    // to initilise hashmap as temporary database
    public HashMap<String, ArrayList<String>> userMap = new HashMap<String, ArrayList<String>>();

    // constructor
    public CartDBInMemory(String baseFolder) {
        this.loadDataFromFiles(baseFolder);
    }

    // load files from directory to hashmap (userMap)
    public void loadDataFromFiles(String folderName) {
        File f = new File(folderName);

        // function to return true for a single file with .db
        FilenameFilter fileNameFilter = new FilenameFilter() {
            // what are we overriding here? 
            @Override
            public boolean accept(File dir, String filename) {
                // returns true if filename ends with .db
                return filename.endsWith(".db");
            }
        };

        // variable to store array of files ending with specified suffix (.db)
        File[] filteredFiles = f.listFiles(fileNameFilter);

        // ends function if less than 1 file found
        if (filteredFiles.length < 1) {
            return;
        }

        // adds key (username) and values (ArrayList) to hashmap
        for (File file : filteredFiles) {
            String userKey = file.getName().replace(".db", "");
            // Read the content of the file
            this.userMap.put(userKey, readFile(file));
        }
    }

    // takes text from individual files and stores in ArrayList
    public ArrayList<String> readFile(File file) {
        // create temporary arraylist
        System.out.println(file);
        ArrayList<String> myArrayList = new ArrayList<String>();
        // S
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            
            // while loop to go through individual lines in file
            String line;
            while ((line = bf.readLine()) != null) {
                // push element to ArrayList
                myArrayList.add(line.trim());
            }
            bf.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return myArrayList;
    }

}
