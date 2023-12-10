package edu.project4;

import edu.project4.model.AffineCoefficients;
import edu.project4.model.Format;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.processor.GammaCorrectionImageProcessor;
import edu.project4.processor.ImageProcessor;
import edu.project4.renderer.ParallelRenderer;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.LinearTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusTransformation;
import edu.project4.transformation.SphereTransformation;
import edu.project4.util.ImageUtils;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings({"MagicNumber", "RegexpSinglelineJava"})
public class FractalMain {
    public static void main(String[] args) {
        Path root = Path.of("src", "main", "resources");
        FractalImage fractalImage = FractalImage.create(7680, 4320);

        ParallelRenderer.renderAsync(
            fractalImage,
            new Rect(-1.77, -2.0, 3.5, 2.0),
            List.of(
                //new PolarTransformation(),
                new HeartTransformation(),
                new SinusTransformation()
                //new LinearTransformation()
            ),
            Stream.generate(AffineCoefficients::new).limit(5).toList(),
            4,
            1000000,
            (short) 5,
            6
        );

        ImageProcessor gammaCorrection = new GammaCorrectionImageProcessor();
        gammaCorrection.process(fractalImage);
        ImageUtils.save(fractalImage, root, "img", Format.PNG);
    }
}
