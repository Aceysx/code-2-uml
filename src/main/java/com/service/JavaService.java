package com.service;

import com.listener.JavaListener;
import com.listener.ParserListener;
import com.util.FileUtil;
import com.util.parser.JavaLexer;
import com.util.parser.JavaParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaService {

    public void parse(String projectDir) throws IOException {
        List<String> filePaths = FileUtil.findFilesPathBy(new File(projectDir));
        filePaths = filePaths.stream().filter(item -> item.endsWith(".java")).collect(Collectors.toList());
        List<JavaParser> parsers = toParses(filePaths);
        ParserListener rule = new JavaListener();

        JavaParser.CompilationUnitContext compilationUnitContext = javaParser.compilationUnit();
        ParseTreeWalker.DEFAULT.walk(rule, compilationUnitContext);
    }


    private List<JavaParser> toParses(List<String> filePaths) throws IOException {
        List<JavaParser> results = new ArrayList<>();
        for (String file : filePaths) {
            CharStream charStream = CharStreams.fromPath(Paths.get(file));
            JavaLexer javaLexer = new JavaLexer(charStream);
            results.add(new JavaParser(new CommonTokenStream(javaLexer)));
        }
        return results;
    }
}
