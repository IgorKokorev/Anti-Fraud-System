import java.math.BigDecimal;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BigDecimal bd1 = new BigDecimal(scanner.nextLine());
        BigDecimal bd2 = new BigDecimal(scanner.nextLine());
        BigDecimal bd3 = new BigDecimal(scanner.nextLine());

        System.out.println(bd1.add(bd2).add(bd3));
    }
}