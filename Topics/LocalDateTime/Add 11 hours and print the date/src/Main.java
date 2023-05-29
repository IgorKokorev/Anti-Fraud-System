import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalDateTime datetime = LocalDateTime.parse(scanner.nextLine());
        System.out.println(datetime.plusHours(11).toLocalDate());
    }
}