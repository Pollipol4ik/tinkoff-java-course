package edu.project4.util;


import edu.project4.model.FractalImage;
import edu.project4.model.Format;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ImageUtilsTest {

    @Test
    @DisplayName("#save test")
    public void save_shouldSaveImageToDisc(@TempDir Path path) {
        Path savedImg = path.resolve("img.png");
        ImageUtils.save(FractalImage.create(400, 400), path.resolve("img.png"), Format.PNG);
        Assertions.assertThat(path.resolve("img.png")).exists();
    }
}
