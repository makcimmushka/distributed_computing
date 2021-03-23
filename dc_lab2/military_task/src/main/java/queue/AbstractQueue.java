package queue;

public abstract class AbstractQueue<T> {
    private T stuff;
    private boolean isValueSet = false;

    public synchronized void put(T stuff) throws InterruptedException {
        while (isValueSet) {
            wait();
        }

        this.stuff = stuff;
        isValueSet = true;
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (!isValueSet) {
            wait();
        }

        isValueSet = false;
        notifyAll();
        return this.stuff;
    }
}
