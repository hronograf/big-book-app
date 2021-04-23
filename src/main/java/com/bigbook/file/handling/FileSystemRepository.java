package com.bigbook.file.handling;

import com.bigbook.exceptions.WebException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {

    private final String RESOURCES_DIR = System.getProperty("user.dir");

    public String save(byte[] content, String filename) throws Exception {
        String storedName = new Date().getTime() + "-" + filename;
        Path newFile = Paths.get(RESOURCES_DIR + "/" + storedName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return storedName;
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(RESOURCES_DIR + "/" + location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new WebException(HttpStatus.NOT_FOUND, "File not found");
        }
    }
}
