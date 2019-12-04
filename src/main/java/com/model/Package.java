package com.model;

import lombok.Getter;

@Getter
public class Package {
    private String modifier;
    private String text;

    public Package(String modifier, String text) {
        this.modifier = modifier;
        this.text = text;
    }
}
