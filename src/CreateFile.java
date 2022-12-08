import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {

    // MAIN Method
    public static void main(String[] args) {
        System.out.println("This is from CreateFile");
        
        // code block to create new file with file name
        File myFile = new File("./random.txt");
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.getMessage();
        }

        // calling writeFile method
        writeToFile();
    }

    // Writing method
    public static void writeToFile() {
        try {
            // destination file to write content
            FileWriter file = new FileWriter("irandom.txt");

            // initialise BufferedWriter linked to FileWriter
            BufferedWriter myWriter = new BufferedWriter(file);

            // write file to desired file (overwrites file content if any)
            String text = "Hello World Again";
            String[] array = text.split(" ");
            
            // writes individual values in array to file
            for (String line : array) {
                myWriter.write(line); 
            }
            
            // close filewriter
            myWriter.close();
            // Statement to state successful writing
            System.out.println("File written");
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
