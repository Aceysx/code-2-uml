package com.service;

import com.model.Violation;
import com.util.parser.JavaParser;
import com.util.parser.JavaParserBaseListener;

import java.util.ArrayList;
import java.util.List;

public abstract class ViolationListener extends JavaParserBaseListener {
    protected List<Violation> violations = new ArrayList<>();
    protected String className = "";
    protected JavaParser parser;

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        updateClassName(ctx.IDENTIFIER().toString());
        super.enterClassDeclaration(ctx);
    }

    public List<Violation> getViolations() {
        return violations;
    }

    protected String getClassName() {
        return className;
    }

    protected void updateClassName(String className) {
        this.className = className;
    }

    public void updateParser(JavaParser parser) {
        this.parser = parser;
    }

}
