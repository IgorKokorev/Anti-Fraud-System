class NumbersThread extends Thread {
    int from;
    int to;
    public NumbersThread(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        super.run();
        for (int i = from; i <= to; i++) {
            System.out.println(i);
        }
    }
}