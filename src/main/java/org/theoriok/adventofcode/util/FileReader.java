package org.theoriok.adventofcode.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class FileReader {
    public List<String> readFile(String filePath) throws URISyntaxException {
        List<String> strings = new ArrayList<>();
        var resource = getClass().getResource(filePath);
        if (resource != null) {
            var path = Paths.get(resource.toURI());
            try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    strings.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return strings;
    }
}
