package com.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Klass {
    private List<Modifier> modifiers;
    private boolean isClass;
    private String name;
    private List<Type> types;
    private List<Field> fields;
    private List<Method> methods;

    Klass(List<Modifier> modifiers, boolean isClassType, String className, List<Type> types) {
        this.modifiers = modifiers;
        this.isClass = isClassType;
        this.name = className;
        this.types = types;
    }

    public void addField(Field field) {
        if (Objects.isNull(fields)) {
            fields = new ArrayList<>();
        }
        fields.add(field);
    }

    public void addMethod(Method method) {
        if (Objects.isNull(methods)) {
            methods = new ArrayList<>();
        }
        methods.add(method);
    }
}
