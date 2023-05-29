import java.math.BigInteger;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputString = scanner.nextLine();

        BigInteger limit;
        try {
            limit = new BigInteger(inputString);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return;
        }

        BigInteger factorial = BigInteger.ONE;
        long i = 1;

        while (factorial.compareTo(limit) < 0) {
            factorial = factorial.multiply(BigInteger.valueOf(++i));
        }

        System.out.println(i);
    }
}