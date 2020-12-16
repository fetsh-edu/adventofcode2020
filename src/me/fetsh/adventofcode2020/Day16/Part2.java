package me.fetsh.adventofcode2020.Day16;

import me.fetsh.adventofcode2020.utils.File;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

class Range {
    int min;
    int max;
    static Range parseRange(String string) {
        var range = new Range();
        range.min = Integer.parseInt(string.split("-")[0]);
        range.max = Integer.parseInt(string.split("-")[1]);
        return range;
    }

    Boolean contains(int value) {
        return value >= min && value <= max;
    }

    @Override
    public String toString() {
        return "Range{min=" + min + ", max=" + max + '}';
    }
}

class Rule {
    String name;
    Range[] ranges;
    static Rule parseRule(String string){
        var rule = new Rule();
        rule.name = string.split(": ")[0];
        rule.ranges = Arrays.stream(string.split(": ")[1].split(" or "))
                .map(Range::parseRange)
                .toArray(Range[]::new);
        return rule;
    }

    Boolean validForFields(Stream<Integer> fields){
        return fields.allMatch(
                field -> Arrays.stream(ranges)
                        .anyMatch(range -> range.contains(field)));
    }

    @Override
    public String toString() {
        return "Rule{field='" + name + '\'' + ", ranges=" + Arrays.toString(ranges) +'}';
    }
}

class Ticket {
    int[] fields;
    static Ticket parseTicket(String string){
        var ticket = new Ticket();
        ticket.fields = Arrays.stream(string.split(",")).mapToInt(Integer::parseInt).toArray();
        return ticket;
    }
    Boolean isValid(Rule[] rules){
        return Arrays.stream(fields)
                .allMatch(
                        field -> Arrays.stream(rules)
                                .flatMap(r -> Stream.of(r.ranges))
                                .anyMatch(range -> range.contains(field))
                );
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "fields=" + Arrays.toString(fields) +
                '}';
    }
}

public class Part2 {
    public static void main(String[] args) throws IOException {
        var input = File.readAllBytes(Part1.class.getResource("input.txt").getPath()).split("\n\n");

        var rules= Arrays.stream(input[0].split("\n"))
                .map(Rule::parseRule)
                .toArray(Rule[]::new);

        var myTicket = Ticket.parseTicket(input[1].split("\n")[1]);

        var tickets = Arrays.stream(input[2].split("\n"))
                .skip(1)
                .map(Ticket::parseTicket)
                .toArray(Ticket[]::new);

        var validTickets = Arrays.stream(tickets).filter(ticket -> ticket.isValid(rules)).toArray(Ticket[]::new);

        HashMap<Integer, String[]> possiblePositions = new HashMap<>();
        HashMap<Integer, String> positions = new HashMap<>();
        Set<String> foundPositions = new HashSet<>();

        for (int i = 0; i < validTickets[1].fields.length; i++) {
            int finalI = i;
            var ruleNames = Arrays.stream(rules)
                    .filter(rule -> rule.validForFields(Arrays.stream(validTickets).map(t -> t.fields[finalI])))
                    .map(r -> r.name).toArray(String[]::new);
            possiblePositions.put(finalI, ruleNames);
        }
        possiblePositions
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(c -> c.getValue().length))
                .forEach(e -> {
                    var position = Arrays.stream(e.getValue()).filter(p -> !foundPositions.contains(p)).findFirst();
                    foundPositions.add(position.get());
                    positions.put(e.getKey(), position.get());
                });

        var departureFields = positions.entrySet().stream().filter(e -> e.getValue().contains("departure")).mapToInt(Map.Entry::getKey);

        var myDepartureFields = departureFields.mapToLong(i -> myTicket.fields[i]).reduce(1, (a, b) -> a * b);
        System.out.println(myDepartureFields);

    }
}
