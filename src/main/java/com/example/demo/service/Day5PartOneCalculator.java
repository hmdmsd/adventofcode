package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Day5PartOneCalculator {

    private static final Logger log = LoggerFactory.getLogger(Day5PartOneCalculator.class);

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

    public long calculateLowestLocation(List<String> input) {
        // Parse seeds
        List<Long> seeds = parseSeeds(input.get(0));

        // Parse all maps
        List<List<Range>> allMaps = parseMaps(input);

        // Find lowest location
        long lowestLocation = Long.MAX_VALUE;
        for (long seed : seeds) {
            long location = processSeed(seed, allMaps);
            lowestLocation = Math.min(lowestLocation, location);
        }

        return lowestLocation;
    }

    private List<Long> parseSeeds(String seedLine) {
        String[] parts = seedLine.split(": ")[1].trim().split(" ");
        List<Long> seeds = new ArrayList<>();
        for (String part : parts) {
            seeds.add(Long.parseLong(part));
        }
        log.info("My Parsed Seeds Are Here : {}",seeds);
        return seeds;
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

        log.info("My All Parsed Maps Are Here : {}",allMaps);

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

}