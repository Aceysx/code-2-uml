package com;

import com.model.Violation;
import com.service.ExcessiveCommentLengthRule;
import com.service.ViolationListener;
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
    public static void main(String[] args) throws IOException {
        CharStream charStream = CharStreams.fromPath(Paths.get(projectDir));
        JavaLexer lexer = new JavaLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser javaParser = new JavaParser(tokens);
        //方法长度规则
//        ViolationListener rule = new ExcessiveMethodLengthRule();

        // 注释规则：找到所有的注释
        ViolationListener rule = new ExcessiveCommentLengthRule();
        rule.updateParser(javaParser);

        JavaParser.CompilationUnitContext compilationUnitContext = javaParser.compilationUnit();
        ParseTreeWalker.DEFAULT.walk(rule, compilationUnitContext);

        List<Violation> filterViolations = rule.getViolations().stream().distinct().collect(Collectors.toList());
        System.out.println(filterViolations);
        System.out.println(filterViolations.size());
    }
}
