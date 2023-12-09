package edu.hw9.task2;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchTreeTest {

    @SneakyThrows
    @Test
    @DisplayName("Тест findLargeDirectories")
    public void findLargeDirectories_shouldReturnDirectories_withFilesMoreThan2(@TempDir Path tempDir) {
        initializeDirectory(tempDir);
        assertThat(ParallelSearchTree.findLargeDirectories(tempDir, 2))
            .containsExactlyInAnyOrder(Path.of(tempDir.toString(), "dir2"),
                Path.of(tempDir.toString(), "dir2/dir4"));
    }

    @SneakyThrows
    @Test
    @DisplayName("Тест findFilesWithPredicate")
    public void findFilesWithPredicate_shouldReturnFiles_with_txt_type(@TempDir Path tempDir) {
        initializeDirectory(tempDir);
        Predicate<Path> txtFilePredicate = file -> file.toString().endsWith(".txt");
        assertThat(ParallelSearchTree.findFilesWithPredicate(tempDir, txtFilePredicate))
            .containsExactlyInAnyOrder(
                Path.of(tempDir.toString(), "dir2", "file1.txt"),
                Path.of(tempDir.toString(), "dir3", "file3.txt"),
                Path.of(tempDir.toString(), "dir2", "dir4", "file4.txt"),
                Path.of(tempDir.toString(), "dir2", "dir4", "file5.txt")
            );
    }

    @SneakyThrows
    private static void initializeDirectory(Path directory) {
        Path dir2 = createDirectory(directory, "dir2");
        Path dir3 = createDirectory(directory, "dir3");
        createFile(dir2, "file1.txt");
        createFile(dir2, "file2.ml");
        createFile(dir3, "file3.txt");

        Path dir4 = createDirectory(dir2, "dir4");
        createFile(dir4, "file4.txt");
        createFile(dir4, "file5.txt");
    }

    private static Path createDirectory(Path parent, String name) throws IOException {
        Path dirPath = Path.of(parent.toString(), name);
        Files.createDirectory(dirPath);
        return dirPath;
    }

    @SneakyThrows
    private static Path createFile(Path parent, String name) {
        Path filePath = Path.of(parent.toString(), name);
        Files.createFile(filePath);
        return filePath;
    }
}
