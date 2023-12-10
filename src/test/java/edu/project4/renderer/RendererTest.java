package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformation.DiscTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.PolarTransformation;
import java.util.List;
import edu.project4.transformation.SinusTransformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RendererTest {

    @Test
    @DisplayName("DefaultRenderer#render test")
    public void defaultRender_shouldNotThrowAnyException_whenEverythingIsCorrect() {
        Assertions.assertDoesNotThrow(() ->
            new DefaultRenderer().render(
                FractalImage.create(400, 400),
                new Rect(-4, -3, 8, 6),
                List.of(
                    new DiscTransformation(),
                    new SinusTransformation()
                ),
                1000, 1000, 2
            )
        );
    }

    @Test
    @DisplayName("ParralelRenderer#render test")
    public void parallelRender_shouldNotThrowAnyException_whenEverythingIsCorrect() {
        Assertions.assertDoesNotThrow(() ->
            new ParallelRenderer().render(
                FractalImage.create(400, 400),
                new Rect(-4, -3, 8, 6),
                List.of(
                    new HeartTransformation(),
                    new PolarTransformation()
                ),
                1000, 1000, 2
            )
        );
    }
}
