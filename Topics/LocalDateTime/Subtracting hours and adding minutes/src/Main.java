import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalDateTime datetime = LocalDateTime.parse(scanner.next());
        int hoursToSubtract = scanner.nextInt();
        int minutesToAdd = scanner.nextInt();

        System.out.println(datetime.minusHours(hoursToSubtract).plusMinutes(minutesToAdd));
    }
}