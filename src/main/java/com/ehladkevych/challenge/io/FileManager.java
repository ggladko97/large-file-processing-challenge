package com.ehladkevych.challenge.io;

import com.ehladkevych.challenge.exception.DataProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileManager {

    private final Map<String, Long> lastModifiedState = new ConcurrentHashMap<>();
    private final String fileName;

    public FileManager(@Value("${weather.data.file.name}") String fileName) {
        this.fileName = fileName;
    }

    public File createFile() {
        try {
            return new File(ClassLoader.getSystemResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new DataProcessingException("File not found", e);
        }
    }

    public void updateLastModifiedForFile(File file) {
        lastModifiedState.put(fileName, file.lastModified());
    }

    public boolean shouldReloadAndRecalculate(File file) {
        return lastModifiedState.get(fileName) == null ||
                file.lastModified() > lastModifiedState.get(fileName);
    }
}
