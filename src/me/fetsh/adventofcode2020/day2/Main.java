package me.fetsh.adventofcode2020.day2;

import me.fetsh.adventofcode2020.utils.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        var policyLines = File.readAllBytes(Main.class.getResource("input.txt").getPath()).split("\n");
        var passwordsWithPolicies = Arrays.stream(policyLines).map(s -> s.split(":\s+|-|\s+")).toArray(String[][]::new);

        System.out.println(Arrays.stream(passwordsWithPolicies).filter(Main::passwordValid1).count());
        System.out.println(Arrays.stream(passwordsWithPolicies).filter(Main::passwordValid2).count());
    }

    public static Boolean passwordValid1(String[] passEnt){
        var sAry = passEnt[3].split("");
        long occurrences = Arrays.stream(sAry).filter(s -> s.equals(passEnt[2])).count();
        return occurrences >= Integer.parseInt(passEnt[0]) && occurrences <= Integer.parseInt(passEnt[1]);
    }
    public static Boolean passwordValid2(String[] passEnt) {
        var sAry = passEnt[3].split("");
        return sAry[Integer.parseInt(passEnt[0]) - 1].equals(passEnt[2]) ^ sAry[Integer.parseInt(passEnt[1]) - 1].equals(passEnt[2]);
    }
}



//        var charAry = passEnt[3].chars();
//        int testChar = Character.getNumericValue(passEnt[2].charAt(0));
//        long occurrences = charAry.filter(s -> Character.getNumericValue(s) == testChar).count();
