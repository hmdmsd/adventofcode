package com.example.demo.service;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Day5PartTwoCalculator {
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
    }

    private static class RangeWindow {
        long start;
        long end;

        RangeWindow(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    public long calculateLowestLocation(List<String> input) {
        // Parse seed ranges
        List<RangeWindow> seedRanges = parseSeedRanges(input.get(0));

        // Parse all conversion maps
        List<List<Range>> allMaps = parseMaps(input);

        // Process ranges through each map
        long lowestLocation = Long.MAX_VALUE;
        for (RangeWindow seedRange : seedRanges) {
            List<RangeWindow> currentRanges = new ArrayList<>();
            currentRanges.add(seedRange);

            // Process through each map
            for (List<Range> map : allMaps) {
                currentRanges = processRanges(currentRanges, map);
            }

            // Find minimum from processed ranges
            for (RangeWindow range : currentRanges) {
                lowestLocation = Math.min(lowestLocation, range.start);
            }
        }

        return lowestLocation;
    }

    private List<RangeWindow> parseSeedRanges(String seedLine) {
        String[] parts = seedLine.split(": ")[1].trim().split(" ");
        List<RangeWindow> ranges = new ArrayList<>();

        for (int i = 0; i < parts.length; i += 2) {
            long start = Long.parseLong(parts[i]);
            long length = Long.parseLong(parts[i + 1]);
            ranges.add(new RangeWindow(start, start + length));
        }

        return ranges;
    }

    private List<List<Range>> parseMaps(List<String> input) {
        List<List<Range>> allMaps = new ArrayList<>();
        List<Range> currentMap = null;

        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i).trim();

            if (line.isEmpty()) continue;

            if (line.endsWith("map:")) {
                if (currentMap != null) {
                    sortRanges(currentMap);
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
            sortRanges(currentMap);
            allMaps.add(currentMap);
        }

        return allMaps;
    }

    private void sortRanges(List<Range> ranges) {
        ranges.sort((a, b) -> Long.compare(a.start, b.start));
    }

    private List<RangeWindow> processRanges(List<RangeWindow> inputRanges, List<Range> mappingRanges) {
        List<RangeWindow> resultRanges = new ArrayList<>();

        for (RangeWindow inputRange : inputRanges) {
            long current = inputRange.start;

            while (current < inputRange.end) {
                Range mappingRange = null;

                // Find the applicable mapping range
                for (Range range : mappingRanges) {
                    if (range.contains(current)) {
                        mappingRange = range;
                        break;
                    }
                    if (range.start > current) {
                        break;
                    }
                }

                if (mappingRange != null) {
                    // Apply the mapping
                    long rangeEnd = Math.min(inputRange.end, mappingRange.end);
                    resultRanges.add(new RangeWindow(
                            mappingRange.convert(current),
                            mappingRange.convert(rangeEnd - 1) + 1
                    ));
                    current = rangeEnd;
                } else {
                    // Find next mapping range or end of input range
                    long nextStart = inputRange.end;
                    for (Range range : mappingRanges) {
                        if (range.start > current) {
                            nextStart = Math.min(nextStart, range.start);
                            break;
                        }
                    }
                    resultRanges.add(new RangeWindow(current, Math.min(nextStart, inputRange.end)));
                    current = nextStart;
                }
            }
        }

        return resultRanges;
    }

}