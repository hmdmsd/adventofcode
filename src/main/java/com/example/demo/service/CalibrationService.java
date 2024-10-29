package com.example.demo.service;

import com.example.demo.util.FileReader;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalibrationService {
    private final Day1PartOneCalculator day1Part1Calculator;
    private final Day1PartTwoCalculator day1Part2Calculator;
    private final Day3PartOneCalculator day3Part1Calculator;
    private final Day3PartTwoCalculator day3Part2Calculator;
    private final FileReader fileReader;

    public CalibrationService(
            Day1PartOneCalculator day1Part1Calculator,
            Day1PartTwoCalculator day1Part2Calculator,
            Day3PartOneCalculator day3Part1Calculator,
            Day3PartTwoCalculator day3Part2Calculator,
            FileReader fileReader) {
        this.day1Part1Calculator = day1Part1Calculator;
        this.day1Part2Calculator = day1Part2Calculator;
        this.day3Part1Calculator = day3Part1Calculator;
        this.day3Part2Calculator = day3Part2Calculator;
        this.fileReader = fileReader;
    }

    public int calculateResult(int day, int part) {
        String inputFileName = String.format("day%d.txt", day);
        List<String> input = fileReader.readFile(inputFileName);

        if (input.isEmpty()) {
            throw new RuntimeException("No input data found for day " + day);
        }

        return switch (day) {
            case 1 -> part == 1 ? day1Part1Calculator.calculateSum(input)
                    : day1Part2Calculator.calculateSum(input);
            case 3 -> part == 1 ? day3Part1Calculator.calculateSum(input)
                    : day3Part2Calculator.calculateSum(input);
            default -> throw new UnsupportedOperationException(
                    "Day " + day + " Part " + part + " not implemented yet");
        };
    }

    public void runTests() {
        // Day 1 tests
        List<String> day1TestInput1 = List.of(
                "1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet"
        );
        System.out.println("Day 1 Part 1 Test Result: " +
                day1Part1Calculator.calculateSum(day1TestInput1));

        List<String> day1TestInput2 = List.of(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"
        );
        System.out.println("Day 1 Part 2 Test Result: " +
                day1Part2Calculator.calculateSum(day1TestInput2));

        // Day 3 tests
        List<String> day3TestInput = List.of(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        );
        System.out.println("Day 3 Part 1 Test Result: " +
                day3Part1Calculator.calculateSum(day3TestInput));
        System.out.println("Day 3 Part 2 Test Result: " +
                day3Part2Calculator.calculateSum(day3TestInput));
    }
}