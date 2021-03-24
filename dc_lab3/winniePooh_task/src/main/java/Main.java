public class Main {
    public static void main(String[] args) {
        int beesNumber = 3;
        Pot honey = new Pot(5);
        Bear bear = new Bear(honey);
        new Thread(bear).start();

        for (int i = 0; i < beesNumber; i++) {
            Runnable bee = new Bee(honey,bear);
            new Thread(bee).start();
        }
    }
}