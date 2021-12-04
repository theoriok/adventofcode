package org.theoriok.adventofcode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theoriok.adventofcode.util.FileReader;
import org.theoriok.adventofcode.y2021.Day1;
import org.theoriok.adventofcode.y2021.Day2;
import org.theoriok.adventofcode.y2021.Day3;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/2021")
public class Y2021Controller {
    private final FileReader fileReader;

    public Y2021Controller(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @GetMapping("/1")
    public ResponseEntity<String> day1() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day1.txt");
        var day1 = new Day1(input);
        return ResponseEntity.ok(getOutput(day1.firstMethod(), day1.secondMethod()));
    }

    @GetMapping("/2")
    public ResponseEntity<String> day2() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day2.txt");
        var day2 = new Day2(input);
        return ResponseEntity.ok(getOutput(day2.firstMethod(), day2.secondMethod()));
    }

    @GetMapping("/3")
    public ResponseEntity<String> day3() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day3.txt");
        var day3 = new Day3(input);
        return ResponseEntity.ok(getOutput(day3.firstMethod(), day3.secondMethod()));
    }

    private String getOutput(int firstValue, int secondValue) {
        return "First value: %d%nSecond value: %d".formatted(firstValue, secondValue);
    }
}
