package edu.project4.util;

import edu.project4.model.Format;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtils {

    @SneakyThrows
    public static void save(FractalImage image, Path fileName, Format format) {
        BufferedImage bufferedImage =
            new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Pixel pixel = image.getPixel(x, y);
                Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB());
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }
        ImageIO.write(bufferedImage, format.name(), fileName.toFile());
    }
}
