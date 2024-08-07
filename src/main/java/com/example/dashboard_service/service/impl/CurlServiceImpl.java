package com.example.dashboard_service.service.impl;

import com.example.dashboard_service.service.CommandUtils;
import com.example.dashboard_service.service.CurlService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CurlServiceImpl implements CurlService {

    @Override
    public String executeCurl(String command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

        try {
            String[] commandParts = CommandUtils.splitCommand(command);
            CommandLine commandLine = new CommandLine(commandParts[0]);
            for (int i = 1; i < commandParts.length; i++) {
                commandLine.addArgument(commandParts[i], false);
            }

            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
            int exitValue = executor.execute(commandLine);

            if (exitValue != 0) {
                throw new ExecuteException("Process exited with an error: " + exitValue, exitValue);
            }

            return outputStream.toString();
        } catch (ExecuteException e) {
            e.printStackTrace();
            return "ExecuteException: " + e.getMessage() + "\nError Output: " + errorStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException: " + e.getMessage();
        }
    }

//    @Override
//    public String executeCurl(String command) throws Exception {
//        CommandLine commandLine = CommandLine.parse(command);
//        DefaultExecutor executor = new DefaultExecutor();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
//        executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
//
//        // Log the command
//        System.out.println("Executing command: " + command);
//
//        try {
//            int exitValue = executor.execute(commandLine);
//            String output = outputStream.toString();
//            String error = errorStream.toString();
//            System.out.println("Exit value: " + exitValue);
//            System.out.println("Standard Output: " + output);
//            System.out.println("Error Output: " + error);
//
//            if (exitValue != 0) {
//                throw new Exception("Command execution failed with exit code " + exitValue + ": " + error);
//            }
//
//            return output;
//        } catch (ExecuteException e) {
//            throw new Exception("ExecuteException: " + e.getMessage() + "\n" + errorStream.toString(), e);
//        } catch (IOException e) {
//            throw new Exception("IOException: " + e.getMessage() + "\n" + errorStream.toString(), e);
//        }
//    }

}
