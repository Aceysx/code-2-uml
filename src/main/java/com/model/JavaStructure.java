package com.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class JavaStructure extends Structure {
    private Package packagee;
    private List<Import> imports;
    private List<Klass> klasses;

    public void updatePackageName(String packageName) {
        if (Objects.nonNull(packageName)) {
            this.packagee = Package.of(
                packageName.substring(packageName.indexOf(" "), packageName.length() - 1)
            );
        }
    }

    public void addImport(String text) {
        if (Objects.isNull(imports)) {
            this.imports = new ArrayList<>();
        }
        this.imports.add(Import.of(
            text.substring(text.indexOf(" "), text.length() - 1)
        ));
    }

    public void addKlass(List<Modifier> modifiers, String type, String className, List<Type> types) {
        if (Objects.isNull(klasses)) {
            this.klasses = new ArrayList<>();
        }
        this.klasses.add(
            new Klass(modifiers, type, className, types)
        );
    }

    public Klass getKlass() {
        if (Objects.isNull(klasses) || klasses.isEmpty()) {
            // TODO: 2019/12/4 throw exception
        }
        return this.klasses.get(klasses.size() - 1);
    }
}
