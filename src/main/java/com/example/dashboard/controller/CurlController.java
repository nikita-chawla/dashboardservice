package com.example.dashboard.controller;

import com.example.dashboard.model.CurlRequest;
import com.example.dashboard.service.CurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping
public class CurlController {

    @Autowired
    private CurlService curlService;

    @CrossOrigin(origins = "*")
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

    @PostMapping("/execute-curl1")
    public ResponseEntity<String> executeCurl(@RequestBody List<String> curlCommand) {
        try {
            // Prepare ProcessBuilder with the curl command and its arguments
            ProcessBuilder processBuilder = new ProcessBuilder(curlCommand);

            // Start the process
            Process process = processBuilder.start();

            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return ResponseEntity.status(500).body("Error occurred while executing curl command.");
            }

            return ResponseEntity.ok(output.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Exception: " + e.getMessage());
        }
    }
}
