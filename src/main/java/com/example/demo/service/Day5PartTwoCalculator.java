package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Day5PartTwoCalculator {
    private static class Range {
        long destStart;
        long sourceStart;
        long length;

        Range(long destStart, long sourceStart, long length) {
            this.destStart = destStart;
            this.sourceStart = sourceStart;
            this.length = length;
        }

        boolean containsSource(long number) {
            return number >= sourceStart && number < sourceStart + length;
        }

        long convert(long number) {
            return destStart + (number - sourceStart);
        }
    }

    private static class SeedRange {
        long start;
        long length;

        SeedRange(long start, long length) {
            this.start = start;
            this.length = length;
        }
    }

    public long calculateLowestLocation(List<String> input) {
        // Parse seed ranges
        List<SeedRange> seedRanges = parseSeedRanges(input.get(0));

        // Parse all maps
        List<List<Range>> allMaps = parseMaps(input);

        // Find lowest location by processing each seed range
        long lowestLocation = Long.MAX_VALUE;

        // Process each seed in each range
        for (SeedRange seedRange : seedRanges) {
            for (long seed = seedRange.start; seed < seedRange.start + seedRange.length; seed++) {
                long location = processSeed(seed, allMaps);
                lowestLocation = Math.min(lowestLocation, location);
            }
        }

        return lowestLocation;
    }

    private List<SeedRange> parseSeedRanges(String seedLine) {
        String[] parts = seedLine.split(": ")[1].trim().split(" ");
        List<SeedRange> ranges = new ArrayList<>();

        for (int i = 0; i < parts.length; i += 2) {
            long start = Long.parseLong(parts[i]);
            long length = Long.parseLong(parts[i + 1]);
            ranges.add(new SeedRange(start, length));
        }

        return ranges;
    }

    private List<List<Range>> parseMaps(List<String> input) {
        List<List<Range>> allMaps = new ArrayList<>();
        List<Range> currentMap = null;

        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentMap != null) {
                    allMaps.add(currentMap);
                }
                currentMap = new ArrayList<>();
                continue;
            }

            if (currentMap != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    currentMap.add(new Range(
                            Long.parseLong(parts[0]),  // destination start
                            Long.parseLong(parts[1]),  // source start
                            Long.parseLong(parts[2])   // length
                    ));
                }
            }
        }

        if (currentMap != null) {
            allMaps.add(currentMap);
        }

        return allMaps;
    }

    private long processSeed(long number, List<List<Range>> allMaps) {
        // Process the number through each map in sequence
        for (List<Range> map : allMaps) {
            number = processMap(number, map);
        }
        return number;
    }

    private long processMap(long number, List<Range> ranges) {
        // Find the range that contains our number
        for (Range range : ranges) {
            if (range.containsSource(number)) {
                return range.convert(number);
            }
        }
        // If no range contains the number, it maps to itself
        return number;
    }

    // This method is used by CalibrationService
    public int calculateSum(List<String> input) {
        return (int) calculateLowestLocation(input);
    }
}