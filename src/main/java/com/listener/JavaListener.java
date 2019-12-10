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

import static com.util.Constant.*;

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
        String className = classContext.getChild(1).getText();
        List<Type> types = parseClassTypes(classContext);
        getJavaStructure()
            .addKlass(
                modifiers,
                getCurrentType(classContext),
                className,
                types
            );
    }

    private String getCurrentType(ParseTree classContext) {
        if (classContext instanceof JavaParser.ClassDeclarationContext) {
            return CLASS_TOKEN;
        }
        if (classContext instanceof JavaParser.InterfaceDeclarationContext) {
            return INTERFACE_TOKEN;
        }
        return ANNOTATION_TOKEN;
    }

    @Override
    public void enterInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx) {
        getJavaStructure()
            .getKlass()
            .addMethod(parseInterfaceMethod(ctx));
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

    private Method parseInterfaceMethod(JavaParser.InterfaceBodyDeclarationContext ctx) {
        ParseTree methodContext = find(ctx, "//interfaceMethodDeclaration")
            .iterator().next();
        List<Method.Param> params = find((RuleContext) methodContext, "//formalParameter").stream()
            .map(context -> Method.ofParam(textOf((RuleContext) context)))
            .collect(Collectors.toList());
        String methodName;
        methodName = methodContext.getChild(1).getText();
        List<Modifier> modifiers = parseMethodModifiers(ctx, false);
        return Method.of(false, modifiers, methodName, params);
    }

    private Method parseMethod(JavaParser.ClassBodyDeclarationContext ctx) {
        boolean isConstructor = !find(ctx, "//constructorDeclaration").isEmpty();
        List<Modifier> modifiers = parseMethodModifiers(ctx, isConstructor);
        ParseTree methodContext = find(ctx,
            isConstructor ? "//constructorDeclaration" : "//methodDeclaration")
            .iterator().next();
        List<Method.Param> params = find((RuleContext) methodContext, "//formalParameter").stream()
            .map(context -> Method.ofParam(textOf((RuleContext) context)))
            .collect(Collectors.toList());
        String methodName;
        if (isConstructor) {
            methodName = methodContext.getChild(0).getText();
        } else {
            methodName = methodContext.getChild(1).getText();
        }
        return Method.of(isConstructor, modifiers, methodName, params);
    }

    private Field parseField(JavaParser.ClassBodyDeclarationContext ctx) {
        List<Modifier> modifiers = findModifiers(ctx);
        String fieldName = find(ctx, "//variableDeclaratorId").iterator().next().getText();
        return Field.of(modifiers, fieldName);
    }

    private List<Modifier> findModifiers(RuleContext ctx) {
        List<Modifier> classOrInterfaceModifiers = find(ctx, "//classOrInterfaceModifier").stream()
            .map(context -> Modifier.of(context.getText()))
            .collect(Collectors.toList());

        List<Modifier> classOrInterfaceTypes = find(ctx, "//classOrInterfaceType").stream()
            .map(context -> Modifier.of(context.getText())).collect(Collectors.toList());
        if (!classOrInterfaceTypes.isEmpty()) {
            classOrInterfaceModifiers.add(classOrInterfaceTypes.get(0));
            return classOrInterfaceModifiers;
        }
        List<Modifier> basicTypes = find(ctx, "//typeType").stream()
            .map(context -> Modifier.of(context.getText())).collect(Collectors.toList());
        if (!basicTypes.isEmpty()) {
            classOrInterfaceModifiers.add(basicTypes.get(0));
            return basicTypes;
        }
        return new ArrayList<>();
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
        Collection<ParseTree> matches = find(ctx, "//*/memberDeclaration/fieldDeclaration");
        return !matches.isEmpty();
    }

    // TODO: 2019/12/7 范型的处理 <T> / class<T>
    private List<Type> parseClassTypes(ParseTree context) {
        List<Type> result = new ArrayList<>();
        for (int i = 2; i < context.getChildCount(); ++i) {
            ParseTree child = context.getChild(i);
            if (child instanceof JavaParser.ClassBodyContext
                ||
                child instanceof JavaParser.InterfaceBodyContext) {
                continue;
            }
            if (child instanceof TerminalNode) {
                result.add(Type.of(child.getText()));
                continue;
            }
            String text = textOf((RuleContext) child).split("<")[0];
            if (text.isEmpty()) {
                continue;
            }
            result.add(Type.of(text));
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

    private List<Modifier> parseMethodModifiers(RuleContext ctx, boolean isConstructor) {
        List<Modifier> result = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); ++i) {
            ParseTree child = ctx.getChild(i);
            boolean isAnnotation = child.getText().startsWith("@");
            if (isAnnotation) continue;

            if (child instanceof JavaParser.ModifierContext) {
                result.add(Modifier.of(child.getText()));
            }
            if ((child instanceof JavaParser.MemberDeclarationContext
                ||
                child instanceof JavaParser.InterfaceMemberDeclarationContext
            )
                &&
                !isConstructor) {
                result.add(Modifier.of(child.getChild(0).getChild(0).getText()));
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
