package edu.project4.renderer;

import edu.project4.model.AffineCoefficients;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformation.Transformation;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultRenderer extends AbstractRenderer {
    public static void renderSync(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        List<AffineCoefficients> affineFunctions,
        int symmetry,
        int samples,
        short iterPerSample
    ) {
        for (int num = 0; num < samples; ++num) {
            renderOneSample(canvas, world, variations, affineFunctions, symmetry, iterPerSample);
        }
    }
}
