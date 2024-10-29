package com.example.demo.controller;

import com.example.demo.dto.AdventRequestDTO;
import com.example.demo.dto.AdventResultDTO;
import com.example.demo.dto.ErrorResponseDTO;
import com.example.demo.exception.AdventException;
import com.example.demo.service.CalibrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/advent")
public class AdventController {
    private final CalibrationService calibrationService;

    public AdventController(CalibrationService calibrationService) {
        this.calibrationService = calibrationService;
    }

    @GetMapping("/solve")
    public ResponseEntity<AdventResultDTO> solveDay(@Valid AdventRequestDTO request) {
        try {
            int result = calibrationService.calculateResult(request.getDay(), request.getPart());
            return ResponseEntity.ok(new AdventResultDTO(request.getDay(), request.getPart(), result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AdventResultDTO(request.getDay(), request.getPart(), e.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            @RequestAttribute(name = "path", required = false) String path) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Validation Failed",
                errors,
                path != null ? path : "/api/advent/solve"
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AdventException.class)
    public ResponseEntity<ErrorResponseDTO> handleAdventException(
            AdventException ex,
            @RequestAttribute(name = "path", required = false) String path) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Advent Calculation Error",
                List.of(ex.getMessage()),
                path != null ? path : "/api/advent/solve"
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex,
            @RequestAttribute(name = "path", required = false) String path) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Internal Server Error",
                List.of(ex.getMessage()),
                path != null ? path : "/api/advent/solve"
        );

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}