package org.theoriok.adventofcode.y2024;

import static org.theoriok.adventofcode.util.Utils.splitToList;

import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day5 implements Day<Integer, Integer> {

    private final List<Rule> rules;
    private final List<PageList> pageLists;

    public Day5(List<String> input) {
        int emptyLine = input.indexOf("");
        rules = input.subList(0, emptyLine).stream()
            .map(Rule::from)
            .toList();
        pageLists = input.subList(emptyLine + 1, input.size()).stream()
            .map(PageList::from)
            .toList();
    }

    @Override
    public Integer firstMethod() {
        return pageLists.stream()
            .filter(pageList -> pageList.isValid(rules))
            .mapToInt(PageList::middlePage)
            .sum();
    }

    @Override
    public Integer secondMethod() {
        return pageLists.stream()
            .filter(pageList -> !pageList.isValid(rules))
            .map(pageList -> pageList.reOrderedPages(rules))
            .mapToInt(PageList::middlePage)
            .sum();
    }

    record Rule(int firstPage, int secondPage) {
        public static Rule from(String line) {
            String[] split = line.split("\\|");
            return new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }

        public boolean validate(List<Integer> pages) {
            return pages.indexOf(firstPage()) < pages.indexOf(secondPage());
        }

        public boolean appliesTo(List<Integer> pages) {
            return pages.contains(firstPage()) && pages.contains(secondPage());
        }
    }

    record PageList(List<Integer> pages) {
        public static PageList from(String line) {
            return new PageList(splitToList(line, ",", Integer::parseInt));
        }

        public boolean isValid(List<Rule> rules) {
            return rules.stream()
                .filter(rule -> rule.appliesTo(pages))
                .allMatch(rule -> rule.validate(pages));
        }

        public int middlePage() {
            return pages.get(pages.size() / 2);
        }

        public PageList reOrderedPages(List<Rule> rules) {
            var reOrderedPages = new ArrayList<>(pages);
            reOrderedPages.sort(compareAccordingToTheRules(rules));

            return new PageList(reOrderedPages);
        }

        private Comparator<Integer> compareAccordingToTheRules(List<Rule> rules) {
            return (page1, page2) -> {
                List<Integer> pagesToSort = List.of(page1, page2);
                return rules.stream()
                    .filter(rule -> rule.appliesTo(pagesToSort))
                    .findFirst()
                    .map(rule -> rule.validate(pagesToSort) ? 1 : -1)
                    .orElse(0);
            };
        }
    }
}
