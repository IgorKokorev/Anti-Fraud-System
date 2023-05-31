import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        Pattern pattern = Pattern.compile("password\\s*(:|\\s)\\s*[a-zA-Z0-9]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            System.out.println("No passwords found.");
            return;
        }

        do {
            String[] nextPwd = text.substring(matcher.start(), matcher.end()).split("\\s*(:|\\s)\\s*");
            System.out.println(nextPwd[1]);
        } while (matcher.find());
    }
}