package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;
//This application is an ATM machine that allows users to withdraw money and handles insufficient funds exceptions
// Custom exception class

// Custom exception to indicate that there are insufficient funds for a withdrawal.
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// ATM Class
class ATM {
    private double balance;

    public ATM(double initialBalance) {
        this.balance = initialBalance;
    }

// throws InsufficientFundsException if there are insufficient funds for the withdrawal.
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Withdrawal failed: Insufficient funds. Attempted: " + amount + ", Available: " + balance);
        }
        balance -= amount;
        System.out.println("Withdrawal successful: " + amount);
    }

    public double getBalance() {
        return balance;
    }
}

// Main class
public class ATMApp {
    public static void main(String[] args) {
        ATM atm = new ATM(12000); // Initial balance
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        try {
            atm.withdraw(amount);
        } catch (InsufficientFundsException e) {
            logError(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Remaining balance: " + atm.getBalance());
        }

        scanner.close();
    }
// Logs an error message to a file.
    private static void logError(String message) {
        try (FileWriter fw = new FileWriter("error.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(LocalDateTime.now() + " - " + message);
        } catch (IOException ex) {
            System.out.println("Failed to write error log: " + ex.getMessage());
        }
    }
}
