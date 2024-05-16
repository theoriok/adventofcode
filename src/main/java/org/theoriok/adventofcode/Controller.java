package org.theoriok.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private final FileReader fileReader;

    public Controller(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @GetMapping("/{day}")
    public ResponseEntity<String> day(@PathVariable(name = "year") String year, @PathVariable(name = "day") String day) throws URISyntaxException {
        var input = fileReader.readFile("/%s/day%s.txt".formatted(year, day));
        var className = "org.theoriok.adventofcode.y%s.Day%s".formatted(year, day);
        try {
            var dayObject = (Day) Class.forName(className).getDeclaredConstructor(List.class).newInstance(input);
            return ResponseEntity.ok(getOutput(dayObject.firstMethod(), dayObject.secondMethod()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String getOutput(Object firstValue, Object secondValue) {
        return "First value: %s%nSecond value: %s".formatted(firstValue, secondValue);
    }
}
