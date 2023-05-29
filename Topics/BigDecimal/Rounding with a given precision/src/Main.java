import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BigDecimal bd = new BigDecimal(scanner.nextLine());
        int scale = scanner.nextInt();

        System.out.println(bd.setScale(scale, RoundingMode.HALF_DOWN));
    }   
}