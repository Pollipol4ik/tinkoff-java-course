package edu.hw9.task3;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import lombok.SneakyThrows;

public class DepthFirstSolver extends SolverAbstract {

    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public DepthFirstSolver(Maze maze) {
        super(maze);
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateCoordinates(start, end);
        boolean result = forkJoinPool.invoke(new ParallelDFSTask(start, end));
        if (!result) {
            return Collections.emptyList();
        }
        return path;
    }

    private void validateCoordinates(Coordinate start, Coordinate end) {
        if (!isValidCoordinate(start) || !isValidCoordinate(end)) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }

    private class ParallelDFSTask extends RecursiveTask<Boolean> {

        private final Coordinate current;
        private final Coordinate end;

        ParallelDFSTask(Coordinate current, Coordinate end) {
            this.current = current;
            this.end = end;
        }

        @Override
        @SneakyThrows
        protected Boolean compute() {
            visited[current.column()][current.row()] = true;
            path.add(current);
            if (current.equals(end)) {
                return true;
            }

            List<ParallelDFSTask> subTasks = new java.util.ArrayList<>();

            for (Coordinate neighbor : getNeighbours(current)) {
                if (!visited[neighbor.column()][neighbor.row()]) {
                    ParallelDFSTask task = new ParallelDFSTask(neighbor, end);
                    task.fork();
                    subTasks.add(task);
                }
            }

            for (ParallelDFSTask task : subTasks) {
                if (task.join()) {
                    return true;
                }
            }

            path.remove(path.size() - 1);
            return false;
        }
    }
}
