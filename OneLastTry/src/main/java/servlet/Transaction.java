package servlet;

import java.util.Date;

public class Transaction {
    private int id;
    private String type;
    private double amount;
    private Date transactionDate;
    private String accountNumber; // New field for account number

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAccountNumber() { // New getter
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) { // New setter
        this.accountNumber = accountNumber;
    }
}