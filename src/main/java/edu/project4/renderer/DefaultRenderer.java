package edu.project4.renderer;


import edu.project4.model.AffineCoefficients;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultRenderer extends AbstractRenderer {

    @Override
    public FractalImage render(
        FractalImage canvas, Rect world, List<Transformation> transformations,
        int samples, int iterPerSample, int symmetry
    ) {
        AffineCoefficients[] coefficientsArray = getRandomAffineCoefficients(samples);
        for (int num = 0; num < samples; num++) {
            Point pw = world.getRandomPoint();
            for (int step = START; step < iterPerSample; step++) {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, coefficientsArray.length);
                AffineCoefficients coefficients = coefficientsArray[randomIndex];
                pw = getPointAfterAffineTransformation(coefficients, pw);
                Transformation transformation =
                    transformations.get(ThreadLocalRandom.current().nextInt(0, transformations.size()));
                pw = transformation.apply(pw);
                if (step >= 0) {
                    double theta = 0;
                    for (int s = 0; s < symmetry; s++) {
                        theta += 2 * Math.PI / symmetry;
                        Point pwr = getRotatedPoint(pw, theta);
                        if (!world.doesContainPoint(pwr)) {
                            continue;
                        }
                        Pixel pixel =
                            canvas.getPixel(
                                (int)
                                    ((pwr.x() - world.x()) * canvas.getWidth() / world.width()),
                                (int) ((pwr.y() - world.y()) * canvas.getHeight() / world.height())
                            );
                        if (pixel == null) {
                            continue;
                        }
                        setPixelColor(pixel, coefficients);
                        pixel.setHitCount(pixel.getHitCount() + 1);
                    }
                }
            }
        }
        return canvas;
    }
}
