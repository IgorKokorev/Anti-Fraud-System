import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalTime time1 = LocalTime.parse(scanner.nextLine());
        LocalTime time2 = LocalTime.parse(scanner.nextLine());

        int diffInSeconds = time2.getSecond() - time1.getSecond();
        diffInSeconds += (time2.getMinute() - time1.getMinute()) * 60;
        diffInSeconds += (time2.getHour() - time1.getHour()) * 3600;

//        if (diffInSeconds < 0) diffInSeconds += 60 * 60 * 24;

        System.out.println(Math.abs(diffInSeconds));
    }
}