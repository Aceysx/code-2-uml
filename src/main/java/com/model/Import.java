package com.model;

import lombok.Getter;

@Getter
class Import {
    private String modifier;
    private String text;

    private Import(String text) {
        this.modifier = "import";
        this.text = text;
    }


    public static Import of(String text) {
        return new Import(text);
    }
}
