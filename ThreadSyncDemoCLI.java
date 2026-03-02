import java.util.concurrent.locks.ReentrantLock;

// Shared counter using synchronized keyword
class SyncCounter {
    private int count = 0;

    // synchronized ensures only one thread at a time can enter this method
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

// Shared counter using ReentrantLock
class LockCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    // lock() and unlock() give manual control over access
    public void increment() {
        lock.lock();   // acquire the lock (like taking the key)
        try {
            count++;
        } finally {
            lock.unlock(); // always release the lock (return the key)
        }
    }

    public int getCount() {
        return count;
    }
}

// Worker thread that increments a counter many times
class CounterWorker implements Runnable {
    private Object counter; // can be SyncCounter or LockCounter
    private boolean useLock;

    public CounterWorker(Object counter, boolean useLock) {
        this.counter = counter;
        this.useLock = useLock;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 1000; i++) {
            if (useLock) {
                ((LockCounter) counter).increment();
            } else {
                ((SyncCounter) counter).increment();
            }
        }
    }
}

public class ThreadSyncDemoCLI {
    public static void main(String[] args) {
        System.out.println("=== Thread Synchronization Comparison ===");

        // Create counters
        SyncCounter syncCounter = new SyncCounter();
        LockCounter lockCounter = new LockCounter();

        // Create threads for synchronized counter
        Thread s1 = new Thread(new CounterWorker(syncCounter, false));
        Thread s2 = new Thread(new CounterWorker(syncCounter, false));
        Thread s3 = new Thread(new CounterWorker(syncCounter, false));

        // Create threads for lock counter
        Thread l1 = new Thread(new CounterWorker(lockCounter, true));
        Thread l2 = new Thread(new CounterWorker(lockCounter, true));
        Thread l3 = new Thread(new CounterWorker(lockCounter, true));

        // Start synchronized threads
        s1.start(); s2.start(); s3.start();

        // Wait for them to finish
        try {
            s1.join(); s2.join(); s3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print result for synchronized counter
        System.out.println("Synchronized counter result (expected 3000): " + syncCounter.getCount());

        // Start lock threads
        l1.start(); l2.start(); l3.start();

        // Wait for them to finish
        try {
            l1.join(); l2.join(); l3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print result for lock counter
        System.out.println("Lock counter result (expected 3000): " + lockCounter.getCount());

        System.out.println("=== Demo Complete ===");
    }
}
