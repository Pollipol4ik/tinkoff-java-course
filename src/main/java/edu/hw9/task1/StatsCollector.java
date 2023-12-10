package edu.hw9.task1;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StatsCollector {
    private final static Integer TIMEOUT = 500;
    private final List<Stat> stats = new CopyOnWriteArrayList<>();
    private final AtomicInteger currentlyExecuted = new AtomicInteger();
    private final ExecutorService executor;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public StatsCollector(Integer numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void push(String name, double[] data) {
        executor.execute(() -> {
            currentlyExecuted.getAndIncrement();
            stats.add(getStats(name, data));
            currentlyExecuted.decrementAndGet();
        });
    }

    public List<Stat> stats() {
        while (currentlyExecuted.get() != 0 && isShutdown.get()) {
        }
        return stats;
    }

    public void shutdownCollector() {
        isShutdown.set(true);
        try {
            if (!executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {
                    throw new RuntimeException("Executor service does`t terminated");
                }
            }
        } catch (InterruptedException | RuntimeException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private Stat getStats(String name, double[] data) {
        return Stat.builder()
            .name(name)
            .sum(getSum(data))
            .average(getAverage(data))
            .max(getMax(data))
            .min(getMin(data))
            .build();
    }

    private double getSum(double[] data) {
        return Arrays.stream(data).sum();
    }

    private double getAverage(double[] data) {
        return getSum(data) / data.length;
    }

    private double getMax(double[] data) {
        return Arrays.stream(data).max().orElseThrow();
    }

    private double getMin(double[] data) {
        return Arrays.stream(data).min().orElseThrow();
    }
}
