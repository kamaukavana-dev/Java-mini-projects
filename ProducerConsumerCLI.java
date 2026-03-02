import java.util.LinkedList;
import java.util.Queue;

// Shared buffer between Producer and Consumer
class SharedBuffer {
    private Queue<Integer> queue = new LinkedList<>();
    private int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    // Producer puts items into the buffer
    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            // Buffer full → wait until consumer takes something
            wait();
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notifyAll(); // wake up consumers waiting
    }

    // Consumer takes items from the buffer
    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            // Buffer empty → wait until producer adds something
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notifyAll(); // wake up producers waiting
        return item;
    }
}

// Producer thread
class Producer implements Runnable {
    private SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                buffer.produce(i); // produce items 1–10
                Thread.sleep(300); // simulate work
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Consumer thread
class Consumer implements Runnable {
    private SharedBuffer buffer;

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                buffer.consume(); // consume items
                Thread.sleep(500); // simulate slower consumption
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ProducerConsumerCLI {
    public static void main(String[] args) {
        System.out.println("=== Producer–Consumer Demo ===");

        SharedBuffer buffer = new SharedBuffer(5); // buffer capacity = 5

        Thread producerThread = new Thread(new Producer(buffer));
        Thread consumerThread = new Thread(new Consumer(buffer));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=== Demo Complete ===");
    }
}
