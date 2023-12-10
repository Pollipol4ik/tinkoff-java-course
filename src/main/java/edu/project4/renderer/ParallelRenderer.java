package edu.project4.renderer;


import edu.project4.model.AffineCoefficients;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformation.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParallelRenderer extends AbstractRenderer {

    @SuppressWarnings("ParameterNumber")
    public static void renderAsync(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        List<AffineCoefficients> affineFunctions,
        int symmetry,
        int samples,
        short iterPerSample,
        int numberOfThreads
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<?>> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            tasks.add(executor.submit(() -> {
                for (int num = 0; num < samples / numberOfThreads; ++num) {
                    renderOneSample(canvas, world, variations, affineFunctions, symmetry, iterPerSample);
                }
            }));
        }

        for (var task : tasks) {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("The generation was completed incorrectly!", e);
            }
        }

        executor.shutdownNow();
    }
}
