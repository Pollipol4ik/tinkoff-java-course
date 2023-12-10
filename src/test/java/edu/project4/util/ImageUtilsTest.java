package edu.project4.util;

import edu.project4.model.FractalImage;
import edu.project4.model.Format;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

class ImageUtilsTest {
    @Test
    @DisplayName("FormatImageSaver #save test")
    public void save_saveImage(@TempDir Path tempDir) {
        FractalImage image = FractalImage.create(10, 10);
        ImageUtils.save(image, tempDir, "test", Format.PNG);
        assertThat(Path.of(tempDir.toString(), "test.png")).exists();
    }

}
