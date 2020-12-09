package me.fetsh.adventofcode2020.day9;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 {
    public static void main(String[] args) throws IOException {
        var numbers = Files.lines(Path.of(Part1.class.getResource("input.txt").getPath())).mapToLong(Long::parseLong).toArray();
        var preambleSize = 25;
        System.out.println(getInvalid(numbers, preambleSize).get());
    }

    public static Optional<Long> getInvalid(long[] numbers, int preambleSize) {
        return IntStream.range(preambleSize, numbers.length)
                .filter(i -> {
                    var num = numbers[i];
                    var preamble =  Arrays.copyOfRange(numbers, i - preambleSize, i);
                    return Arrays.stream(preamble).noneMatch(n -> Arrays.stream(preamble).boxed().filter(s -> s != n).collect(Collectors.toList()).contains(num - n));
                })
                .mapToObj(i -> numbers[i])
                .findFirst();
    }
}
