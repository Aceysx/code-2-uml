package com.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Method {
    private List<Modifier> modifiers;
    private boolean isConstructor;
    private String name;
    private List<Param> params;

    private Method(boolean isConstructor, List<Modifier> modifiers, String methodName, List<Param> params) {
        this.isConstructor = isConstructor;
        this.modifiers = modifiers;
        this.name = methodName;
        this.params = params;
    }

    public static Param ofParam(String text) {
        return new Param(text);
    }

    public static Method of(boolean isConstructor, List<Modifier> modifiers, String methodName, List<Param> params) {
        return new Method(isConstructor, modifiers, methodName, params);
    }


    public static class Param {
        private String name;
        Param(String text) {
            this.name = text;
        }

        public String getName() {
            return name;
        }
    }
}
