package com.example.dashboard_service.controller;

import com.example.dashboard_service.model.CurlRequest;
import com.example.dashboard_service.service.CurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CurlController {

    @Autowired
    private CurlService curlService;

    @PostMapping("/execute-curl")
    public ResponseEntity<String> executeCurl(@RequestBody CurlRequest curlRequest) {
        String curlCommand = curlRequest.getCommand();

        if (!curlCommand.startsWith("curl ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid cURL command");
        }

        try {
            String result = curlService.executeCurl(curlCommand);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
