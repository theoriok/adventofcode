package org.theoriok.adventofcode.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReader {
    public List<String> readFile(String filePath) throws URISyntaxException {
        List<String> strings = new ArrayList<>();
        var path = Paths.get(getClass().getResource(filePath).toURI());
        try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
