package com;

import com.service.JavaService;
import org.junit.Test;

import java.io.IOException;

public class JavaServiceTest {
    private JavaService javaService = new JavaService();
    private static String projectDir = "/Users/xinsi/demo/code-2-uml/src/main";
    @Test
    public void should_success() throws IOException {
        javaService.parse(projectDir);
    }

}
