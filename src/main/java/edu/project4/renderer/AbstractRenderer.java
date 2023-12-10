package edu.project4.renderer;

import edu.project4.model.AffineCoefficients;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformation.Transformation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static edu.project4.util.PointUtils.rotate;

public class AbstractRenderer {

    private static final int FOCUS_STEPS = 20;

    protected AbstractRenderer() {
    }

    protected static void renderOneSample(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        List<AffineCoefficients> affineFunctions,
        int symmetry,
        short iterPerSample
    ) {
        var random = ThreadLocalRandom.current();
        Point currentPoint = world.getRandom();

        for (short step = -FOCUS_STEPS; step < iterPerSample; ++step) {
            AffineCoefficients currentFunction = getRandomAffineFunction(random, affineFunctions);
            currentPoint = transformPoint(currentFunction, currentPoint);

            Transformation currentTransformation = getRandomTransformation(random, variations);
            currentPoint = applyTransformation(currentTransformation, currentPoint);

            if (step > 0) {
                updateCanvasWithSymmetry(canvas, world, currentPoint, symmetry, currentFunction);
            }
        }
    }

    private static AffineCoefficients getRandomAffineFunction(
        ThreadLocalRandom random, List<AffineCoefficients> affineFunctions
    ) {
        return affineFunctions.get(random.nextInt(affineFunctions.size()));
    }

    private static Point transformPoint(AffineCoefficients function, Point point) {
        return function.transform(point);
    }

    private static Transformation getRandomTransformation(
        ThreadLocalRandom random, List<Transformation> variations
    ) {
        return variations.get(random.nextInt(variations.size()));
    }

    private static Point applyTransformation(Transformation transformation, Point point) {
        return transformation.apply(point);
    }

    private static void updateCanvasWithSymmetry(
        FractalImage canvas, Rect world, Point point, int symmetry, AffineCoefficients function
    ) {
        double theta = 0.0;

        for (int s = 0; s < symmetry; theta += Math.PI * 2 / symmetry, ++s) {
            Point rotatedPoint = rotate(point, theta);

            if (world.isContain(rotatedPoint)) {
                Pixel currentPixel = getPixel(rotatedPoint, world, canvas);
                currentPixel.updatePixel(function.getRgb());
            }
        }
    }

    private static Pixel getPixel(Point point, Rect world, FractalImage canvas) {
        int centerCanvasY = canvas.getHeight() / 2;
        int centerCanvasX = canvas.getWidth() / 2;

        int newY = (int) ((point.y() / ((world.height() - world.y()) / 2)) * centerCanvasY);
        int newX = (int) ((point.x() / ((world.width() - world.x()) / 2)) * centerCanvasX);

        return canvas.pixel(newX + centerCanvasX, newY + centerCanvasY);
    }
}
