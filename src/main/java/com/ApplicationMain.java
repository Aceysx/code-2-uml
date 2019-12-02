package com;

import com.listener.JavaListener;
import com.listener.ParserListener;
import com.model.Violation;
import com.service.JavaService;
import com.util.parser.JavaLexer;
import com.util.parser.JavaParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class ApplicationMain {
    private static String projectDir = System.getProperty("user.dir") + "/src/resource/test.c";
    private static JavaService javaService;

    public static void main(String[] args) throws IOException {
        javaService.parse(projectDir);

    }
}
