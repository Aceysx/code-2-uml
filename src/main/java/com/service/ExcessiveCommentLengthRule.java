package com.service;

import com.model.Violation;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExcessiveCommentLengthRule extends ViolationListener {

    public static final int COMMENT_CHANNEL = 2;

    @Override
    public void visitTerminal(TerminalNode node) {
        TokenStream tokenStream = parser.getTokenStream();

        for (int i = 0; i < tokenStream.size(); ++i) {
            Token token = tokenStream.get(i);
            if (token.getChannel() == COMMENT_CHANNEL) {
                String code = token.getText();
                violations.add(Violation.build(
                        getClassName(),
                        code,
                        token.getLine() + "",
                        token.getLine() + "",
                        "comment"
                ));
            }

        }
        super.visitTerminal(node);
    }


}
