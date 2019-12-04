package com.listener;

import com.model.*;
import com.util.parser.JavaParser;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.xpath.XPath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaListener extends ParserListener {
    private JavaParser parser;

    public JavaListener(Structure structure, JavaParser parser) {
        super(structure);
        this.parser = parser;
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        getJavaStructure()
            .addImport(textOf(ctx));
    }

    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        getJavaStructure()
            .updatePackageName(textOf(ctx));
    }

    @Override
    public void enterTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
        List<Modifier> modifiers = parseClassModifiers(ctx);
        ParseTree classContext = ctx.getChild(modifiers.size());
        boolean isClassType = classContext instanceof JavaParser.ClassDeclarationContext;
        String className = classContext.getChild(1).getText();
        List<Type> types = parseClassTypes(classContext);
        getJavaStructure()
            .addKlass(
                modifiers,
                isClassType,
                className,
                types
            );
    }

    @Override
    public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
        if (isFiledContext(ctx)) {
            getJavaStructure()
                .getKlass()
                .addField(parseField(ctx));
        }
        if (isMethodContext(ctx)) {
            getJavaStructure()
                .getKlass()
                .addMethod(parseMethod(ctx));
        }
    }

    private Method parseMethod(JavaParser.ClassBodyDeclarationContext ctx) {
        boolean isConstructor = !find(ctx, "//constructorDeclaration").isEmpty();
        List<Modifier> modifiers = parseClassModifiers(ctx);
        ParseTree methodContext = find(ctx,
            isConstructor ? "//constructorDeclaration" : "//methodDeclaration").iterator().next();
        List<Method.Param> params = find((RuleContext) methodContext, "//formalParameter").stream()
            .map(context -> Method.ofParam(textOf((RuleContext) context)))
            .collect(Collectors.toList());
        String methodName;
        if (isConstructor) {
            methodName = methodContext.getChild(0).getText();
        } else {
            methodName = methodContext.getChild(1).getText();
        }
        return Method.of(isConstructor,modifiers, methodName, params);
    }

    private Field parseField(JavaParser.ClassBodyDeclarationContext ctx) {
        List<Modifier> modifiers = findModifiers(ctx);
        String fieldName = find(ctx, "//variableDeclaratorId").iterator().next().getText();
        return Field.of(modifiers, fieldName);
    }

    private List<Modifier> findModifiers(RuleContext ctx) {
        return Stream.concat(find(ctx, "//classOrInterfaceModifier").stream()
                .map(context -> Modifier.of(context.getText())),
            find(ctx, "//classOrInterfaceType").stream()
                .map(context -> Modifier.of(context.getText())))
            .collect(Collectors.toList());
    }

    private Collection<ParseTree> find(RuleContext ctx, String xpath) {
        return XPath.findAll(ctx, xpath, parser);
    }

    private boolean isMethodContext(JavaParser.ClassBodyDeclarationContext ctx) {
        Collection<ParseTree> methods = find(ctx, "//methodDeclaration");
        Collection<ParseTree> constructors = find(ctx, "//constructorDeclaration");
        return !(methods.isEmpty() && constructors.isEmpty());
    }

    private boolean isFiledContext(JavaParser.ClassBodyDeclarationContext ctx) {
        Collection<ParseTree> matches = find(ctx, "//fieldDeclaration");
        return !matches.isEmpty();
    }

    private List<Type> parseClassTypes(ParseTree context) {
        List<Type> result = new ArrayList<>();
        for (int i = 2; i < context.getChildCount(); ++i) {
            ParseTree child = context.getChild(i);
            if (child instanceof JavaParser.ClassBodyContext) {
                continue;
            }
            if (child instanceof TerminalNode) {
                result.add(Type.of(child.getText()));
                continue;
            }
            result.add(Type.of(textOf((RuleContext) child)));
        }
        return result;
    }

    private List<Modifier> parseClassModifiers(RuleContext ctx) {
        List<Modifier> result = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); ++i) {
            ParseTree child = ctx.getChild(i);
            if (child instanceof JavaParser.ClassOrInterfaceModifierContext) {
                result.add(Modifier.of(child.getText()));
            }
        }
        return result;
    }

    private JavaStructure getJavaStructure() {
        return (JavaStructure) getStructure();
    }

    private String textOf(RuleContext context) {
        TokenStream tokenStream = parser.getTokenStream();
        return tokenStream.getText(context.getSourceInterval());
    }
}
