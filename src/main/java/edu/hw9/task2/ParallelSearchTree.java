package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

public class ParallelSearchTree {

    private static final ForkJoinPool POOL = ForkJoinPool.commonPool();
    private static final String ERROR_MESSAGE = "Error while processing the file tree";

    private ParallelSearchTree() {

    }

    public static List<Path> findLargeDirectories(Path rootDir, Integer minimumFiles) {
        return POOL.invoke(new DirectoriesRecursiveTask(rootDir, minimumFiles));
    }

    public static List<Path> findFilesWithPredicate(Path rootDir, Predicate<Path> predicate) {
        return POOL.invoke(new FilesRecursiveTask(rootDir, predicate));
    }

    @RequiredArgsConstructor
    private static class DirectoriesRecursiveTask extends RecursiveTask<List<Path>> {
        private final Path rootDir;
        private final Integer minimumFiles;

        @Override
        protected List<Path> compute() {
            List<Path> result = new ArrayList<>();
            List<DirectoriesRecursiveTask> subTasks = new ArrayList<>();
            int counterOfFiles = 0;

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootDir)) {
                for (var path : paths) {
                    if (!Files.isDirectory(path)) {
                        counterOfFiles++;
                        continue;
                    }
                    DirectoriesRecursiveTask newTask = new DirectoriesRecursiveTask(path, minimumFiles);
                    subTasks.add(newTask);
                    newTask.fork();
                }
            } catch (IOException e) {
                throw new RuntimeException(ERROR_MESSAGE, e);
            }

            if (counterOfFiles >= minimumFiles) {
                result.add(rootDir);
            }

            subTasks.forEach(task -> result.addAll(task.join()));
            return result;
        }
    }

    @RequiredArgsConstructor
    private static class FilesRecursiveTask extends RecursiveTask<List<Path>> {
        private final Path rootDir;
        private final Predicate<Path> predicate;

        @Override
        protected List<Path> compute() {
            List<Path> result = new ArrayList<>();
            List<FilesRecursiveTask> subTasks = new ArrayList<>();

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootDir)) {
                for (var path : paths) {
                    if (!Files.isDirectory(path)) {
                        if (predicate.test(path)) {
                            result.add(path);
                        }
                        continue;
                    }
                    FilesRecursiveTask newTask = new FilesRecursiveTask(path, predicate);
                    subTasks.add(newTask);
                    newTask.fork();
                }
            } catch (IOException e) {
                throw new RuntimeException(ERROR_MESSAGE, e);
            }

            subTasks.forEach(task -> result.addAll(task.join()));
            return result;
        }
    }
}
