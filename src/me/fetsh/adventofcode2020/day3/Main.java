package me.fetsh.adventofcode2020.day3;

import me.fetsh.adventofcode2020.utils.File;

import java.io.IOException;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Forest {

    static class Pair {
        public final int index;
        public final String line;
        public Pair(int index, String line) {
            this.index = index;
            this.line = line;
        }
    }


    static char charAt(String string, int position) {
        return string.charAt(position % string.length());
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
        var indexedForest = IntStream.range(0, filteredForest.length).mapToObj(i -> new Pair(i, filteredForest[i])).toArray(Pair[]::new);

        return Arrays.stream(indexedForest)
                .skip(initDown - 1)
                .map(s -> charAt(s.line, initRight + s.index * right - 1))
                .filter(s -> s == '#').count();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var forest = File.readAllBytes(Main.class.getResource("input.txt").getPath());

        var r1 = Forest.treesEncountered(forest, 1,1,1, 1);
        var r2 = Forest.treesEncountered(forest, 1,1,1, 3);
        var r3 = Forest.treesEncountered(forest, 1,1,1, 5);
        var r4 = Forest.treesEncountered(forest, 1,1,1, 7);
        var r5 = Forest.treesEncountered(forest, 1,1,2, 1);

        System.out.printf("%d * %d * %d * %d * %d = %d", r1, r2, r3, r4, r5, r1*r2*r3*r4*r5);
    }
}
