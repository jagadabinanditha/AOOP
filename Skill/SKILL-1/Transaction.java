
public class Transaction {

    String reference;
    TransactionType type;
    double amount;
    String description;

    public Transaction(String reference, TransactionType type,
                       double amount, String description) {

        this.reference = reference;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public void display() {

        System.out.println("Reference: " + reference);
        System.out.println("Type: " + type);
        System.out.println("Amount: " + amount);
        System.out.println("Description: " + description);
    }
}
