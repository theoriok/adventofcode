package org.theoriok.adventofcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class GenericDayTest {

    private static final List<String> LIST = List.of(

    );

    @Test
    void firstMethod() {
        var day = new GenericDay(LIST);

        var result = day.firstMethod();

        assertThat(result).isEqualTo(0);
    }

    @Test
    void secondMethod() {
        var day = new GenericDay(LIST);

        var result = day.secondMethod();

        assertThat(result).isEqualTo(0);
    }
}