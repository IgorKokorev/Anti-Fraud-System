import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalDateTime datetime = LocalDateTime.parse(scanner.next());
        int daysToAdd = scanner.nextInt();
        int hoursToSubtract = scanner.nextInt();

        System.out.println(datetime.plusDays(daysToAdd).plusHours(-hoursToSubtract));
    }
}