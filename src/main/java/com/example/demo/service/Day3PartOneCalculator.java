package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Day3PartOneCalculator {
    public int calculateSum(List<String> input) {
        char[][] schematic = convertToGrid(input);
        return findPartNumbersSum(schematic);
    }

    private char[][] convertToGrid(List<String> input) {
        int rows = input.size();
        int cols = input.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private boolean hasAdjacentSymbol(char[][] grid, int row, int startCol, int endCol) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Check all adjacent cells including diagonals
        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, startCol - 1); c <= Math.min(cols - 1, endCol + 1); c++) {
                if (isSymbol(grid[r][c])) {
                    return true;
                }
            }
        }
        return false;
    }

    private int findPartNumbersSum(char[][] grid) {
        int sum = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            int numStart = -1;
            StringBuilder currentNum = new StringBuilder();

            for (int col = 0; col <= cols; col++) {
                if (col < cols && Character.isDigit(grid[row][col])) {
                    if (numStart == -1) {
                        numStart = col;
                    }
                    currentNum.append(grid[row][col]);
                } else if (numStart != -1) {
                    // Check if this number is adjacent to a symbol
                    if (hasAdjacentSymbol(grid, row, numStart, col - 1)) {
                        sum += Integer.parseInt(currentNum.toString());
                    }
                    // Reset for next number
                    numStart = -1;
                    currentNum.setLength(0);
                }
            }
        }
        return sum;
    }
}