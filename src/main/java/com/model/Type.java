package com.model;

import lombok.Getter;

@Getter
public class Type {
    private String text;

    public Type(String text) {
        this.text = text;
    }

    public static Type of(String text) {
        return new Type(text);
    }
}
