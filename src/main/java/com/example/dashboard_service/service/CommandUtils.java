package com.example.dashboard_service.service;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {

    public static String[] splitCommand(String command) {
        List<String> parts = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder part = new StringBuilder();

        for (char c : command.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (part.length() > 0) {
                    parts.add(part.toString());
                    part = new StringBuilder();
                }
            } else {
                part.append(c);
            }
        }

        if (part.length() > 0) {
            parts.add(part.toString());
        }

        return parts.toArray(new String[0]);
    }
}
