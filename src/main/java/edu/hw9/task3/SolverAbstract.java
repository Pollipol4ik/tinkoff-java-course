package edu.hw9.task3;

import java.util.ArrayList;
import java.util.List;

public abstract class SolverAbstract implements Solver {
    private static final int[][] NEIGHBOURS_COORDINATES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    protected final List<Coordinate> path = new ArrayList<>();
    protected boolean[][] visited;
    protected Maze maze;

    protected SolverAbstract(Maze maze) {
        this.maze = maze;
        this.visited = new boolean[maze.getHeight()][maze.getWidth()];
    }

    protected List<Coordinate> getNeighbours(Coordinate coordinate) {
        List<Coordinate> neighbours = new ArrayList<>();
        for (int[] neighbour : NEIGHBOURS_COORDINATES) {
            int newRow = coordinate.row() + neighbour[0];
            int newColumn = coordinate.column() + neighbour[1];
            if (isPassable(newRow, newColumn)) {
                neighbours.add(new Coordinate(newRow, newColumn));
            }
        }
        return neighbours;
    }

    protected boolean isValidCoordinate(Coordinate coordinate) {
        return inBounds(coordinate.row(), coordinate.column()) && isPassable(coordinate.row(), coordinate.column());
    }

    private boolean isPassable(int row, int col) {
        return inBounds(row, col) && maze.getGrid()[row][col].getType() == Cell.Type.PASSAGE;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < maze.getWidth() && y >= 0 && y < maze.getHeight();
    }

}
