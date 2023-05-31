class Main {
    public static void main(String... args) {
        ClassLoader classLoader = Main.class.getClassLoader();
        System.out.println(classLoader.getName());
    }
}