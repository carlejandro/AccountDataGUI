package com.example.accountdatagui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AccountDataGUI extends Application {
    //Declare our reader object
    private RandomAccessFileReader reader;
    private ArrayList<Account> accounts; // PRIVATE Accounts field for data storage
    private Label securityQuestionLabel;
    private TextField securityQuestionTextField;
    private TextField accountNumberTextField;
    private TextField passwordTextField;
    private TextArea accountInfoTextArea;
    private Button checkSecurityQuestionButton;

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
            primaryStage.setTitle("Account Data GUI");

            //Initialize the reader Object and Array list for accounts. Focus on the OBJECTS.
            reader = new RandomAccessFileReader();
            accounts = new ArrayList<>();
            // Welcome Label
            Label welcomeLabel = new Label("\t\tWelcome to the Account Checker." +
                    " Push the button to open a new account file window." );

            // File opener button
            Button fileOpenerButton = new Button("Open File");
            fileOpenerButton.setOnAction(e -> {
                try {
                    openFile(primaryStage);
                } catch (ExceptionHandling.CustomFileNotFoundException | IOException ignored) {}
            });

            // Account Number TextField
            accountNumberTextField = new TextField();
            accountNumberTextField.setPromptText("Type Account Number");

            //Labels for Account Number and Password
            Label accountNumberLabel = new Label("Account Number:");
            Label passwordLabel = new Label("Password:");

            // HBox for Account Number
            HBox accountNumberBox = new HBox(10);
            accountNumberBox.setAlignment(Pos.CENTER);
            accountNumberBox.getChildren().addAll(accountNumberLabel, accountNumberTextField);

            // Password TextField
            passwordTextField = new TextField();
            passwordTextField.setPromptText("Type Password");

            // HBox for Password
            HBox passwordBox = new HBox(10);
            passwordBox.setAlignment(Pos.CENTER);
            passwordBox.getChildren().addAll(passwordLabel, passwordTextField);

            // Button to check account and password
            Button checkButton = new Button("Check Account");
            checkButton.setOnAction(e -> checkAccount(accountNumberTextField.getText(), passwordTextField.getText()));

            //Security Question Label
            securityQuestionLabel = new Label();
            securityQuestionLabel.setVisible(false); //Non visible on startup

            // Security Question TextField
            securityQuestionTextField = new TextField();
            securityQuestionTextField.setPromptText("Type Security Answer");
            securityQuestionTextField.setVisible(false); //Non-visible on startup

            // Button to check security question
            checkSecurityQuestionButton = new Button("Check Security Question");
            checkSecurityQuestionButton.setOnAction(e -> checkSecurityQuestion());
            checkSecurityQuestionButton.setVisible(false);

            // TextArea to display account information
            accountInfoTextArea = new TextArea();
            accountInfoTextArea.setEditable(false); //READ-ONLY
            accountInfoTextArea.setWrapText(true); //TEXT WRAPPER
            accountInfoTextArea.setVisible(false); //Not visible on startup

            //Button to clear the form
            Button clearButton = new Button("Clear");
            clearButton.setOnAction(e -> clearForm());

            // Button to exit the application
            Button exitButton = new Button("Exit");
            exitButton.setOnAction(e -> primaryStage.close());

            // HBox for the Clear and Exit buttons
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(clearButton, exitButton);

            // VBox for the entire layout
            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER); //Center content horizontally
            vbox.setPadding(new Insets(20, 20, 20, 20));
            //Add all labels buttons etc to VBOX
            vbox.getChildren().addAll(welcomeLabel, fileOpenerButton, accountNumberBox, passwordBox, checkButton,
                    securityQuestionLabel, securityQuestionTextField, checkSecurityQuestionButton, accountInfoTextArea,
                    buttonBox);
            Scene scene = new Scene(vbox, 600, 450);
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    private void openFile(Stage primaryStage) throws ExceptionHandling.CustomFileNotFoundException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File");

        try {
            // You can change the parameter here to test the Custom FNF Exception
            accounts = reader.readRandomAccessFile(fileChooser.showOpenDialog(primaryStage).getPath(), accounts);

            // Process accounts as needed
            for (Account account : accounts) {
                System.out.println(account.toString());
            }
        } catch (ExceptionHandling.CustomFileNotFoundException | IOException e) {
            // Handle the custom exception
            JOptionPane.showMessageDialog(null, e.getMessage() + "Please Select Other File ", "File Processing Error", JOptionPane.ERROR_MESSAGE);

            // Allow the user to select another input file
            File newSelectedFile = fileChooser.showOpenDialog(primaryStage);

            //If user actually selected something
            if (newSelectedFile != null) {
                try {
                    // Try reading the selected file again
                    accounts = reader.readRandomAccessFile(newSelectedFile.getPath(), accounts);
                    for (Account account : accounts) {
                        System.out.println(account.toString());
                    }
                } catch (ExceptionHandling.CustomFileNotFoundException | IOException ex) {
                    // Handle the exception or propagate it as needed if the same issue happens again
                    ex.printStackTrace();
                }
            } else {
                System.out.println("File selection canceled.");
                // Handle the case where the user canceled file selection
            }
        }
    }
    private void checkAccount(String enteredAccountNumber, String enteredPassword) {
        boolean accountFound = false;
        //if the accounts array list isnt empty
        if (accounts != null) {
            //For each account object in the account array list
            for (Account account : accounts) {
                if (account.getAcctNumber() == Integer.parseInt(enteredAccountNumber)
                        && account.getHashedPassword().equals(enteredPassword)) {
                    System.out.println("Account Found: " + account.toString());
                    showSecurityQuestion(account.getSecurityQuestion());
                    accountFound = true;
                    break;
                }
            }
            if(!accountFound) {
                System.out.println("Account not found or incorrect password");
                hideSecurityQuestion();
            }
        } else {
            System.out.println("Please open a file first.");
            hideSecurityQuestion();
        }
    }

    private void showSecurityQuestion(String question) {
        securityQuestionLabel.setText("Security Question: " + question);
        securityQuestionLabel.setVisible(true);
        securityQuestionTextField.setVisible(true);
        checkSecurityQuestionButton.setVisible(true);
    }

    private void hideSecurityQuestion() {
        securityQuestionLabel.setVisible(false);
        securityQuestionTextField.setVisible(false);
        checkSecurityQuestionButton.setVisible(false);
    }

    private void checkSecurityQuestion() {
        if (accounts != null &&!accounts.isEmpty()) {
            String enteredAccountNumber = accountNumberTextField.getText();
            String enteredPassword = passwordTextField.getText();

            Account currentAccount = null;
            //This finds the account with the entered account number and password
            for (Account account : accounts) {
                if (account.getAcctNumber() == Integer.parseInt(enteredAccountNumber)
                        && account.getHashedPassword().equals(enteredPassword)) {
                    currentAccount = account;
                    break;
                }
            }
            if (currentAccount != null) {
                String enteredAnswer = securityQuestionTextField.getText();
                //If the current account objects security question attribute equals user input
                if (currentAccount.getSecurityAnswer().equals(enteredAnswer)) {
                    System.out.println("Security Question Answer is correct.");
                    updateAndDisplayAccountInfo(currentAccount);
                } else {
                    System.out.println("Incorrect Security Question Answer");
                }
            } else {
                System.out.println("Account not found or incorrect password");
            }
        } else {
            System.out.println("Please open a file first");
        }
    }

    private void updateAndDisplayAccountInfo(Account account) {
        //Format the balance in currency format. Using US DOLLARS
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedBalance = currencyFormat.format(account.getAccountBalance());

        //Display status of account based on switch statement
        String statusDescription = switch (account.getAccountStatus()) {
            case 'A' -> "Active and in good standing.";
            case 'D' -> "Delinquent and not in good standing.";
            case 'C' -> "Account closed.";
            default -> "Unknown status";
        };
        accountInfoTextArea.setVisible(true);
        accountInfoTextArea.setText("Your Personal Account information\n" +"Balance: " + formattedBalance + "\n" +
                "Status: " + statusDescription + "\n" +
                "Credit Limit: " + currencyFormat.format(account.getAccountLimit()));
    }

    //This method clears all the contents of all controls and hides private security information
    private void clearForm() {
        accountNumberTextField.clear();
        passwordTextField.clear();
        securityQuestionTextField.clear();
        accountInfoTextArea.clear();
        accountInfoTextArea.setVisible(false);
        securityQuestionLabel.setVisible(false);
        securityQuestionTextField.setVisible(false);
        checkSecurityQuestionButton.setVisible(false);
    }
}