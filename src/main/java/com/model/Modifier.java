package com.model;

import lombok.Getter;

@Getter
public class Modifier {
    private String text;

    private Modifier(String text) {
        this.text = text;
    }

    public static Modifier of(String text) {
        return new Modifier(text);
    }
}
