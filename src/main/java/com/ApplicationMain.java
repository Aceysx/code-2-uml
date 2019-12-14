package com;

import com.service.JavaService;

import java.io.IOException;


public class ApplicationMain {
    private static String projectDir = "";
    private static JavaService javaService = new JavaService();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("please enter code path to parse");
        }
        projectDir = args[0];

        javaService.parse(projectDir);
    }
}
