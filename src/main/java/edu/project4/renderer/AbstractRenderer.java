package edu.project4.renderer;



import edu.project4.model.AffineCoefficients;
import edu.project4.model.Pixel;
import edu.project4.model.Point;

public abstract class AbstractRenderer implements Renderer {

    protected static final int START = -20;

    protected Point getRotatedPoint(Point pw, double theta) {
        double xRot = pw.x() * Math.cos(theta) - pw.y() * Math.sin(theta);
        double yRot = pw.x() * Math.sin(theta) + pw.y() * Math.cos(theta);
        return new Point(xRot, yRot);
    }

    protected void setPixelColor(Pixel pixel, AffineCoefficients coefficients) {
        if (pixel.getHitCount() == 0) {
            pixel.setR(coefficients.color().getRed());
            pixel.setG(coefficients.color().getGreen());
            pixel.setB(coefficients.color().getBlue());
        } else {
            pixel.setR((pixel.getR() + coefficients.color().getRed()) / 2);
            pixel.setG((pixel.getG() + coefficients.color().getGreen()) / 2);
            pixel.setB((pixel.getB() + coefficients.color().getBlue()) / 2);
        }
    }

    protected AffineCoefficients[] getRandomAffineCoefficients(int samples) {
        AffineCoefficients[] transformations = new AffineCoefficients[samples];
        for (int i = 0; i < samples; i++) {
            transformations[i] = AffineCoefficients.create();
        }
        return transformations;
    }

    protected Point getPointAfterAffineTransformation(AffineCoefficients coefficients, Point pw) {
        double x = getNewXUsingCoefficients(coefficients, pw);
        double y = getNewYUsingCoefficients(coefficients, pw);
        return new Point(x, y);
    }

    private double getNewXUsingCoefficients(AffineCoefficients coefficients, Point pw) {
        return coefficients.a() * pw.x() + coefficients.b() * pw.y()
            + coefficients.c();
    }

    private double getNewYUsingCoefficients(AffineCoefficients coefficients, Point pw) {
        return coefficients.d() * pw.x() + coefficients.e() * pw.y()
            + coefficients.f();
    }
}
