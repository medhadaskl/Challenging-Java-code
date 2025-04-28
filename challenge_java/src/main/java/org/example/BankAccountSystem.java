package org.example;
import java.util.Scanner;
// Base class BankAccount
abstract class BankAccount {
    private String accountNumber;
    private double balance;


    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdraw amount or insufficient balance");
        }

    }

    public abstract void applyInterest();
}

// Subclass SavingsAccount
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest applied: " + interest);
    }
}

// Subclass CurrentAccount
class CurrentAccount extends BankAccount {
    private double overdraftLimit;
    private double interestRate;

    public CurrentAccount(String accountNumber, double balance, double overdraftLimit, double interestRate) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= getBalance() + overdraftLimit) {
            double currentBalance = getBalance();
            double newBalance = currentBalance - amount;
            if (newBalance < 0) {
                overdraftLimit += newBalance; // Adjust overdraft
            }
            // Directly update balance using reflection or better: change design
            // Instead, safer fix: Add protected method in BankAccount to allow controlled update
            setBalance(newBalance);
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdraw amount or exceeds overdraft limit");
        }
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest applied: " + interest);
    }
}


// Subclass FixedDepositAccount
class FixedDepositAccount extends BankAccount {
    private double interestRate;
    private int lockInPeriod; // in months
    private int monthsPassed;

    public FixedDepositAccount(String accountNumber, double balance, double interestRate, int lockInPeriod) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
        this.lockInPeriod = lockInPeriod;
        this.monthsPassed = 0;
    }

    public void passMonth() {
        monthsPassed++;
        if (monthsPassed >= lockInPeriod) {
            applyInterest();
        }
    }

    @Override
    public void withdraw(double amount) {
        if (monthsPassed >= lockInPeriod) {
            super.withdraw(amount);
        } else {
            System.out.println("Cannot withdraw before lock-in period ends");
        }
    }

    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest applied: " + interest);
    }
}

// Main class
public class BankAccountSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account type (savings, current, fixed): ");
        String type = scanner.nextLine();

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();

        BankAccount account;
        switch (type.toLowerCase()) {
            case "savings":
                System.out.print("Enter interest rate: ");
                double savingsInterestRate = scanner.nextDouble();
                account = new SavingsAccount(accountNumber, balance, savingsInterestRate);
                break;
            case "current":
                System.out.print("Enter overdraft limit: ");
                double overdraftLimit = scanner.nextDouble();
                System.out.print("Enter interest rate: ");
                double currentInterestRate = scanner.nextDouble();
                account = new CurrentAccount(accountNumber, balance, overdraftLimit,currentInterestRate);
                break;
            case "fixed":
                System.out.print("Enter interest rate: ");
                double fixedInterestRate = scanner.nextDouble();
                System.out.print("Enter lock-in period (months): ");
                int lockInPeriod = scanner.nextInt();
                account = new FixedDepositAccount(accountNumber, balance, fixedInterestRate, lockInPeriod);
                break;
            default:
                throw new IllegalArgumentException("Unknown account type");
        }

        System.out.print("Enter amount to deposit: ");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);

        System.out.print("Enter amount to withdraw: ");
        double withdrawAmount = scanner.nextDouble();
        account.withdraw(withdrawAmount);

        account.applyInterest();

        System.out.println("Final balance: " + account.getBalance());
    }
}
