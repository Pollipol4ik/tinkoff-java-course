package edu.hw9.task3;

public final class Utils {

    private Utils() {
    }

    public static Cell[][] createGrid(int height, int width, Cell.Type cellType) {
        Cell[][] cells = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cells[row][column] = new Cell(row, column, cellType);
            }
        }
        return cells;
    }

    public static void addBorderWalls(Cell[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (row == 0 || row == height - 1 || column == 0 || column == width - 1) {
                    grid[row][column].setType(Cell.Type.WALL);
                }
            }
        }
    }

}

