package org.theoriok.adventofcode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

class Years {
    public List<Integer> availableYears() {
        List<Integer> years = new ArrayList<>();
        var yearPattern = Pattern.compile("^y(\\d{4})$");
        try {
            var classLoader = Thread.currentThread().getContextClassLoader();
            var directory = new File(Objects.requireNonNull(classLoader.getResource("org/theoriok/adventofcode")).getFile());
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isDirectory()) {
                    var matcher = yearPattern.matcher(file.getName());
                    if (matcher.matches()) {
                        years.add(Integer.parseInt(matcher.group(1)));
                    }
                }
            }
        } catch (Exception ignored) {
            // Return empty list if any error occurs
        }
        Collections.sort(years);
        return years;
    }
}
