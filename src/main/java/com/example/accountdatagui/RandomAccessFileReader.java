package com.example.accountdatagui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;

//This class contains one method to read the data the user provides
//And store
public class RandomAccessFileReader {
    public ArrayList<Account> readRandomAccessFile(String filePath, ArrayList<Account> accounts) throws IOException, ExceptionHandling.CustomFileNotFoundException {
        //Specify the mode r for reading
//        ArrayList<Account> accounts  = new ArrayList<>();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")){
            // move the file pointer to the beginning of the file
            randomAccessFile.seek(0);
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                //read a line assuming text data (adjust as needed)
                String line = randomAccessFile.readLine();
                // Split the line using a comma as a delimiter and store it in an array of strings
                String[] values = line.split(", ");
                //Create an account object with the parsed values
                Account account = new Account(Integer.parseInt(values[0]),
                        values[1],
                        values[2],
                        values[3],
                        Double.parseDouble(values[4].substring(1)),
                        values[5].charAt(0),
                        Double.parseDouble(values[6].substring(1))
                );
                //Add the new account object to the array list of accounts
                accounts.add(account);
            }
        } catch (FileNotFoundException e){
            //Catch the file not found exception then throw custom file not found
            throw new ExceptionHandling.CustomFileNotFoundException("Custom File Not Found Exception \n File not Found: " + filePath);
        }
        return accounts;
    }

    private void displayErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String selectAnotherFile(){
        //Create a file Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Another Input File");

        //Show the FileChooser and wait for the user to select a file
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Return string of path of new selected file for recursion
        return (selectedFile != null) ? selectedFile.getPath() : "";

    }
}
