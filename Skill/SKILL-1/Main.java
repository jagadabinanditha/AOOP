
public class Main {

    public static void main(String[] args) {

        BankAccount account = new BankAccount(
            "123456789",
            "nissy@gmail.com",
            "Hyderabad",
            5000,
            2000
        );

        System.out.println("INITIAL ACCOUNT DETAILS");
        account.displayAccount();

        System.out.println("\n--- DEPOSIT ---");
        account.deposit(2000);

        System.out.println("\n--- WITHDRAWAL ---");
        account.withdraw(3000);

        System.out.println("\n--- INVALID TRANSACTIONS ---");
        account.deposit(-500);
        account.withdraw(10000);

        System.out.println("\n--- BALANCE ENQUIRY ---");
        account.balanceEnquiry();

        System.out.println("\n--- FINAL ACCOUNT DETAILS ---");
        account.displayAccount();

        account.showTransactions();
    }
}
