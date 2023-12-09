package edu.hw9.task1;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class StatsCollectorTest {

    @Test
    @DisplayName("Stats collector test")
    public void statsCollector_test1() throws InterruptedException {
        StatsCollector collector = new StatsCollector(4);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> collector.push("metric1", new double[]{1, 4, 3, 4, 5}));
        executorService.execute(() -> collector.push("metric2", new double[]{1, 9, 3, 4, 5}));
        executorService.execute(() -> collector.push("metric3", new double[]{0, 44, 3, 4, 5}));

        var stats = collector.stats();
        Thread.sleep(500);
        assertThat(collector.stats()).isNotEmpty();
        assertThat(stats).containsExactlyInAnyOrder(
            new Stat("metric1", 17, 3.4, 5, 1),
            new Stat("metric2", 22, 4.4, 9, 1),
            new Stat("metric3", 56, 11.2, 44, 0)
        );
        executorService.shutdown();
        collector.shutdownCollector();
    }

}
