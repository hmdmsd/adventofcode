package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AdventRequestDTO {
    @NotNull(message = "Day is required")
    @Min(value = 1, message = "Day must be at least 1")
    @Max(value = 25, message = "Day must not exceed 25")
    private Integer day;

    @NotNull(message = "Part is required")
    @Min(value = 1, message = "Part must be either 1 or 2")
    @Max(value = 2, message = "Part must be either 1 or 2")
    private Integer part;

    // Getters and Setters
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }
}
