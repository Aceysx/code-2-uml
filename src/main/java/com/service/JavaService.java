package com.service;

import com.listener.JavaListener;
import com.listener.ParserListener;
import com.model.Field;
import com.model.JavaStructure;
import com.model.Method;
import com.model.Structure;
import com.util.FileUtil;
import com.util.parser.JavaLexer;
import com.util.parser.JavaParser;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.util.Constant.UML_TEXT_PATH;

public class JavaService {

    public void parse(String projectDir) throws IOException {
        List<String> filePaths = FileUtil.findFilesPathBy(new File(projectDir));
        filePaths = filePaths.stream().filter(item -> item.endsWith(".java")).collect(Collectors.toList());
        List<JavaParser> parsers = toParses(filePaths);

        List<Structure> structures = new ArrayList<>();
        parsers.forEach(parser -> {
            ParserListener listener = new JavaListener(new JavaStructure(), parser);
            JavaParser.CompilationUnitContext compilationUnitContext = parser.compilationUnit();
            ParseTreeWalker.DEFAULT.walk(listener, compilationUnitContext);
            structures.add(listener.getStructure());
        });
        String umlText = JavaUml.parse(structures);
        FileUtil.write(umlText);
        System.out.println(213);
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
