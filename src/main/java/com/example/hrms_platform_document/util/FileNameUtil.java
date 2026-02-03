package com.example.hrms_platform_document.util;

import java.text.Normalizer;

public class FileNameUtil {

    public static String sanitize(String filename) {
        return Normalizer.normalize(filename, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}
