package edu.project4;

import edu.project4.processor.GammaCorrectionImageProcessor;
import edu.project4.model.Format;
import edu.project4.model.Rect;
import edu.project4.renderer.ParallelRenderer;
import edu.project4.transformation.DiscTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusTransformation;
import edu.project4.transformation.SphereTransformation;
import java.nio.file.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FractalGeneratorTest {

    @Test
    @DisplayName("#generate test")
    public void generate_shouldGenerateFractalFlameImage(@TempDir Path path) {
        FractalGenerator generator = new FractalGenerator(
            1920,
            1080,
            new ParallelRenderer(),
            new Rect(-4, -3, 8, 6),
            List.of(
                new DiscTransformation(),
                new SinusTransformation(),
                new SphereTransformation(),
                new PolarTransformation(),
                new HeartTransformation()
            ),
            new GammaCorrectionImageProcessor()
        );
        generator.generate(1000, 1000, 2, path.resolve("img.png"), Format.PNG);
        Assertions.assertThat(path.resolve("img.png")).exists();
        System.out.println(path.toAbsolutePath());
    }
}
