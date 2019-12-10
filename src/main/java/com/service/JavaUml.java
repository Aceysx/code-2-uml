package com.service;

import com.model.*;
import com.util.FileUtil;
import net.sourceforge.plantuml.SourceFileReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.util.Constant.*;
import static java.util.stream.Collectors.joining;

public class JavaUml {
    private final List<JavaStructure> structures;
    private StringBuffer umlText;

    private JavaUml(List<Structure> structures) {
        this.structures = structures.stream().map(item ->
            (JavaStructure) item)
            .filter(item -> !Objects.equals(item.getKlass().getType(), ANNOTATION_TOKEN))
            .collect(Collectors.toList());
        umlText = new StringBuffer(START_TOKEN)
            .append(NEW_LINE);
    }

    static String parse(List<Structure> structures) throws IOException {
        JavaUml javaUml = new JavaUml(structures);
        javaUml.parseClasses();
        javaUml.parseClassRelation();
        String format = javaUml.format();
        FileUtil.write(format);
        javaUml.png();
        return format;
    }

    private void parseClassRelation() {
        structures
            .forEach(structure -> structure.getKlasses()
                .forEach(klass -> {
                    List<Type> types = klass.getTypes();
                    String klassName = klass.getName();
                    for (int i = 0; i < types.size(); i += 2) {
                        String parent = types.get(i + 1).getText();
                        String parentModifier = types.get(i).getText();
                        Arrays.asList(parent.split(","))
                            .forEach(item -> umlText.append(item)
                                .append(" -[hidden]--> ")
                                .append(klassName)
                                .append(" : ")
                                .append(parentModifier)
                                .append("↑")
                                .append(NEW_LINE));


                    }
                }));
    }

    private void png() throws IOException {
        SourceFileReader reader = new SourceFileReader(new File(UML_TEXT_PATH));
        reader.getGeneratedImages();
    }

    private String format() {
        return umlText
            .append(NEW_LINE)
            .append(END_TOKEN)
            .toString();
    }

    private void parseClasses() {
        structures.forEach(structure -> {
            boolean hasPackage = Objects.nonNull(structure.getPackagee());
            if (hasPackage) {
                umlText.append("package").append(SPACE_TOKEN)
                    .append(structure.getPackagee().getText())
                    .append("{")
                    .append(NEW_LINE)
                ;

            }
            Klass klass = structure.getKlass();
            umlText
                .append(klass.getType())
                .append(SPACE_TOKEN)
                .append(klass.getName())
                .append("{")
                .append(NEW_LINE);

            parseFields(klass.getFields());

            parseMethods(klass.getMethods());
            umlText
                .append(NEW_LINE)
                .append("}")
                .append(NEW_LINE);

            if (hasPackage) {
                umlText.append("}")
                    .append(NEW_LINE);
            }
        });
    }

    private void parseMethods(List<Method> methods) {
        if (Objects.nonNull(methods)) {
            methods.forEach(method -> {
                umlText
                    .append(modifierTokens(method.getModifiers()))
                    .append(method.getName())
                    .append(SPACE_TOKEN);
                List<Method.Param> params = method.getParams();
                if (Objects.nonNull(params)) {
                    umlText
                        .append("( ")
                        .append(
                            params.stream()
                                .map(Method.Param::getName)
                                .collect(joining(","))
                        );
                    umlText.append(" )")
                        .append(NEW_LINE);
                }

            });
        }
    }

    private void parseFields(List<Field> fields) {
        if (Objects.nonNull(fields)) {
            fields.forEach(field ->
                umlText
                    .append(modifierTokens(field.getModifiers()))
                    .append(field.getName())
                    .append(NEW_LINE));
        }
        umlText.append("\n");
    }

    private String modifierTokens(List<Modifier> modifiers) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < modifiers.size(); ++i) {
            String text = modifiers.get(i).getText();
            if (i == 0) {
                result.append(modifierIcon(text))
                    .append(SPACE_TOKEN);
                continue;
            }
            result.append(text)
                .append(SPACE_TOKEN);
        }
        return result.toString();
    }

    private String modifierIcon(String text) {
        if (Objects.equals(text, PUBLIC_MODIFIER_TOKEN)) {
            return "+";
        }
        if (Objects.equals(text, PROTECTED_MODIFIER_TOKEN)) {
            return "#";
        }
        if (Objects.equals(text, PRIVATE_MODIFIER_TOKEN)) {
            return "-";
        }
        return "~ "+ text;
    }


}
