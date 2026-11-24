
import java.util.*;

public class BankAccount {

    String accountNumber;
    String email;
    String branch;

    double balance;
    double overdraftLimit;

    AccountStatus status;

    ArrayList<Transaction> transactions = new ArrayList<>();

    public BankAccount(String number, String email,
                       String branch, double balance,
                       double limit) {

        if (balance < 0 || limit < 0) {
            throw new IllegalArgumentException("Invalid balance or limit");
        }

        this.accountNumber = number;
        this.email = email;
        this.branch = branch;
        this.balance = balance;
        this.overdraftLimit = limit;
        this.status = AccountStatus.ACTIVE;
    }

    public void deposit(double amount) {

        if (status != AccountStatus.ACTIVE) {
            System.out.println("Account is not active");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        balance += amount;
        System.out.println("Amount deposited: " + amount);

        addTransaction(TransactionType.DEPOSIT, amount);
    }

    public void withdraw(double amount) {

        if (status != AccountStatus.ACTIVE) {
            System.out.println("Account is not active");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount > balance + overdraftLimit) {
            System.out.println("Overdraft limit exceeded!");
            return;
        }

        balance -= amount;
        System.out.println("Amount withdrawn: " + amount);

        addTransaction(TransactionType.WITHDRAWAL, amount);
    }

    public void balanceEnquiry() {
        System.out.println("Current Balance: " + balance);
        addTransaction(TransactionType.BALANCE_ENQUIRY, 0);
    }

    public void freezeAccount() {
        if (status == AccountStatus.ACTIVE) {
            status = AccountStatus.FROZEN;
            System.out.println("Account frozen");
        }
    }

    public void unfreezeAccount() {
        if (status == AccountStatus.FROZEN) {
            status = AccountStatus.ACTIVE;
            System.out.println("Account unfrozen");
        }
    }

    public void closeAccount() {
        if (balance == 0 && status != AccountStatus.CLOSED) {
            status = AccountStatus.CLOSED;
            System.out.println("Account closed");
        } else {
            System.out.println("Cannot close account. Balance must be zero.");
        }
    }

    public void addTransaction(TransactionType type, double amount) {

        String ref = UUID.randomUUID().toString();

        transactions.add(new Transaction(
            ref, type, amount, "Bank transaction"
        ));
    }

    public void showTransactions() {

        System.out.println("\n--- Transaction History ---");

        for (Transaction t : transactions) {
            t.display();
            System.out.println("------------------");
        }
    }

    public void displayAccount() {

        System.out.println("\nAccount Number: " + accountNumber);
        System.out.println("Email: " + email);
        System.out.println("Branch: " + branch);
        System.out.println("Balance: " + balance);
        System.out.println("Overdraft Limit: " + overdraftLimit);
        System.out.println("Status: " + status);
    }
}
