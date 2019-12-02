package com.service;

import com.model.Violation;
import com.util.parser.JavaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ExcessiveMethodLengthRule extends ViolationListener {
    private final static Integer LIMIT_LINE = 10;

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        if (isViolateRule(ctx)) {
            Token start = ctx.getStart();
            Token end = ctx.getStop();
            String code = parser.getTokenStream().getText(ctx.getSourceInterval());
            violations.add(Violation.build(
                    getClassName(),
                    code,
                    start.getLine() + "," + start.getCharPositionInLine(),
                    end.getLine() + "," + end.getCharPositionInLine(),
                    "Excessive method length rule, limit 10 line"
            ));
        }
        super.enterMethodDeclaration(ctx);
    }

    private boolean isViolateRule(ParserRuleContext context) {
        Token start = context.getStart();
        Token end = context.getStop();
        return end.getLine() - start.getLine() >= LIMIT_LINE;
    }
}
