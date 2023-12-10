package edu.project4.processor;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;

public class GammaCorrectionImageProcessor implements ImageProcessor {

    private static final double GAMMA = 1.9;

    @Override
    public void process(FractalImage image) {
        double max = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Pixel curPixel = image.getPixel(x, y);
                if (curPixel.getHitCount() != 0) {
                    curPixel.setNormal(Math.log10(curPixel.getHitCount()));
                    max = Math.max(max, curPixel.getNormal());
                }
            }
        }
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Pixel curPixel = image.getPixel(x, y);
                curPixel.setNormal(curPixel.getNormal() / max);
                curPixel.setR(
                    (int) (curPixel.getR() * Math.pow(curPixel.getNormal(), (1.0 / GAMMA))));
                curPixel.setG(
                    (int) (curPixel.getG() * Math.pow(curPixel.getNormal(), (1.0 / GAMMA))));
                curPixel.setB(
                    (int) (curPixel.getB() * Math.pow(curPixel.getNormal(), (1.0 / GAMMA))));
            }
        }
    }
}
