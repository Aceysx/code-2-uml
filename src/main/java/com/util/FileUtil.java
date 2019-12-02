package com.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String> findFilesPathBy(File dir) {
        List<String> files = new ArrayList<>();
        get(dir,files);
        return files;
    }

    private static void get(File file,List files) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                get(f, files);
            }
            if (f.isFile()) {
                files.add(f.getPath());
            }
        }
    }

}
