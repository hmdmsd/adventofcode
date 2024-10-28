package com.example.demo.service;

import com.example.demo.util.FileReader;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalibrationService {
    private final PartOneCalculator partOneCalculator;
    private final PartTwoCalculator partTwoCalculator;
    private final FileReader fileReader;

    public CalibrationService(PartOneCalculator partOneCalculator,
                              PartTwoCalculator partTwoCalculator,
                              FileReader fileReader) {
        this.partOneCalculator = partOneCalculator;
        this.partTwoCalculator = partTwoCalculator;
        this.fileReader = fileReader;
    }

    public void processCalibrationDocument(String filename) {
        List<String> input = fileReader.readFile(filename);
        if (input.isEmpty()) {
            System.out.println("No input data found!");
            return;
        }

        // Process Part 1
        int part1Result = partOneCalculator.calculateSum(input);
        System.out.println("Part 1 Result: " + part1Result);

        // Process Part 2
        int part2Result = partTwoCalculator.calculateSum(input);
        System.out.println("Part 2 Result: " + part2Result);
    }

    public void runTests() {
        // Test Part 1
        List<String> testInput1 = List.of(
                "1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet"
        );
        System.out.println("Part 1 Test Result: " + partOneCalculator.calculateSum(testInput1));

        // Test Part 2
        List<String> testInput2 = List.of(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"
        );
        System.out.println("Part 2 Test Result: " + partTwoCalculator.calculateSum(testInput2));
    }
}