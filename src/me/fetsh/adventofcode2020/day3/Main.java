package me.fetsh.adventofcode2020.day3;

import me.fetsh.adventofcode2020.utils.File;

import java.io.IOException;

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
