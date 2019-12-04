package com.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Field {
    private List<Modifier> modifiers;
    private String name;

    private Field(List<Modifier> modifiers, String fieldName) {
        this.modifiers= modifiers;
        this.name = fieldName;
    }


    public static Field of(List<Modifier> modifiers, String fieldName) {
        return new Field(modifiers, fieldName);
    }
}
