package me.fetsh.adventofcode2020.day8;

import me.fetsh.adventofcode2020.utils.File;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

record Instruction(String instruction){
    private static final Map<String, BiFunction<Integer, Integer, Integer>> accFunction = new HashMap<>();
    private static final Map<String, BiFunction<Integer, Integer, Integer>> indFunction = new HashMap<>();
    static {
        accFunction.put("nop", (a, b) -> a);
        accFunction.put("jmp", (a, b) -> a);
        accFunction.put("acc", Integer::sum);
        indFunction.put("nop", (a, b) -> a + 1);
        indFunction.put("jmp", Integer::sum);
        indFunction.put("acc", (a, b) -> a + 1);
    }
    int acc(int init){
        return accFunction.get(instruction.substring(0,3)).apply(init, Integer.parseInt(instruction.substring(4)));
    }
    int index(int init){
        return indFunction.get(instruction.substring(0,3)).apply(init, Integer.parseInt(instruction.substring(4)));
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        var instructionLines = File.readAllBytes(Main.class.getResource("input.txt").getPath()).split("\n");
        
        System.out.println(calcAccBeforeRepeating(instructionLines, new HashSet<>(), 0, 0));
        System.out.println(calcAcc(instructionLines, new HashSet<>(), 0, 0));
    }

    public static OptionalInt calcAccBeforeRepeating(String[] instructionLines, Set<Integer> visitedIndices, Integer index, Integer acc){
        if (index > instructionLines.length) return OptionalInt.empty();
        if (!visitedIndices.add(index)) return OptionalInt.of(acc);
        var instruction = new Instruction(instructionLines[index]);
        return calcAccBeforeRepeating(instructionLines, visitedIndices, instruction.index(index), instruction.acc(acc));
    }

    public static OptionalInt calcAcc(String[] instructionLines, Set<Integer> visitedIndices, Integer index, Integer acc){
        if (index == instructionLines.length) return OptionalInt.of(acc);
        if (index > instructionLines.length) return OptionalInt.empty();
        if (!visitedIndices.add(index)) return OptionalInt.empty();
        var instruction = new Instruction(instructionLines[index]);
        return calcAcc(instructionLines, visitedIndices, instruction.index(index), instruction.acc(acc));
    }
}
