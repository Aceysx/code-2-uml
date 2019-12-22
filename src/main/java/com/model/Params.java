package com.model;

import java.util.Objects;

public class Params {
    private static String scanPath;
    private static boolean exportClassOnly = false;

    public static void init(String scanPath, boolean classOnly) throws IllegalAccessException {
        if (Objects.nonNull(Params.getScanPath())) {
            throw new IllegalAccessException("can not modify params repeated");
        }
        Params.scanPath = scanPath;
        exportClassOnly = classOnly;
    }

    public static String getScanPath() {
        return scanPath;
    }

    public static boolean isExportClassOnly() {
        return exportClassOnly;
    }
}
