package com.service;

import com.model.Structure;
import com.util.FileUtil;

import java.io.IOException;
import java.util.List;

public class JavaService {

    public void parse(String projectDir) throws IOException {
        List<Structure> structures = JavaParserService.parse(projectDir);
        String umlText = JavaUml.parse(structures);
        FileUtil.write(umlText);
    }
}
