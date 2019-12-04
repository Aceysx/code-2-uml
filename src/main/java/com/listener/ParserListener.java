package com.listener;

import com.model.Structure;
import com.util.parser.JavaParserBaseListener;

public abstract class ParserListener extends JavaParserBaseListener {
    private Structure structure;

    ParserListener(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructure() {
        return structure;
    }
}
