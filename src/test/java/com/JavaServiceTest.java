package com;

import com.service.JavaService;
import org.junit.Test;

import java.io.IOException;

public class JavaServiceTest {
    private JavaService javaService = new JavaService();
    private static String projectDir = System.getProperty("user.dir") + "/src/resource/test/src/main/java/tw/commands";
    @Test
    public void should_success() throws IOException {
        javaService.parse(projectDir);
    }

}
