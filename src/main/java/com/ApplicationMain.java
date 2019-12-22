package com;

import com.model.Params;
import com.service.JavaService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


public class ApplicationMain {
    private static JavaService javaService = new JavaService();

    public static void main(String[] args) throws IOException, IllegalAccessException {
        parse(args);

        javaService.parse(Params.getScanPath());
    }

    private static void parse(String[] args) throws IllegalAccessException {
        String path = findScanPath(args);
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("please pass code path to parse");
        }
        boolean exportClassOnly = isExportClassOnly(args);

        Params.init(path, exportClassOnly);
    }

    private static boolean isExportClassOnly(String[] args) {
        return Arrays.stream(args)
            .anyMatch(item -> item.startsWith("-classonly"));

    }

    private static String findScanPath(String[] args) {
        Optional<String> pathOptional = Arrays.stream(args)
            .filter(item -> item.startsWith("-path="))
            .findFirst();
        if (pathOptional.isPresent()) {
            String path = pathOptional.get();
            return path.split("-path=")[1];
        }
        return null;
    }
}
