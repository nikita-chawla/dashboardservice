package com.example.dashboard.service.impl;

import com.example.dashboard.service.CurlService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;

@Service
public class CurlServiceImpl implements CurlService {

//    @Override
//    public String executeCurl(String command) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
//
//        try {
//            String[] commandParts = CommandUtils.splitCommand(command);
//            CommandLine commandLine = new CommandLine(commandParts[0]);
//            for (int i = 1; i < commandParts.length; i++) {
//                commandLine.addArgument(commandParts[i], false);
//            }
//
//            DefaultExecutor executor = new DefaultExecutor();
//            executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
//            int exitValue = executor.execute(commandLine);
//
//            if (exitValue != 0) {
//                throw new ExecuteException("Process exited with an error: " + exitValue, exitValue);
//            }
//
//            return outputStream.toString();
//        } catch (ExecuteException e) {
//            e.printStackTrace();
//            return "ExecuteException: " + e.getMessage() + "\nError Output: " + errorStream.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "IOException: " + e.getMessage();
//        }
//    }


    @Override
    public String executeCurl(String command) throws Exception {
        CommandLine commandLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        executor.setStreamHandler(new PumpStreamHandler(outputStream,
                errorStream));

        // Log the command
        System.out.println("Executing command: " + command);

        try {
            String jsonBody = extractJsonBody(command);
            String originalCommand = buildCurlCommand(commandLine, jsonBody);
            String fullCurlCommand = "curl " + originalCommand;
            int exitValue = executor.execute(CommandLine.parse(fullCurlCommand.trim())); // Reuse CommandLine object
            String output = outputStream.toString();
            String error = errorStream.toString();
            System.out.println("Exit value: " + exitValue);
            System.out.println("Standard Output: " + output);
            System.out.println("Error Output: " + error);

            if (exitValue != 0) {
                throw new Exception("Command execution failed with exit code " + exitValue + ": " + error);
            }

            return output;
        } catch (ExecuteException e) {
            throw new Exception("ExecuteException: " + e.getMessage() + "\n" + errorStream.toString(), e);
        } catch (IOException e) {
            throw new Exception("IOException: " + e.getMessage() + "\n" + errorStream.toString(), e);
        } catch (Exception e) { // Consider adding a specific exception for missing JSON body
            throw new Exception("Error processing curl command: " + e.getMessage(), e);
        }
    }

    public static String extractJsonBody(String curlCommand) {
        String[] parts = curlCommand.split("-d ");
        if (parts.length > 1) {
            return parts[1].replaceFirst("^'(.*)'$", "$1"); // Remove leading and trailing quotes
        } else {
            return null;
        }
//        Pattern pattern = Pattern.compile("-d '(.*)'");
//        Matcher matcher = pattern.matcher(curlCommand);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            // Consider throwing an exception for missing JSON body
//            return null;
//        }
    }

    private String buildCurlCommand(CommandLine commandLine, String jsonBody) {
        StringBuilder curlCommand = new StringBuilder();
        curlCommand.append(String.join(" ", commandLine.getArguments())); // Rebuild command from arguments

        if (jsonBody != null && !jsonBody.isEmpty()) {
            curlCommand.append(" -d '")
                    .append(StringEscapeUtils.escapeJava(jsonBody))
                    .append("'");
        }
        return curlCommand.toString();
    }
}