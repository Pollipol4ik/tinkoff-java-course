package edu.project4;


import edu.project4.processor.GammaCorrectionImageProcessor;
import edu.project4.processor.ImageProcessor;
import edu.project4.renderer.ParallelRenderer;
import edu.project4.transformation.SphereTransformation;
import edu.project4.model.AffineCoefficients;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FractalMainTest {

    @Test
    @DisplayName("Correct test")
    public void process_correctImage_whenUsedMultiThread() {
        FractalImage fractalImage = FractalImage.create(7680, 4320);

        ParallelRenderer.renderAsync(
            fractalImage,
            new Rect(-3.5, -2.0, 3.5, 2.0),
            List.of(new SphereTransformation()),
            Stream.generate(AffineCoefficients::new).limit(3).toList(),
            2,
            10000,
            (short) 5,
            6
        );

        ImageProcessor gammaCorrection = new GammaCorrectionImageProcessor();
        assertDoesNotThrow(() -> gammaCorrection.process(fractalImage));
    }

}
