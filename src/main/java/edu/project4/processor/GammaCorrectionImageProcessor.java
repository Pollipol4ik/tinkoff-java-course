package edu.project4.processor;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;

public class GammaCorrectionImageProcessor implements ImageProcessor {

    private static final double GAMMA_CONSTANT = 1.7;

    @Override
    public void process(FractalImage image) {
        double maxCorrectionValue = findMaxCorrectionValue(image);
        double gamma = GAMMA_CONSTANT;

        normalizeAndApplyGammaCorrection(image, maxCorrectionValue, gamma);
    }

    private double findMaxCorrectionValue(FractalImage image) {
        double maxCorrectionValue = 0.0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = image.pixel(x, y);
                if (pixel.getHitCount() != 0) {
                    pixel.setCorrectionValue(Math.log10(pixel.getHitCount()));
                    maxCorrectionValue = Math.max(maxCorrectionValue, pixel.getCorrectionValue());
                }
            }
        }

        return maxCorrectionValue;
    }

    private void normalizeAndApplyGammaCorrection(FractalImage image, double maxCorrectionValue, double gamma) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = image.pixel(x, y);

                double normalizedCorrection = pixel.getCorrectionValue() / maxCorrectionValue;
                pixel.setCorrectionValue(normalizedCorrection);

                pixel.setR((int) (pixel.getR() * Math.pow(normalizedCorrection, (1.0 / gamma))));
                pixel.setG((int) (pixel.getG() * Math.pow(normalizedCorrection, (1.0 / gamma))));
                pixel.setB((int) (pixel.getB() * Math.pow(normalizedCorrection, (1.0 / gamma))));
            }
        }
    }
}
