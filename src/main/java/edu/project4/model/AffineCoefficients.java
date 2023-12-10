package edu.project4.model;


import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public record AffineCoefficients(double a, double b, double c, double d, double e, double f, Color color) {

    private static final int COLOR_BOUND = 256;

    public static AffineCoefficients create() {
        while (true) {
            double a = ThreadLocalRandom.current().nextDouble(-1, 1);
            double b = ThreadLocalRandom.current().nextDouble(-1, 1);
            double c = ThreadLocalRandom.current().nextDouble(-1, 1);
            double d = ThreadLocalRandom.current().nextDouble(-1, 1);
            double e = ThreadLocalRandom.current().nextDouble(-1, 1);
            double f = ThreadLocalRandom.current().nextDouble(-1, 1);
            if (isCoefValid(a, b, c, d, e, f)) {
                return new AffineCoefficients(a, b, c, d, e, f, getRandomColor());
            }
        }
    }

    private static boolean isCoefValid(double a, double b, double c, double d, double e, double f) {
        return (a * a + d * d < 1)
            && (b * b + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d));
    }

    private static Color getRandomColor() {
        return new Color(
            ThreadLocalRandom.current().nextInt(0, COLOR_BOUND),
            ThreadLocalRandom.current().nextInt(0, COLOR_BOUND),
            ThreadLocalRandom.current().nextInt(0, COLOR_BOUND)
        );
    }
}
