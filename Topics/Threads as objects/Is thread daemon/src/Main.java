class ThreadUtil {
    public static void printIfDaemon(Thread thread) {
        if (!thread.isDaemon()) System.out.print("not ");
        System.out.println("daemon");
    }
}