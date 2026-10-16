package com.esi.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback/patient")
    public ResponseEntity<Map<String,String>> patientFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                    "status", "ERROR",
                    "message", "MS-Patient est indisponible, veuillez réessayer plus tard"
                ));
    }

    @GetMapping("/fallback/ordonnance")
    public ResponseEntity<Map<String,String>> ordonnanceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                    "status", "ERROR",
                    "message", "MS-Ordonnance est indisponible, veuillez réessayer plus tard"
                ));
    }

    @GetMapping("/fallback/remboursement")
    public ResponseEntity<Map<String,String>> remboursementFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                    "status", "ERROR",
                    "message", "MS-Remboursement est indisponible, veuillez réessayer plus tard"
                ));
    }
}
