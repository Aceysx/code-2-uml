package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaStructure extends Structure {
    private String packageName;
    private List<Import> imports;
    private List<Klass> klasses;

    public void updatePackageName(String packageName) {
        this.packageName = packageName;
    }

    public void addImport(String text) {
        if (Objects.isNull(imports)) {
            this.imports = new ArrayList<>();
        }
        this.imports.add(new Import(text));
    }

    public void addKlass(List<Modifier> modifiers, boolean isClassType, String className, List<Type> types) {
        if (Objects.isNull(klasses)) {
            this.klasses = new ArrayList<>();
        }
        this.klasses.add(
            new Klass(modifiers,isClassType,className,types)
        );
    }

    public Klass getKlass() {
        if (Objects.isNull(klasses) || klasses.isEmpty()) {
            // TODO: 2019/12/4 throw exception
        }
        return this.klasses.get(klasses.size() - 1);
    }
}
