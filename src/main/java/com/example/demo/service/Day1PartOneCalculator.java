package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Day1PartOneCalculator {
    public int calculateSum(List<String> input) {
        return input.stream()
                .mapToInt(this::getCalibrationValue)
                .sum();
    }

    private int getCalibrationValue(String line) {
        char firstDigit = '0';
        char lastDigit = '0';

        // Find first digit
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                firstDigit = c;
                break;
            }
        }

        // Find last digit
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                lastDigit = line.charAt(i);
                break;
            }
        }

        return Integer.parseInt(String.valueOf(firstDigit) + String.valueOf(lastDigit));
    }
}