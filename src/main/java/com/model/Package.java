package com.model;

import lombok.Getter;

@Getter
public class Package {
    private String modifier;
    private String text;

    private Package(String modifier, String text) {
        this.modifier = modifier;
        this.text = text;
    }
    public static Package of(String text){
        return new Package("package", text);
    }
}
