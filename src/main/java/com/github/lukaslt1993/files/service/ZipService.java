package com.github.lukaslt1993.files.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {

    public byte[] zip(Map<String, String> nameAndContent) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (Map.Entry<String, String> entry : nameAndContent.entrySet()) {
            zos.putNextEntry(new ZipEntry(entry.getKey()));
            zos.write(entry.getValue().getBytes());
            zos.closeEntry();
        }

        zos.close();
        return baos.toByteArray();
    }

    public Map<String, String> unzip(byte[] content) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        ZipInputStream zis = new ZipInputStream(bais);
        Map<String, String> result = new HashMap<>();
        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {
            result.put(entry.getName(), new String(zis.readAllBytes()));
            zis.closeEntry();
        }

        zis.close();
        return result;
    }

}
