package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Day5PartOneCalculator {
    private static final Logger log = LoggerFactory.getLogger(Day5PartOneCalculator.class);

    private static class Range {
        long start;
        long end;
        long offset;

        Range(long destStart, long sourceStart, long length) {
            this.start = sourceStart;
            this.end = sourceStart + length;
            this.offset = destStart - sourceStart;
        }

        boolean contains(long number) {
            return number >= start && number < end;
        }

        long convert(long number) {
            return number + offset;
        }

        @Override
        public String toString() {
            return String.format("Range[start=%d, end=%d, offset=%d]", start, end, offset);
        }
    }

    public long calculateLowestLocation(List<String> input) {
        // Parse seeds
        List<Long> seeds = parseSeeds(input.get(0));
        log.info("Processing {} seeds", seeds.size());

        // Parse and sort all maps
        List<List<Range>> allMaps = parseMaps(input);

        // Process all seeds in parallel for better performance
        return seeds.parallelStream()
                .mapToLong(seed -> processSeed(seed, allMaps))
                .min()
                .orElse(Long.MAX_VALUE);
    }

    private List<Long> parseSeeds(String seedLine) {
        String[] parts = seedLine.split(": ")[1].trim().split(" ");
        List<Long> seeds = new ArrayList<>(parts.length);

        for (String part : parts) {
            seeds.add(Long.parseLong(part));
        }

        log.info("Parsed Seeds: {}", seeds);
        return seeds;
    }

    private List<List<Range>> parseMaps(List<String> input) {
        List<List<Range>> allMaps = new ArrayList<>();
        List<Range> currentMap = null;
        int mapIndex = 0;

        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentMap != null) {
                    sortRanges(currentMap);
                    allMaps.add(currentMap);
                    log.debug("Processed map {}: {} ranges", mapIndex++, currentMap.size());
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
            sortRanges(currentMap);
            allMaps.add(currentMap);
            log.debug("Processed final map {}: {} ranges", mapIndex, currentMap.size());
        }

        log.info("Total maps parsed: {}", allMaps.size());
        if (log.isDebugEnabled()) {
            for (int i = 0; i < allMaps.size(); i++) {
                log.debug("Map {} size: {}", i, allMaps.get(i).size());
            }
        }

        return allMaps;
    }

    private void sortRanges(List<Range> ranges) {
        ranges.sort((a, b) -> Long.compare(a.start, b.start));
    }

    private long processSeed(long number, List<List<Range>> allMaps) {
        long currentValue = number;

        for (List<Range> map : allMaps) {
            currentValue = processMap(currentValue, map);
        }

        return currentValue;
    }

    private long processMap(long number, List<Range> ranges) {
        // Binary search for the appropriate range
        int left = 0;
        int right = ranges.size() - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            Range range = ranges.get(mid);

            if (range.contains(number)) {
                return range.convert(number);
            }

            if (number < range.start) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return number;
    }
}