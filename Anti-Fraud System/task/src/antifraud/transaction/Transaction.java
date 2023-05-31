package antifraud.transaction;

public class Transaction {
    private long amount;

    public Transaction(long amount) {
        this.amount = amount;
    }

    public Transaction() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
