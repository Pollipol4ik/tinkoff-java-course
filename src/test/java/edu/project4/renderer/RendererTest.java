package edu.project4.renderer;

import edu.project4.model.AffineCoefficients;
import edu.project4.transformation.SphereTransformation;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RendererTest {

    @Test
    @DisplayName("Тестирование ParallelRenderer#renderAsync")
    public void render_shouldRenderImage_whenUsedMultiThread() {
        FractalImage fractalImage = FractalImage.create(1920, 1080);
        assertDoesNotThrow(() -> ParallelRenderer.renderAsync(
            fractalImage,
            new Rect(-1.777, -1.0, 1.777, 1.0),
            List.of(new SphereTransformation()),
            Stream.generate(AffineCoefficients::new).limit(3).toList(),
            2,
            10000,
            (short) 5,
            6
        ));
    }

    @Test
    @DisplayName("Тестирование SingleThreadRenderer#renderSync")
    public void render_shouldRenderImage_whenUsedOneThread() {
        FractalImage fractalImage = FractalImage.create(1920, 1080);
        assertDoesNotThrow(() -> DefaultRenderer.renderSync(
            fractalImage,
            new Rect(-1.777, -1.0, 1.777, 1.0),
            List.of(new SphereTransformation()),
            Stream.generate(AffineCoefficients::new).limit(3).toList(),
            2,
            10000,
            (short) 5
        ));
    }
}
