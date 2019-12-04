package com;

import com.service.JavaService;

import java.io.IOException;


public class ApplicationMain {
    private static String projectDir = System.getProperty("user.dir") + "/src/resource/test.c";
    private static JavaService javaService;

    public static void main(String[] args) throws IOException {
        javaService.parse(projectDir);

    }
}
