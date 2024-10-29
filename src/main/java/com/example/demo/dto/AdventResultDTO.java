package com.example.demo.dto;

public class AdventResultDTO {
    private int day;
    private int part;
    private int result;
    private String message;
    private boolean success;

    // Constructor for successful response
    public AdventResultDTO(int day, int part, int result) {
        this.day = day;
        this.part = part;
        this.result = result;
        this.success = true;
        this.message = "Calculation completed successfully";
    }

    // Constructor for error response
    public AdventResultDTO(int day, int part, String errorMessage) {
        this.day = day;
        this.part = part;
        this.success = false;
        this.message = errorMessage;
    }

    // Getters and Setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}