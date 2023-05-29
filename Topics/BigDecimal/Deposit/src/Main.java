import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BigDecimal amount = new BigDecimal(scanner.nextLine());
        BigDecimal rate = new BigDecimal(scanner.nextLine());
        BigDecimal increment = rate.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);

        int years = scanner.nextInt();

        System.out.print("Amount of money in the account: ");
        System.out.println(increment.pow(years).multiply(amount).setScale(2, RoundingMode.CEILING));
    }
}