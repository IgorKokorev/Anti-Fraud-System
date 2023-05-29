import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine().trim();

        String regexp = "\\d+(\\.\\d+){3}";
        if(!ip.matches(regexp)) {
            System.out.println("NO");
            return;
        }

        String[] split = ip.split("\\.");
        boolean isCorrect = true;
        for (String part: split) {
            if (Integer.parseInt(part) >= 256) {
                isCorrect = false;
                break;
            }
        }
        System.out.println(isCorrect ? "YES" : "NO");
    }
}