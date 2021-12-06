package org.theoriok.adventofcode.y2021;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theoriok.adventofcode.util.FileReader;

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
        var day = new Day1(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/2")
    public ResponseEntity<String> day2() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day2.txt");
        var day = new Day2(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/3")
    public ResponseEntity<String> day3() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day3.txt");
        var day = new Day3(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/4")
    public ResponseEntity<String> day4() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day4.txt");
        var day = new Day4(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/5")
    public ResponseEntity<String> day5() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day5.txt");
        var day = new Day5(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/6")
    public ResponseEntity<String> day6() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day6.txt");
        var day = new Day6(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    @GetMapping("/7")
    public ResponseEntity<String> day7() throws URISyntaxException {
        var input = fileReader.readFile("/2021/day7.txt");
        var day = new Day7(input);
        return ResponseEntity.ok(getOutput(day.firstMethod(), day.secondMethod()));
    }

    private String getOutput(long firstValue, long secondValue) {
        return "First value: %d%nSecond value: %d".formatted(firstValue, secondValue);
    }
}
