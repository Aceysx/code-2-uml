package com;

import com.service.JavaService;

import java.io.IOException;
import java.util.regex.Pattern;


public class ApplicationMain {
    private static String projectDir = System.getProperty("user.dir") + "/src/resource/test.c";
    private static JavaService javaService = new JavaService();

    public static void main(String[] args) throws IOException {
        javaService.parse(projectDir);
    }
}
