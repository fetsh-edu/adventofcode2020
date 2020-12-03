package me.fetsh.adventofcode2020.day3;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Forest {

    static char charAt(String string, int position) {
        return (char) Stream.generate(string::chars).flatMapToInt(s -> s).skip(position).findFirst().getAsInt();
    }

    public static String[] everyNth(String[] ary, int n) {
        int limit = ary.length / n + Math.min(ary.length % n, 1);
        return Stream.iterate(0, i -> i + n)
                .limit(limit)
                .map(i -> ary[i])
                .toArray(String[]::new);
    }

    public static long treesEncountered(String forestString, int initDown, int initRight, int down, int right){
        var filteredForest = everyNth(forestString.split("\n"), down);
        var indexedForest = IntStream.range(0, filteredForest.length).mapToObj(i -> new AbstractMap.SimpleEntry<Integer, String>(i, filteredForest[i])).toArray(AbstractMap.SimpleEntry[]::new);

        return Arrays.stream(indexedForest)
                .skip(initDown - 1)
                .map(s -> charAt((String) s.getValue(), initRight + (int) s.getKey() * right - 1))
                .filter(s -> s == '#').count();
    }
}
