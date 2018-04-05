package com.example.cucumber.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileReader {

    public static String read(String filePath) throws IOException {
        File file = new File(filePath);
        InputStream in = new FileInputStream(file);
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        return writer.toString();
    }
}
