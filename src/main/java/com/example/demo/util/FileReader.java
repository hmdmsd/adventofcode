package com.example.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileReader {
    public List<String> readFile(String fileName) {
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            return Files.readAllLines(Paths.get(file.getPath()));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return List.of();
        }
    }
}