import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrencyDemo {

    public static void main(String[] args) throws Exception {

        System.out.println("Java version: " +
                System.getProperty("java.version"));

        // --------------------------------------------------
        // 1. Basic platform threads
        // --------------------------------------------------

        System.out.println("\n=== PLATFORM THREADS ===");

        Thread thread1 = new Thread(() -> {
            simulateTask("Platform Thread 1");
        });

        Thread thread2 = new Thread(() -> {
            simulateTask("Platform Thread 2");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


        // --------------------------------------------------
        // 2. ExecutorService with platform threads
        // --------------------------------------------------

        System.out.println("\n=== FIXED THREAD POOL ===");

        try (ExecutorService executor =
                     Executors.newFixedThreadPool(4)) {

            List<Future<String>> results = new ArrayList<>();

            for (int i = 1; i <= 8; i++) {

                int taskId = i;

                Future<String> future = executor.submit(() -> {

                    simulateTask("Task " + taskId);

                    return "Task " + taskId +
                            " completed by " +
                            Thread.currentThread();
                });

                results.add(future);
            }

            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        }


        // --------------------------------------------------
        // 3. Virtual threads
        // --------------------------------------------------

        System.out.println("\n=== VIRTUAL THREADS ===");

        try (ExecutorService virtualExecutor =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<String>> results = new ArrayList<>();

            for (int i = 1; i <= 20; i++) {

                int taskId = i;

                Future<String> future =
                        virtualExecutor.submit(() -> {

                            simulateTask(
                                    "Virtual Task " + taskId
                            );

                            return "Virtual Task " + taskId +
                                    " completed by " +
                                    Thread.currentThread();
                        });

                results.add(future);
            }

            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        }


        // --------------------------------------------------
        // 4. Creating virtual threads directly
        // --------------------------------------------------

        System.out.println("\n=== DIRECT VIRTUAL THREAD ===");

        Thread virtualThread = Thread.startVirtualThread(() -> {

            System.out.println(
                    "Running inside: " +
                            Thread.currentThread()
            );

        });

        virtualThread.join();


        // --------------------------------------------------
        // 5. CompletableFuture
        // --------------------------------------------------

        System.out.println("\n=== COMPLETABLE FUTURE ===");

        CompletableFuture<String> user =
                CompletableFuture.supplyAsync(() -> {

                    simulateTask("Loading user");

                    return "Daniel";

                });

        CompletableFuture<String> orders =
                CompletableFuture.supplyAsync(() -> {

                    simulateTask("Loading orders");

                    return "5 orders";

                });

        CompletableFuture<String> result =
                user.thenCombine(
                        orders,
                        (username, orderCount) ->
                                username + " has " + orderCount
                );

        System.out.println(result.get());





