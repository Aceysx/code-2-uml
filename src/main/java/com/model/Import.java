package com.model;

import lombok.Getter;

@Getter
class Import {
    private String modifier;
    private String text;

    Import(String text) {
        this.modifier = "import";
        this.text = text;
    }
}
