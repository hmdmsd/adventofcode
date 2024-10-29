package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Day3PartTwoCalculator {

    private static class Number {
        int value;
        int row;
        int startCol;
        int endCol;

        Number(int value, int row, int startCol, int endCol) {
            this.value = value;
            this.row = row;
            this.startCol = startCol;
            this.endCol = endCol;
        }
    }

    private static class Gear {
        int row;
        int col;

        Gear(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Gear gear = (Gear) o;
            return row == gear.row && col == gear.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public int calculateSum(List<String> input) {
        char[][] schematic = convertToGrid(input);
        List<Number> numbers = findAllNumbers(schematic);
        List<Gear> gears = findAllGears(schematic);
        return calculateGearRatiosSum(numbers, gears);
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

    private List<Number> findAllNumbers(char[][] grid) {
        List<Number> numbers = new ArrayList<>();
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
                    numbers.add(new Number(
                            Integer.parseInt(currentNum.toString()),
                            row,
                            numStart,
                            col - 1
                    ));
                    numStart = -1;
                    currentNum.setLength(0);
                }
            }
        }
        return numbers;
    }

    private List<Gear> findAllGears(char[][] grid) {
        List<Gear> gears = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == '*') {
                    gears.add(new Gear(row, col));
                }
            }
        }
        return gears;
    }

    private boolean isNumberAdjacentToGear(Number number, Gear gear) {
        // Check if the number is within one row of the gear
        if (Math.abs(number.row - gear.row) > 1) {
            return false;
        }

        // Check if the number is adjacent horizontally (including diagonally)
        return gear.col >= number.startCol - 1 && gear.col <= number.endCol + 1;
    }

    private int calculateGearRatiosSum(List<Number> numbers, List<Gear> gears) {
        int sum = 0;

        for (Gear gear : gears) {
            List<Number> adjacentNumbers = new ArrayList<>();

            // Find all numbers adjacent to this gear
            for (Number number : numbers) {
                if (isNumberAdjacentToGear(number, gear)) {
                    adjacentNumbers.add(number);
                }
            }

            // If exactly two numbers are adjacent, multiply them and add to sum
            if (adjacentNumbers.size() == 2) {
                sum += adjacentNumbers.get(0).value * adjacentNumbers.get(1).value;
            }
        }

        return sum;
    }
}