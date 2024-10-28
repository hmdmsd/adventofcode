package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class PartTwoCalculator {
    private static final Map<String, String> NUMBER_WORDS = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public int calculateSum(List<String> input) {
        return input.stream()
                .mapToInt(this::getCalibrationValue)
                .sum();
    }

    private int getCalibrationValue(String line) {
        String firstDigit = findFirstDigit(line);
        String lastDigit = findLastDigit(line);
        return Integer.parseInt(firstDigit + lastDigit);
    }

    private String findFirstDigit(String line) {
        int firstIndex = line.length();
        String firstDigit = "0";

        // Check for numeric digits
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                firstIndex = i;
                firstDigit = String.valueOf(line.charAt(i));
                break;
            }
        }

        // Check for word digits
        for (Map.Entry<String, String> entry : NUMBER_WORDS.entrySet()) {
            int index = line.indexOf(entry.getKey());
            if (index != -1 && index < firstIndex) {
                firstIndex = index;
                firstDigit = entry.getValue();
            }
        }

        return firstDigit;
    }

    private String findLastDigit(String line) {
        int lastIndex = -1;
        String lastDigit = "0";

        // Check for numeric digits
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                lastIndex = i;
                lastDigit = String.valueOf(line.charAt(i));
                break;
            }
        }

        // Check for word digits
        for (Map.Entry<String, String> entry : NUMBER_WORDS.entrySet()) {
            int index = line.lastIndexOf(entry.getKey());
            if (index != -1 && index > lastIndex) {
                lastIndex = index;
                lastDigit = entry.getValue();
            }
        }

        return lastDigit;
    }
}