package org.theoriok.adventofcode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theoriok.adventofcode.util.FileReader;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/{year}")
public class Controller {
    private final FileReader fileReader;

    public Controller(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @GetMapping("/{day}")
    public ResponseEntity<String> day(@PathVariable String year, @PathVariable String day) throws URISyntaxException {
        var input = fileReader.readFile("/%s/day%s.txt".formatted(year, day));
        var className = "org.theoriok.adventofcode.y%s.Day%s".formatted(year, day);
        try {
            Day dayObject = (Day) Class.forName(className).getDeclaredConstructor(List.class).newInstance(input);
            return ResponseEntity.ok(getOutput(dayObject.firstMethod(), dayObject.secondMethod()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    private String getOutput(long firstValue, long secondValue) {
        return "First value: %d%nSecond value: %d".formatted(firstValue, secondValue);
    }
}
