package com.example.accountdatagui;

public class Account {
    //Encapsulated Account Data
    private int acctNumber;
    private String hashedPassword;
    private String securityQuestion;
    private String securityAnswer;
    private double accountBalance;
    private char accountStatus;
    private double accountLimit;

    //Account constructor
    public Account(int acctNumber, String hashedPassword, String securityQuestion,
                   String securityAnswer, double accountBalance, char accountStatus, double accountLimit) {
        this.acctNumber = acctNumber;
        this.hashedPassword = hashedPassword;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.accountBalance = accountBalance;
        this.accountStatus = accountStatus;
        this.accountLimit = accountLimit;
    }

    //Getters and setters
    public int getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(int acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public char getAccountStatus(){
        return accountStatus;
    }

    public void setAccountStatus(char accountStatus){
        this.accountStatus = accountStatus;
    }

    public double getAccountLimit(){
        return accountLimit;
    }

    public void setAccountLimit(double accountLimit){
        this.accountLimit = accountLimit;
    }
    @Override
    public String toString() {
        return "Account{" +
                "acctNumber=" + acctNumber +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountStatus=" + accountStatus +
                ", accountLimit=" + accountLimit +
                '}';
    }
}