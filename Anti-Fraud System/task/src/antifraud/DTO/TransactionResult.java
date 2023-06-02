package antifraud.DTO;

public class TransactionResult {
    public enum Result {ALLOWED, MANUAL_PROCESSING, PROHIBITED};

    private Result result;

    public TransactionResult(Result result) {
        this.result = result;
    }

    public TransactionResult() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
