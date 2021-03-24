public class Pot {
    private final int honeyMax;
    private int curr = 0;

    public Pot(int honeyMax) {
        this.honeyMax = honeyMax;
    }

    public synchronized void addHoney(){
        while(isFull()){
            try {
                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        curr++;
        System.out.println("Bee bring honey to the pot. Now size is: " + curr);
    }

    synchronized boolean isFull() {
        return honeyMax == curr;
    }

    synchronized void eatAllHoney() {
        System.out.println("Winnie Pooh ate all honey :(");
        curr = 0;
        notifyAll();
    }
}