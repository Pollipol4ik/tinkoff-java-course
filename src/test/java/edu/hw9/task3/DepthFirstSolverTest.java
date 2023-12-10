package edu.hw9.task3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DepthFirstSolverTest {

    private final Solver solver = new DepthFirstSolver(maze);
    private static Maze maze;

    @BeforeAll
    static void initMaze() {
        Cell[][] grid = Utils.createGrid(5, 5, Cell.Type.PASSAGE);
        Utils.addBorderWalls(grid);
        grid[1][2].setType(Cell.Type.WALL);
        grid[2][2].setType(Cell.Type.WALL);
        maze = new Maze(5, 5, grid);
        maze = new Maze(7, 7, grid);
    }

    @Test
    @DisplayName("DFS solver test")
    public void solve_shouldFindCorrectPath() {
        List<Coordinate> actual = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 2));
        assertThat(actual).containsExactlyInAnyOrder(
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2)
        );

    }

    @Test
    @DisplayName("DFS solver test with wrong input")
    public void solve_shouldThrowExceptionWhenInputIsIncorrect() {
        assertThatThrownBy(() -> solver.solve(maze, new Coordinate(-1, 0), new Coordinate(9, 9)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
